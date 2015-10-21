package esavo.vospace.dl.querymanager.cmd.ingestion;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class UpdateUserQuotaCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UpdateUserQuotaCommandTest.class);
    
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
    public UpdateUserQuotaCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoUpdateUserQuotaCommandTest() {
        try {
            log.info("Into UpdateUserQuotaCommandTest."
                    + "testDoUpdateUserQuotaCommandTest()");

            this.getQueryManager().doUpdateUserQuota("snieto", 4096);
            
            
            log.debug("End of UpdateUserQuotaCommandTest."
                    + "testDoUpdateUserQuotaCommandTest()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
