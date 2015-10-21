package test.esac.archive.vospace.persistence.dao.controls;

import static org.junit.Assert.assertNotNull;
import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.junit.Test;

import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.VospaceDaoServiceImpl;
import esac.archive.vospace.persistence.dao.IGroupControlDao;
import esac.archive.vospace.persistence.dao.IGroupsDao;
import esac.archive.vospace.persistence.model.Groups;
import esac.archive.vospace.persistence.model.controls.Group;
import test.esac.archive.vospace.persistence.HstObjectFactory;
import test.esac.archive.vospace.persistence.LazyLoadingTestCase;

public class GroupControlDaoTest extends LazyLoadingTestCase {

    /** Logger. */
    private static Logger logger = Logger.getLogger(GroupControlDaoTest.class);
    
    private IGroupControlDao groupDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getGroupControlDao();
    
    public GroupControlDaoTest() {
        super();
    }
    
    /**
     * Test insert and delete Group.
     */
    @Test
    public final void testInsertAndDeleteGroup() {
        logger.info("Into GroupControlDaoTest.testInsertAndDeleteGroup()");
        try {
            Group group = HstObjectFactory.getInstance().insertAndReturnGroupControlObject();
            HstObjectFactory.getInstance().deleteTestGroupsFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in GroupControlDaoTest.testInsertAndDeleteGroup()", e);
            throw new AssertionFailedError();
        }

        logger.info("End of GroupControlDaoTest.testInsertAndDeleteGroup()");
    }
    
    /**
     * Test insert, query and delete group.
     */
    @Test
    public final void queryGroup() {

        logger.info("Into GroupControlDaoTest.queryGroup()");
        try {

            Group group = HstObjectFactory.getInstance().insertAndReturnGroupControlObject();

            // Let's query for it
            Group insertedGroup = this.groupDao.getGroupControlById(group.getGroupOid());
            
            System.out.println("Oid " + group.getGroupOid());
            
            logger.info("Group [" + insertedGroup + "]");
            logger.info("Group [" + insertedGroup.getName() + "]");

            assertNotNull(insertedGroup);

            // Delete artifact
            HstObjectFactory.getInstance().deleteTestGroupsFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in GroupControlDaoTest.queryGroup()", e);
            throw new AssertionFailedError();
        }
        logger.info("End of GroupControlDaoTest.queryGroup()");
    }
}
