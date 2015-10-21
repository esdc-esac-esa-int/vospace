package esavo.vospace.dl.querymanager.cmd.controls;

import java.util.Date;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class IngestUserControlCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestUserControlCommandTest.class);
    
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
    public IngestUserControlCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoIngestUserControl() {
        try {
            log.info("Into IngestUserControlCommandTest."
                    + "testDoIngestUserControl()");

            UserControlTO u = createUserControl();
            this.getQueryManager().doInsertUserControlTO(u);

            log.debug("End of IngestUserControlCommandTest."
                    + "testDoIngestUserControl()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    private UserControlTO createUserControl () {
        UserControlTO u = new UserControlTO();
        
        u.setName("testname4");
        u.setSurname("testsurname4");
        u.setMail("testname4@sciops.esa.int");
        //u.setRegisterDate(new Date());
        
        return u;
    }
}
