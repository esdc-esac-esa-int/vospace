package test.esac.archive.vospace.persistence.dao;

import static org.junit.Assert.assertNotNull;
import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.junit.Test;

import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.VospaceDaoServiceImpl;
import esac.archive.vospace.persistence.dao.IGroupsDao;
import esac.archive.vospace.persistence.model.Groups;
import test.esac.archive.vospace.persistence.HstObjectFactory;
import test.esac.archive.vospace.persistence.LazyLoadingTestCase;

public class GroupDaoTest extends LazyLoadingTestCase {

    /** Logger. */
    private static Logger logger = Logger.getLogger(GroupDaoTest.class);
    
    private IGroupsDao groupDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getGroupsDAO();
    
    public GroupDaoTest() {
        super();
    }
    
    /**
     * Test insert and delete Group.
     */
    @Test
    public final void testInsertAndDeleteGroup() {
        logger.info("Into GroupDaoTest.testInsertAndDeleteGroup()");
        try {
            Groups group = HstObjectFactory.getInstance().insertAndReturnGroupsObject();
            HstObjectFactory.getInstance().deleteTestGroupsFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in GroupDaoTest.testInsertAndDeleteGroup()", e);
            throw new AssertionFailedError();
        }

        logger.info("End of GroupDaoTest.testInsertAndDeleteGroup()");
    }
    
    /**
     * Test insert, query and delete group.
     */
    @Test
    public final void queryGroup() {

        logger.info("Into GroupDaoTest.queryGroup()");
        try {

            Groups group = HstObjectFactory.getInstance().insertAndReturnGroupsObject();

            // Let's query for it
            Groups insertedGroup = this.groupDao.queryGroupById(group.getGroupOid());
            
            System.out.println("Oid " + group.getGroupOid());
            
            logger.info("Group [" + insertedGroup + "]");
            logger.info("Group [" + insertedGroup.getName() + "]");

            assertNotNull(insertedGroup);

            // Delete artifact
            HstObjectFactory.getInstance().deleteTestGroupsFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in GroupDaoTest.queryGroup()", e);
            throw new AssertionFailedError();
        }
        logger.info("End of GroupDaoTest.queryGroup()");
    }
}
