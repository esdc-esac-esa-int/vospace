package esac.archive.vospace.persistence.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoProperty;
import esac.archive.vospace.persistence.util.DaoConstants;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class NodeDaoImpl extends AbstractHibernateDAOImpl implements INodeDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(NodeDaoImpl.class);
    
    /**
     * Returns a Node Class object, used by Session and Criteria object.
     * @return Node class
     */
    @Override
    public final Class<Node> getPersistentClass() {
        return Node.class;
    }
    
    @Override
    public Node getEmptyNode() {
        return new Node();
    }

	@SuppressWarnings("unchecked")
    @Override
	public List<Node> queryNodesByOwnerId(Integer ownerId) {
		if (ownerId == null) {
            throw new IllegalArgumentException("NodeDaoImpl.queryNodesByOwnerId (" + ownerId
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Node.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_OWNER, ownerId));
        criteria.addOrder(Order.asc(DaoConstants.DB_NODE_NAME));
        
        List<?> nodeList = this.executeCriteriaQuery(criteria);
        if ((nodeList == null) || (nodeList.size() == 0)) {
            return null;
        }
        return (List<Node>) nodeList;
	}

    @Override
    @SuppressWarnings("unchecked")
    public List<Node> queryNodeByName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("NodeDaoImpl.queryNodeByVospaceAndName (" + name
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Node.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_NAME, name));
        criteria.addOrder(Order.asc(DaoConstants.DB_NODE_ID));
        
        List<?> nodeList = this.executeCriteriaQuery(criteria);
        if ((nodeList == null) || (nodeList.size() == 0)) {
            return null;
        }
        return (List<Node>) nodeList;
    }
    
    @Override
    public String queryNodeURIById(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("NodeDaoImpl.queryNodeURIById (" + id
                    + "does not accept [null] as input");
        }
        
        Node node = queryNodeById(id);
        if (node == null) {
            return "";
        }
        
        String uriPath = node.getName();        
        boolean root = false;
        Integer nodeId = id;
        while (!root) {
            if (nodeId != id) { //is not the first iteration
                node = queryNodeById(nodeId);
            }
            Node parent = node.getContainerNode();
            if (parent == null) {
                root = true;
            } else {
                nodeId = parent.getNodeOid();
                uriPath = parent.getName() + "/" + uriPath;
            }
        }
        
        return uriPath;
    }
    
    /**
     * Retrieve the Node with its uri path
     * @param path a/b/c/d
     * @return node with the given uri path
     */
    @Override
    public Node queryNodeByUri(final String uriPath) {
        Node node = null;
        
        String[] pathSplit = uriPath.split(File.separator);
        List<String> path = new ArrayList<String>();
        for (String nodeName : pathSplit) {
            path.add(nodeName);
        }
        
        if (path.isEmpty()) {
            return node;
        }
        
        int last = path.size()-1;
        List<Node> candidates = queryNodeByName(path.get(last));
        
        if (candidates ==  null || (candidates != null && candidates.isEmpty())) {
            return node;
        }
        
        path.remove(last);        
        node = queryNodeByUriList(path, candidates);
        
        return node;
    }
    
    /*
     * Retrieve the node with the given uri
     * Recursive method
     */
    private Node queryNodeByUriList(List<String> path, List<Node> resultList) {
        
        Node result = null;
        List<String> originalPath = new ArrayList<String>(path);
        for (Node candidate : resultList) {
            path = new ArrayList<String>(originalPath);
            boolean check = checkFamily(path, candidate);
            if (check) {
                result = candidate;
            }
        }
        
        return result;
    }
    
    private boolean checkFamily (List<String> path, Node node) {
        boolean checkFamily = true;
        if (node != null) {
            
            ContainerNode nodeParent = node.getContainerNode();                        
            if (path.isEmpty()) {
                if (nodeParent != null) {
                    return false;
                } else return true;
            } else {
                if (nodeParent == null) {
                    return false;
                }
            }
            
            String myParent = nodeParent.getName();            
            int last = path.size()-1;
            String pathName = path.get(last);
            
            if (myParent.compareTo(pathName) != 0) {
                checkFamily = false;
            } else {
                path.remove(last);
                node = nodeParent;
                checkFamily = checkFamily(path, node);
            }
        } else
            checkFamily = false;
        return checkFamily;
    }

    @Override
    public Node queryNodeById(Integer nodeId) {
        if (nodeId == null) {
            throw new IllegalArgumentException("NodeDaoImpl.queryNodeById (" + nodeId
                    + "does not accept [null] as input");
        }
        
        /*Node node = getEmptyNode();
        node.setNodeOid(nodeId);
        List<?> nodeList = this.findByExample(node);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Node.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_ID, nodeId));
        List<?> nodeList = executeCriteriaQuery(criteria);
        
        if ((nodeList == null) || (nodeList.size() == 0)) {
            return null;
        } else if (nodeList.size() > 1) {
            String errMsg = "More than 1 result found for NodeDaoImpl.queryNodeById ("
                    + nodeId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (Node) nodeList.get(0);
    }

    /**
     * 
     */
    @Override
    public synchronized long queryUserQuota (Users user, VoProperty voProperty) {
        
        if (user == null) {
            throw new IllegalArgumentException("NodeDaoImpl.queryQuotaUser (" 
                    + " does not accept [null] as input");
        }
        
        DetachedCriteria criteria = DetachedCriteria.forClass(NodeProperty.class,"nodeProperty");
        criteria.createAlias("nodeProperty.node","node");
        criteria.add(Restrictions.eq("node.users", user));
        criteria.add(Restrictions.eq("nodeProperty.voProperty", voProperty));
        
        @SuppressWarnings("unchecked")
        List<NodeProperty> nodeList = (List<NodeProperty>) executeCriteriaQuery(criteria);
        if ((nodeList == null) || (nodeList.size() == 0)) {
            return 0;
        }
        
        long total = 0;
        for (NodeProperty property : nodeList) {
            total += Long.parseLong(property.getValue());
        }
        
        return total;
    }

    @Override
    public Node queryNodeByParentAndName(ContainerNode parent, String nodeName) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Node.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_PARENT, parent));
        @SuppressWarnings("unchecked")
        List<Node> nodeList = (List<Node>) executeCriteriaQuery(criteria);
        
        Node result = null;        
        for (Node node : nodeList) {
            if (node.getName().compareTo(nodeName) == 0) {
                result = node;
            }
        }
        
        return result;
    }
}
