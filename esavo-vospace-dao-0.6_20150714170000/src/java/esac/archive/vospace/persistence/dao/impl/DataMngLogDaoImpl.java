package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IDataMngLogDao;
import esac.archive.vospace.persistence.model.controls.DataMngLog;
import esac.archive.vospace.persistence.model.controls.User;
import esac.archive.vospace.persistence.util.DaoConstants;

public class DataMngLogDaoImpl extends AbstractHibernateDAOImpl implements
        IDataMngLogDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(DataMngLogDaoImpl.class);
    
    @Override
    public DataMngLog getEmptyDataManagementLog() {
        return new DataMngLog();
    }

    @Override
    public Class<DataMngLog> getPersistentClass() {
        return DataMngLog.class;
    }

    @Override
    public DataMngLog queryLogById(Integer logId) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(DataMngLog.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_DATA_MNG_ID, logId));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        } else if (logList.size() > 1) {
            String errMsg = "More than 1 result found for DataMngLogImpl.queryLogById ("
                    + logId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (DataMngLog) logList.get(0);
    }

    @Override
    public DataMngLog queryLastLogOfUser(User user) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(DataMngLog.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_USER, user));
        criteria.addOrder(Order.desc(DaoConstants.DB_CONTROLS_DATA_MNG_ID));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        }
        
        DataMngLog log = (DataMngLog) logList.get(0);
        if (log.getStopTimestamp() != null) {
            return null;
        }
        
        return (DataMngLog) logList.get(0);
    }

}
