package esavo.vospace.dl.querymanager.query;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IContainerNodeDao;
import esac.archive.vospace.persistence.dao.ILinkNodeDao;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.dao.IStructuredDataNodeDao;
import esac.archive.vospace.persistence.dao.IUnstructuredDataNodeDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.LinkNode;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.StructuredDataNode;
import esac.archive.vospace.persistence.model.UnstructuredDataNode;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;
import esavo.vospace.dl.querymanager.util.QueryManagerUtils;

/**
 * Command to retrieve a node by its uri path.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class QueryNodeCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryNodeCommand.class);
    
    /** Vospace Node DAO. */
    private INodeDao nodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getNodeDAO();
    
    /** Vospace LinkNode DAO. */
    private ILinkNodeDao linkNodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getLinkNodeDAO();
    
    /** Vospace Container Node DAO. */
    private IContainerNodeDao containerNodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getContainerNodeDAO();
    
    /** Vospace Structured Data Node DAO. */
    private IStructuredDataNodeDao structuredNodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getStructuredDataNodeDAO();
    
    /** Vospace Unstructured Data Node DAO. */
    private IUnstructuredDataNodeDao unstructuredNodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUnstructuredDataNodeDAO();
    
    private VOSpaceURI nodeURI;
    
    /** Persistent Node */
    private Node node;
    
    /** Retrieve all related information otherwise not */
    private Boolean fullRetrieve;
    
    /** Transfer Object Node */
    private NodeTO nodeTO;
    
    /**
     * Constructor.
     * @param vospaceOwner
     * @param nodeUriPath
     */
    public QueryNodeCommand (final VOSpaceURI nodeURI, final Boolean retrieval) {
        this.nodeURI = nodeURI;
        this.fullRetrieve = retrieval;
    }
    
    @Override
    public void execute() {
        log.debug("Into QueryNodeCommand().execute()");
        
        if (this.nodeURI == null) {
            throw new IllegalArgumentException("QueryNodeCommand.execute() -> "
                    + "Input nodeURI cannot be null.");
        }
        
        String path = QueryManagerUtils.getPath(this.nodeURI);        
        this.node = this.nodeDao.queryNodeByUri(path);
        if (this.node == null) {
            throw new UnsupportedOperationException("Node: [" + this.nodeURI
                    + "] does not exists");
        }
        
        // Transform persistent Node into Node Transfer Object
        Integer nodeId = this.node.getNodeOid();        
        if (this.node instanceof LinkNode) {
            LinkNode linkNode = this.linkNodeDao.queryLinkNodeById(nodeId);            
            this.nodeTO = VospaceTransferObjectMapper
                    .populateLinkNodeTO(linkNode, this.nodeURI, this.fullRetrieve);
        } else if (this.node instanceof ContainerNode) {
            ContainerNode containerNode = 
                    this.containerNodeDao.queryContainerNodeById(nodeId);
            this.nodeTO = VospaceTransferObjectMapper
                    .populateContainerNodeTO(containerNode, 
                    this.nodeURI, this.fullRetrieve, this.fullRetrieve);
        } else if (this.node instanceof StructuredDataNode) {
            StructuredDataNode structuredDataNode =
                    this.structuredNodeDao.queryStructuredDataNodeById(nodeId);
            this.nodeTO = VospaceTransferObjectMapper
                    .populateStructuredDataNodeTO(structuredDataNode, 
                    this.nodeURI, this.fullRetrieve);
        } else if (this.node instanceof UnstructuredDataNode) {
            UnstructuredDataNode unstructuredDataNode =
                    this.unstructuredNodeDao.queryUnstructuredDataNodeById(nodeId);
            this.nodeTO = VospaceTransferObjectMapper
                    .populateUnstructuredDataNodeTO(unstructuredDataNode,
                            this.nodeURI, this.fullRetrieve);
        }
        
        log.debug("End of QueryNodeCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.nodeTO;
    }

}
