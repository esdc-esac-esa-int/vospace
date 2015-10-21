package esavo.vospace.dl.querymanager.cmd.ingestion;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class IngestUserCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestUserCommandTest.class);
    
    /**
     * Initialize query manager services before instantiating this class.
     */
    @BeforeClass
    public static final void runBeforeClass() {
        initializeVOspaceQueryManagerServices();
    }
    
    /**
     * Public constructor.
     */
    public IngestUserCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoIngestUser() {
        try {
            log.info("Into IngestUserCommandTest."
                    + "testDoIngestUser()");

            UserTO userTO = createUserTO();
            this.getQueryManager().doInsertUser(userTO);

            // Clean up

            log.debug("End of IngestUserCommandTest."
                    + "testDoIngestUser()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    private UserTO createUserTO() {
        UserTO userTO = new UserTO();
        
        userTO.setName("platest");
        userTO.setFullName("PLA Test");
        userTO.setMail("platest@gmail.com");
        userTO.setCreationDate(DUMMY_DATE_VALUE);
        userTO.setQuota(DUMMY_LONG_VALUE);
        userTO.setQLimit(DUMMY_LONG_VALUE);
        
        return userTO;
    }
}
