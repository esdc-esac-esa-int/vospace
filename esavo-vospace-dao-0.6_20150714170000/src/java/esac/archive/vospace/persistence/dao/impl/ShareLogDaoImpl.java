package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IShareLogDao;
import esac.archive.vospace.persistence.model.controls.ShareLog;
import esac.archive.vospace.persistence.util.DaoConstants;

public class ShareLogDaoImpl extends AbstractHibernateDAOImpl implements
        IShareLogDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(ShareLogDaoImpl.class);
    
    @Override
    public ShareLog getEmptyShareLog() {
        return new ShareLog();
    }

    @Override
    public Class<ShareLog> getPersistentClass() {
        return ShareLog.class;
    }

    @Override
    public ShareLog queryLogById(Integer logId) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(ShareLog.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_SHARE_ID, logId));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        } else if (logList.size() > 1) {
            String errMsg = "More than 1 result found for ShareLogDaoImpl.queryLogById ("
                    + logId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (ShareLog) logList.get(0);
    }

}
