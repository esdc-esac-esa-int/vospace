/**
 * 
 */
package esac.archive.vospace.persistence.dao;

import java.util.List;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.Groups;
import esac.archive.vospace.persistence.model.Users;

/**
 * Interface to define methods used by GroupsNode DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public interface IGroupsDao extends IHibernateDAO {
    
    /**
     * Returns an Groups Class object, used by Session and Criteria object.
     * @return Group class
     */
    Groups getEmptyGroup();
    
    /**
     * Query Group by Id.
     * @param id
     * @return Group if exist.
     */
    Groups queryGroupById(Integer id);
    
    /**
     * Query Groups by Owner
     * @param ownerId
     * @return List of Groups
     */
    List < Groups > queryGroupByOwner(Users manager);
    
    /**
     * Query Groups by Name
     * @param name
     * @return List of Groups
     */
    Groups queryGroupByOwnerAndName(Users manager, String name);
}
