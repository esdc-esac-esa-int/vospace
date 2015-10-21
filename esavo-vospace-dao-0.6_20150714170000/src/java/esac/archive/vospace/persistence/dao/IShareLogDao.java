package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.controls.ShareLog;

/**
 * Interface to define methods used by ShareLog DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public interface IShareLogDao extends IHibernateDAO { 

    /**
     * Retrives empty ShareLog
     * @return ShareLog
     */
    ShareLog getEmptyShareLog();
    
    /**
     * Retrieves the ShareLog by Id.
     * @param logId
     * @return ShareLog if exists.
     */
    ShareLog queryLogById (Integer logId);
}
