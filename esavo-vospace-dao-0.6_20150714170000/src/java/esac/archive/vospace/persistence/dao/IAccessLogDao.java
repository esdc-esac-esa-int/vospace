package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.controls.AccessLog;
import esac.archive.vospace.persistence.model.controls.User;

/**
 * Interface to define methods used by AccessLog DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public interface IAccessLogDao extends IHibernateDAO {

    /**
     * Returns an AccessLog Class object, used by Session and Criteria object.
     * @return AccessLog class
     */
    AccessLog getEmptyAccessLog();
    
    /**
     * Retrieves the AccessLog by Id.
     * @param logId
     * @return AccessLog if exists.
     */
    AccessLog queryLogById(Integer logId);
    
    /**
     * Retrieves the last log of a given User.
     * @param user
     * @return AccessLog if exists.
     */
    AccessLog queryLastLogOfUser(User user);
}
