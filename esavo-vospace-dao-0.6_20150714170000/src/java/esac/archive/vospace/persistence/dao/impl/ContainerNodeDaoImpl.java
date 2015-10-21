package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IContainerNodeDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.util.DaoConstants;

public class ContainerNodeDaoImpl extends AbstractHibernateDAOImpl implements
		IContainerNodeDao {

	/**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(ContainerNode.class);
	
	@Override
	public ContainerNode getEmptyContainerNode() {
		return new ContainerNode();
	}

	@Override
	public Class<ContainerNode> getPersistentClass() {
		return ContainerNode.class;
	}

    @Override
    public ContainerNode queryContainerNodeById(Integer nodeId) {
        if (nodeId == null) {
            throw new IllegalArgumentException("ContainerNodeDaoImpl.queryContainerNodeById (" + nodeId
                    + "does not accept [null] as input");
        }
        /*ContainerNode node = getEmptyContainerNode();
        node.setNodeOid(nodeId);
        
        List<?> containerList = this.findByExample(node);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(ContainerNode.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_ID, nodeId));
        List<?> containerList = executeCriteriaQuery(criteria);
        
        if ((containerList == null) || (containerList.size() == 0)) {
            return null;
        } else if (containerList.size() > 1) {
            String errMsg = "More than 1 result found for ContainerNodeDaoImpl.queryContainerNodeById("
                    + nodeId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        
        ContainerNode container = ((ContainerNode) containerList.get(0));
        container.getChildrens();
        
        return container;
    }

}
