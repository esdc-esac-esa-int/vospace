package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.controls.User;

/**
 * Interface to define methods used by UserControl DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public interface IUserControlDao extends IHibernateDAO {
    
    /**
     * Retrieves empty UserControl
     * @return TransferLog
     */
    User getEmptyUserControl();
    
    /**
     * Retrieves the User by Id.
     * @param logId
     * @return User if exists.
     */
    User getUserControlById (Integer userId);
    
    /**
     * Retrieves the User by user name.
     * @param mail
     * @return User if exists.
     */
    User getUserControlByUname (String mail);
}
