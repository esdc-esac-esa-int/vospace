package esavo.vospace.dl.querymanager.cmd.controls;

import java.util.Date;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.control.AccessLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class IngestAccessLogCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestAccessLogCommandTest.class);
    
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
    public IngestAccessLogCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoIngestAccessLog() {
        try {
            log.info("Into IngestAccessLogCommandTest."
                    + "testDoIngestAccessLog()");

            createLog();
            //updateLog();

            //log.debug("End of IngestAccessLogCommandTest."
            //        + "testDoIngestAccessLog()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    private void createLog () {
        try {
            AccessLogTO log = createAccessLog();
            this.getQueryManager().doInsertAccessLog(log);
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    private void updateLog () {
        try {
            AccessLogTO log = updateAccessLog();
            this.getQueryManager().doInsertAccessLog(log);
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    private AccessLogTO updateAccessLog () {
        
        AccessLogTO log = createAccessLog();
        log.setLogoutTimestamp(new Date());
        
        return log;
    }
    
    private UserControlTO createUserContro () {
        UserControlTO u = new UserControlTO();        
        
        u.setName("posuna");
        u.setSurname("osuna");
        u.setMail("pedro.osuna@sciops.esa.int");
        u.setRegisterDate(new Date());
        
        return u;
    }
    
    private AccessLogTO createAccessLog () {
                
        AccessLogTO log = new AccessLogTO();        
        log.setLoginTimestamp(new Date());
        log.setLogoutTimestamp(new Date());
        log.setQuota(DUMMY_LONG_VALUE);
        log.setUserControlTO(createUserContro());
        log.setEnvironment("");
        log.setInternetSite("");
        
        return log;
    }
}
