package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.Node;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.dl.querymanager.util.QueryManagerUtils;

/**
 * Command that removes a generic Node of any type
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class RemoveNodeCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(RemoveNodeCommand.class);
        
    /** Vospace Node DAO. */
    private INodeDao nodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getNodeDAO();
    
    /** Vospace node uri path */
    private VOSpaceURI nodeURI;
    
    /** Vospace name */
    private String vospaceName;
    
    /**
     * Constructor.
     * @param vospaceName
     * @param uri
     */
    public RemoveNodeCommand(final VOSpaceURI uri) {
        log.info("Into RemoveNodeCommand(" + uri + ")");
        this.vospaceName = uri.getAuthority();
        this.nodeURI = uri;
    }
    
    @Override
    public void execute() {
        log.debug("Into RemoveNodeCommand().execute()");
        
        if (this.nodeURI == null || this.vospaceName == null) {
            throw new IllegalArgumentException("RemoveNodeCommand.execute() -> "
                    + "Input values cannot be null.");
        }
        
        String path = QueryManagerUtils.getPath(this.nodeURI);
        Node persistentNode = this.nodeDao.queryNodeByUri(path);
        if (persistentNode != null) {
            deletePersistentNode(persistentNode);
        } else {
            throw new UnsupportedOperationException("Node for uri ["
                    + this.nodeURI + "] does not exist");
        }
        
        log.debug("End of RemoveNodeCommand(" + this.nodeURI + ")");
    }
    
    /*
     * Removes a tree of nodes (recursive) 
     */
    private void deletePersistentNode(final Node node) {
        if (node != null) {
            
            if (node instanceof ContainerNode) {
                ContainerNode container = (ContainerNode) node;
                
                Set<Node> children = container.getChildrens();
                if (children.isEmpty()) {
                    removeLeafNode(node);
                } else {
                    for (Node child : children) {
                        deletePersistentNode(child);
                    }
                    removeLeafNode(node);
                }
                
            } else {
                removeLeafNode(node);
            }
        }
    }
    
    /*
     * Removes a node with no children
     */
    private void removeLeafNode (final Node node) {
        if (node != null) {
            this.nodeDao.delete(node);
        }
    }

    @Override
    public Object getResult() {
        return null;
    }

}
