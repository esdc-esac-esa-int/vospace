package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.controls.MetadataRetrievalLog;
import esac.archive.vospace.persistence.model.controls.User;

/**
 * Interface to define methods used by MetadataRetrievalLog DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public interface IMetadataRetrievalLogDao extends IHibernateDAO {

    /**
     * Retrieves empty MetadataRetrievalLog
     * @return MetadataRetrievalLog
     */
    MetadataRetrievalLog getEmptyMetadataRetrievalLog();
    
    /**
     * Retrieves the MetadataRetrievalLog by Id.
     * @param logId
     * @return MetadataRetrievalLog if exists.
     */
    MetadataRetrievalLog queryLogById (Integer logId);
    
    /**
     * Retrieves the last log of a given User.
     * @param user
     * @return MetadataRetrievalLog if exists.
     */
    MetadataRetrievalLog queryLastLogOfUser(User user);
}
