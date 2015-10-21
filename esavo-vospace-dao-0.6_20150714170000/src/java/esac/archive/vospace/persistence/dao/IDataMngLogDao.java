package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.controls.DataMngLog;
import esac.archive.vospace.persistence.model.controls.User;

/**
 * Interface to define methods used by DataMngLog DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public interface IDataMngLogDao extends IHibernateDAO {

    /**
     * Retrieves empty DataManagementLog
     * @return DataManagementLog
     */
    DataMngLog getEmptyDataManagementLog();
    
    /**
     * Retrieves the DataMngLog by Id.
     * @param logId
     * @return DataMngLog if exists.
     */
    DataMngLog queryLogById(Integer logId);
    
    /**
     * Retrieves the last log of a given User.
     * @param user
     * @return DataMngLog if exists.
     */
    DataMngLog queryLastLogOfUser(User user);
}
