package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IAccessLogDao;
import esac.archive.vospace.persistence.model.controls.AccessLog;
import esac.archive.vospace.persistence.model.controls.User;
import esac.archive.vospace.persistence.util.DaoConstants;

public class AccessLogDaoImpl extends AbstractHibernateDAOImpl implements
        IAccessLogDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(AccessLogDaoImpl.class);
    
    @Override
    public AccessLog getEmptyAccessLog() {
        return new AccessLog();
    }

    @Override
    public Class<AccessLog> getPersistentClass() {
        return AccessLog.class;
    }

    @Override
    public AccessLog queryLogById(Integer logId) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(AccessLog.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_ACCESS_ID, logId));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        } else if (logList.size() > 1) {
            String errMsg = "More than 1 result found for AccessLog.queryLogById ("
                    + logId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (AccessLog) logList.get(0);
    }

    @Override
    public AccessLog queryLastLogOfUser(User user) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(AccessLog.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_USER, user));
        criteria.addOrder(Order.desc(DaoConstants.DB_CONTROLS_ACCESS_ID));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        }
        
        AccessLog log = (AccessLog) logList.get(0);
        if (log.getLogoutTimestamp() != null) {
            return null;
        }
        
        return log;
    }

}
