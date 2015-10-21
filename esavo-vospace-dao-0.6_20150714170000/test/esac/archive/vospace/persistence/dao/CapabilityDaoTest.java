package test.esac.archive.vospace.persistence.dao;

import static org.junit.Assert.assertNotNull;
import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.junit.Test;

import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.VospaceDaoServiceImpl;
import esac.archive.vospace.persistence.dao.IVoCapabilityDao;
import esac.archive.vospace.persistence.model.VoCapability;
import test.esac.archive.vospace.persistence.HstObjectFactory;
import test.esac.archive.vospace.persistence.LazyLoadingTestCase;

public class CapabilityDaoTest extends LazyLoadingTestCase {

    /** Logger. */
    private static Logger logger = Logger.getLogger(CapabilityDaoTest.class);
    
    private IVoCapabilityDao voCapability = ((VospaceDaoServiceImpl)DAOServiceProvider.getDaoService())
            .getVoCapabilityDAO();
    
    public CapabilityDaoTest() {
        super();
    }
    
    /**
     * Test insert and delete voCapability.
     */
    @Test
    public final void testInsertAndDeleteVoCapability() {
        logger.info("Into CapabilityDaoTest.testInsertAndDeleteVoCapability()");
        try {
            VoCapability capability = HstObjectFactory.getInstance().insertAndReturnCapabilityObject();
            HstObjectFactory.getInstance().deleteTestVoCapabilityFromDatabase();;
        } catch (Exception e) {
            logger.error("Exception in CapabilityDaoTest.testInsertAndDeleteVoCapability()", e);
            throw new AssertionFailedError();
        }

        logger.info("End of CapabilityDaoTest.testInsertAndDeleteVoCapability()");
    }
    
    /**
     * Test insert, query and delete voCapability.
     */
    @Test
    public final void queryVoCapability() {

        logger.info("Into CapabilityDaoTest.queryVoCapability()");
        try {

            VoCapability capability = HstObjectFactory.getInstance().insertAndReturnCapabilityObject();

            // Let's query for it
            VoCapability voCapability = this.voCapability.queryVoCapabilityById(capability.getVoCapabilityOid());
            
            System.out.println("Oid " + capability.getVoCapabilityOid());
            
            logger.info("Capability [" + voCapability + "]");

            assertNotNull(voCapability);

            // Delete artifact
            HstObjectFactory.getInstance().deleteTestVoCapabilityFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in CapabilityDaoTest.queryVoCapability()", e);
            throw new AssertionFailedError();
        }
        logger.info("End of CapabilityDaoTest.queryVoCapability()");
    }
}
