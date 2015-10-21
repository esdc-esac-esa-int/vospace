/**
 * 
 */
package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.util.DaoConstants;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public class UsersDaoImpl extends AbstractHibernateDAOImpl implements IUsersDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UsersDaoImpl.class);
    
    @Override
    public Class<Users> getPersistentClass() {
        return Users.class;
    }
    
    @Override
    public Users getEmptyUsers() {
        return new Users();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Users> getUsers() {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Users.class);
        
        List<?> userList = this.executeCriteriaQuery(criteria);
        if ((userList == null) || (userList.size() == 0)) {
            return null;
        }
        return  (List<Users>) userList;
    }

    @Override
    public Users queryUserByName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("UsersDaoImpl.queryUserByName (" + name
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Users.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_USERS_NAME, name));
        
        List<?> userList = this.executeCriteriaQuery(criteria);
        if ((userList == null) || (userList.size() == 0)) {
            return null;
        } else if (userList.size() > 1) {
            String errMsg = "More than 1 result found for UsersDaoImpl.queryUserByName("
                    + name + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (Users) userList.get(0);
    }

    @Override
    public Users queryUserById(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("UsersDaoImpl.queryUserById (" + id
                    + "does not accept [null] as input");
        }
        /*Users user = getEmptyUsers();
        user.setUserOid(id);
        List<?> userList = this.findByExample(user);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Users.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_USERS_ID, id));
        List<?> userList = executeCriteriaQuery(criteria);
        
        if ((userList == null) || (userList.size() == 0)) {
            return null;
        } else if (userList.size() > 1) {
            String errMsg = "More than 1 result found for UsersDaoImpl.queryUserById("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (Users) userList.get(0);
    }

    @Override
    public Users updateQuotaWithSize(Long size) {
        
        
        
        return null;
    }
    
    

}
