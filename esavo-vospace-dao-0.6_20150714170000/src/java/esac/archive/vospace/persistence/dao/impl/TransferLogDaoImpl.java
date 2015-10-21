package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.ITransferLogDao;
import esac.archive.vospace.persistence.model.controls.TransferLog;
import esac.archive.vospace.persistence.model.controls.User;
import esac.archive.vospace.persistence.util.DaoConstants;

public class TransferLogDaoImpl extends AbstractHibernateDAOImpl implements
        ITransferLogDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(TransferLogDaoImpl.class);
    
    @Override
    public TransferLog getEmptyTransferLog() {
        return new TransferLog();
    }

    @Override
    public Class<TransferLog> getPersistentClass() {
        return TransferLog.class;
    }

    @Override
    public TransferLog queryLogById(Integer logId) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(TransferLog.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_TRANSFER_ID, logId));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        } else if (logList.size() > 1) {
            String errMsg = "More than 1 result found for TransferLogDaoImpl.queryLogById ("
                    + logId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (TransferLog) logList.get(0);
    }

    @Override
    public TransferLog queryLastLogOfUser(User user) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(TransferLog.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_USER, user));
        criteria.addOrder(Order.desc(DaoConstants.DB_CONTROLS_TRANSFER_ID));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        }
        
        TransferLog log = (TransferLog) logList.get(0);
        if (log.getStopTimestamp() != null) {
            return null;
        }
        
        return (TransferLog) logList.get(0);
    }

}
