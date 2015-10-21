package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.controls.TransferLog;
import esac.archive.vospace.persistence.model.controls.User;

/**
 * Interface to define methods used by TransferLog DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public interface ITransferLogDao extends IHibernateDAO {
    
    /**
     * Retrieves empty TransferLog
     * @return TransferLog
     */
    TransferLog getEmptyTransferLog();
    
    /**
     * Retrieves the TransferLog by Id.
     * @param logId
     * @return TransferLog if exists.
     */
    TransferLog queryLogById(Integer logId);
    
    /**
     * Retrieves the last log of a given User.
     * @param user
     * @return TransferLog if exists.
     */
    TransferLog queryLastLogOfUser(User user);
}
