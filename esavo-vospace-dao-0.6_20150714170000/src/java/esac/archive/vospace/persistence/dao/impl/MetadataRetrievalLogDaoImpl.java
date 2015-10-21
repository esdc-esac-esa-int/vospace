package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IMetadataRetrievalLogDao;
import esac.archive.vospace.persistence.model.controls.MetadataRetrievalLog;
import esac.archive.vospace.persistence.model.controls.User;
import esac.archive.vospace.persistence.util.DaoConstants;

public class MetadataRetrievalLogDaoImpl extends AbstractHibernateDAOImpl
        implements IMetadataRetrievalLogDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(MetadataRetrievalLogDaoImpl.class);
    
    @Override
    public MetadataRetrievalLog getEmptyMetadataRetrievalLog() {
        return new MetadataRetrievalLog();
    }

    @Override
    public Class<MetadataRetrievalLog> getPersistentClass() {
        return MetadataRetrievalLog.class;
    }

    @Override
    public MetadataRetrievalLog queryLogById(Integer logId) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(MetadataRetrievalLog.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_METADATA_RETRIEVAL_ID, logId));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        } else if (logList.size() > 1) {
            String errMsg = "More than 1 result found for MetadataRetrievalLog.queryLogById ("
                    + logId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (MetadataRetrievalLog) logList.get(0);
    }

    @Override
    public MetadataRetrievalLog queryLastLogOfUser(User user) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(MetadataRetrievalLog.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_USER, user));
        criteria.addOrder(Order.desc(DaoConstants.DB_CONTROLS_METADATA_RETRIEVAL_ID));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        }
        
        MetadataRetrievalLog log = (MetadataRetrievalLog) logList.get(0);
        if (log.getStopTimestamp() != null) {
            return null;
        }
        
        return (MetadataRetrievalLog) logList.get(0);
    }

}
