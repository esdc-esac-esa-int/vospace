package test.esac.archive.vospace.persistence.dao;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.junit.Test;

import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.VospaceDaoServiceImpl;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.model.Users;
import test.esac.archive.vospace.persistence.HstObjectFactory;
import test.esac.archive.vospace.persistence.LazyLoadingTestCase;

/**
 * JUnit UserDaoTest
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public class UserDaoTest extends LazyLoadingTestCase{
    
    /** Logger. */
    private static Logger logger = Logger.getLogger(UserDaoTest.class);
    
    private IUsersDao usersDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /**
     * Main constructor.
     */
    public UserDaoTest() {
        super();
    }
    
    /**
     * Test insert and delete user.
     */
    @Test
    public final void testInsertAndDeleteUser() {
        logger.info("Into UsersDaoTest.testInsertAndDeleteUser()");
        try {
            Users users = HstObjectFactory.getInstance().insertAndReturnUsersObject();
            HstObjectFactory.getInstance().deleteTestUsersFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in UsersDaoTest.testInsertAndDeleteUser()", e);
            throw new AssertionFailedError();
        }

        logger.info("End of UsersDaoTest.testInsertAndDeleteUser()");
    }
    
    /**
     * Test insert, query and delete user.
     */
    @Test
    public final void queryUsers() {

        logger.info("Into UsersDaoTest.queryUsers()");
        try {

            //Users users = HstObjectFactory.getInstance().insertAndReturnUsersObject();

            // Let's query for it
            //Users insertedUsers = this.usersDao.queryUserById(users.getUserOid());
            List<Users> userList = this.usersDao.getUsers();
            
            for (Users user : userList) {
                System.out.println("User name: " + user.getName());
            }
            /*System.out.println("Oid " + users.getUserOid());
            
            logger.info("User [" + insertedUsers + "]");
            logger.info("User [" + insertedUsers.getUserOid() + "]");
            logger.info("User [" + insertedUsers.getName() + "]");
            logger.info("User [" + insertedUsers.getFullName() + "]");
            logger.info("User [" + insertedUsers.getMail() + "]");
            logger.info("User [" + insertedUsers.getCreationDate() + "]");

            assertNotNull(insertedUsers);*/

            // Delete artifact
            HstObjectFactory.getInstance().deleteTestUsersFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in UsersDaoTest.queryUsers()", e);
            throw new AssertionFailedError();
        }
        logger.info("End of UsersDaoTest.queryUsers()");
    }
}
