package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IUserControlDao;
import esac.archive.vospace.persistence.model.controls.User;
import esac.archive.vospace.persistence.util.DaoConstants;

public class UserControlDaoImpl extends AbstractHibernateDAOImpl implements
        IUserControlDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UserControlDaoImpl.class);
    
    @Override
    public User getEmptyUserControl() {
        return new User();
    }

    @Override
    public Class<User> getPersistentClass() {
        return User.class;
    }

    @Override
    public User getUserControlById(Integer userId) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_USER_ID, userId));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        } else if (logList.size() > 1) {
            String errMsg = "More than 1 result found for UserControlDaoImpl.queryLogById ("
                    + userId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (User) logList.get(0);
    }

    @Override
    public User getUserControlByUname(String uname) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_USER_UNAME, uname));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        } else if (logList.size() > 1) {
            String errMsg = "More than 1 result found for UserControlDaoImpl.queryLogByUname ("
                    + uname + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (User) logList.get(0);
    }

}
