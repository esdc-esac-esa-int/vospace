package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.controls.Group;

/**
 * Interface to define methods used by GroupControl DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public interface IGroupControlDao extends IHibernateDAO {

    /**
     * Retrieves empty GroupControl
     * @return GroupControl
     */
    Group getEmptyGroupControl();
    
    /**
     * Retrieves the Group by Id.
     * @param logId
     * @return Group if exists.
     */
    Group getGroupControlById (Integer groupId);
}
