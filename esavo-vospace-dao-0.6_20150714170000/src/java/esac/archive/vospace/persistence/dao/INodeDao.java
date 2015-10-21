/**
 * 
 */
package esac.archive.vospace.persistence.dao;

import java.util.List;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoProperty;

/**
 * Interface to define methods used by Node DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public interface INodeDao extends IHibernateDAO {
    
    /**
     * Returns an Node Class object, used by Session and Criteria object.
     * @return Node class
     */
    Node getEmptyNode();
    
    /**
     * Retrieves the Node by Id.
     * @param nodeId
     * @return Node if exists.
     */
    Node queryNodeById(Integer nodeId);
    
    /**
     * Retrieves the node by vospace and name.
     * @param name
     * @return List<Node> if found.
     */
    List<Node> queryNodeByName (String name);
    
    /**
     * Retrieves the node by vospace and uri.
     * @param uriPath
     * @return node if found
     */
    Node queryNodeByUri(String uriPath);
    
    /**
     * Retrieve the uri path from a node id.
     * @param id
     * @return uri path of the node.
     */
    String queryNodeURIById(final Integer id);
    
    /**
     * Query nodes by owner id.
     * @param ownerId id of user owner
     * @return List <Nodes> if found.
     */
    List<Node> queryNodesByOwnerId (Integer ownerId);
    
    /**
     * Query User Quota.
     * @param user
     * @return quota
     */
    long queryUserQuota (Users user, VoProperty uriProperty);
    
    Node queryNodeByParentAndName (ContainerNode parent, String nodeName);
}
