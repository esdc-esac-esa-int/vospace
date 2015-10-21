package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IDataNodeDao;
import esac.archive.vospace.persistence.model.DataNode;
import esac.archive.vospace.persistence.util.DaoConstants;

public class DataNodeDaoImpl extends AbstractHibernateDAOImpl implements
		IDataNodeDao {

	/**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(DataNode.class);
	
	@Override
	public DataNode getEmptyDataNode() {
		return new DataNode();
	}

	@Override
	public Class<DataNode> getPersistentClass() {
		return DataNode.class;
	}

    @Override
    public DataNode queryDataNodeById(Integer nodeId) {
        if (nodeId == null) {
            throw new IllegalArgumentException("DataNodeDaoImpl.queryDataNodeById (" + nodeId
                    + "does not accept [null] as input");
        }
        /*DataNode node = getEmptyDataNode();
        node.setNodeOid(nodeId);
        List<?> dataList = this.findByExample(node);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(DataNode.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_ID, nodeId));
        List<?> dataList = executeCriteriaQuery(criteria);
        
        if ((dataList == null) || (dataList.size() == 0)) {
            return null;
        } else if (dataList.size() > 1) {
            String errMsg = "More than 1 result found for DataNodeDaoImpl.queryDataNodeById("
                    + nodeId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        
        return (DataNode) dataList.get(0);
    }

}
