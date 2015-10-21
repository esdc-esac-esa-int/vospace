package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.ILinkNodeDao;
import esac.archive.vospace.persistence.model.LinkNode;
import esac.archive.vospace.persistence.util.DaoConstants;

public class LinkNodeDaoImpl extends AbstractHibernateDAOImpl implements
		ILinkNodeDao {

	/**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(LinkNode.class);
	
	@Override
	public LinkNode getEmptyLinkNode() {
		return new LinkNode();
	}
	
	@Override
    public Class<LinkNode> getPersistentClass() {
        return LinkNode.class;
    }

	@SuppressWarnings("unchecked")
    @Override
	public List<LinkNode> queryLinkNodeByTarget(String target) {
		if (target == null) {
            throw new IllegalArgumentException("LinkNodeDaoImpl.queryLinkNodeByTarget (" + target
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(LinkNode.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_TARGET, target));
        
        List<?> linkList = this.executeCriteriaQuery(criteria);
        if ((linkList == null) || (linkList.size() == 0)) {
            return null;
        }
        
        return (List<LinkNode>) linkList;
	}

    @Override
    public LinkNode queryLinkNodeById(Integer nodeId) {
        if (nodeId == null) {
            throw new IllegalArgumentException("LinkNodeDaoImpl.queryLinkNodeById (" + nodeId
                    + "does not accept [null] as input");
        }
        /*LinkNode node = getEmptyLinkNode();
        node.setNodeOid(nodeId);        
        List<?> linkList = this.findByExample(node);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(LinkNode.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_ID, nodeId));
        List<?> linkList = executeCriteriaQuery(criteria);
        
        if ((linkList == null) || (linkList.size() == 0)) {
            return null;
        } else if (linkList.size() > 1) {
            String errMsg = "More than 1 result found for LinkNodeDaoImpl.queryLinkNodeById("
                    + nodeId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        
        return (LinkNode) linkList.get(0);
    }

}
