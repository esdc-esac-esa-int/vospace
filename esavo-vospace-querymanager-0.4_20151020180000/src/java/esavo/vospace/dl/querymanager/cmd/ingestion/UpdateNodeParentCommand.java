package esavo.vospace.dl.querymanager.cmd.ingestion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.Node;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;
import esavo.vospace.dl.querymanager.util.QueryManagerUtils;

public class UpdateNodeParentCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(UpdateNodeParentCommand.class);
        
    /** Vospace Node DAO. */
    private INodeDao nodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getNodeDAO();
    
    private NodeTO nodeTO;
    
    /** Transfer object result. */
    private NodeTO result;
    
    private Node node;
    
    public UpdateNodeParentCommand(NodeTO node) {
        this.nodeTO = node;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        
        if (this.nodeTO == null) {
            throw new IllegalArgumentException("UpdateNodeParentCommand.execute() -> "
                    + "Input UnstructuredDataNode cannot be null.");
        }
        
        // Does this Node already exists??
        String path = QueryManagerUtils.getPath(this.nodeTO.getURI());
        this.node = this.nodeDao.queryNodeByUri(path);        
        
        ContainerNodeTO parentTO = this.nodeTO.getParent();
        String parentPath = QueryManagerUtils.getPath(parentTO.getURI());
        ContainerNode parent = (ContainerNode) this.nodeDao.queryNodeByUri(parentPath);
        
        this.node.setContainerNode(parent);        
        this.nodeDao.insertOrUpdate(this.node);

        // add the URI to the node
        String uriPath = this.nodeDao.queryNodeURIById(this.node.getNodeOid());        
        VOSpaceURI uri = QueryManagerUtils.composeURIFromPath(uriPath);        
        
        // mapping Transfer Object
        this.result = VospaceTransferObjectMapper.
                populateNodeTO(this.node, uri, true);        
    }

    @Override
    public Object getResult() {
        return this.result;
    }

}
