/**
 * 
 */
package esac.archive.vospace.persistence.dao;

import java.util.List;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.Users;

/**
 * Interface to define methods used by Users DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public interface IUsersDao extends IHibernateDAO {

    /**
     * Returns an Users Class object, used by Session and Criteria object.
     * @return Users class
     */
    Users getEmptyUsers();
    
    /**
     * Retrieves list of users of the system.
     * @return list of users.
     */
    List < Users > getUsers ();
    
    /**
     * Retrieves User by name.
     * @param name of Users
     * @return Users if found.
     */
    Users queryUserByName (String name);
    
    /**
     * Retrieves User by Id.
     * @param id of Users
     * @return Users if found.
     */
    Users queryUserById (Integer id);
    
    Users updateQuotaWithSize (Long soze);
}
