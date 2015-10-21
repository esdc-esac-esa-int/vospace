package esavo.vospace.dl.querymanager.cmd.controls;

import java.util.Date;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.control.DataMngLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class IngestDataMngLogCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestDataMngLogCommandTest.class);
    
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
    public IngestDataMngLogCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoIngestDataMngLog() {
        try {
            log.info("Into IngestDataMngLogCommandTest."
                    + "testDoIngestDataMngLog()");

            insertDataMngLog();
            //updateDataMngLog();
            
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    private void insertDataMngLog () throws Exception {
        DataMngLogTO log = createDataMngLog();
        this.getQueryManager().doInsertDataMngLog(log);
    }
    
    /*private void updateDataMngLog () throws Exception {
        DataMngLogTO log = createDataMngLog();
        this.getQueryManager().doInsertDataMngLog(log, true);
    }*/
    
    private DataMngLogTO createDataMngLog () {
        DataMngLogTO log = new DataMngLogTO();
        
        log.setUserTO(createUserControl());
        log.setStartTimestamp(DUMMY_DATE_VALUE);
        log.setStopTimestamp(DUMMY_DATE_VALUE);
        log.setNodeSource(DUMMY_STRING_VALUE);
        log.setNodeTarget(DUMMY_STRING_VALUE);
        log.setOperType("CREATE");
        log.setSize(DUMMY_LONG_VALUE);
        
        return log;
    }
    
    private UserControlTO createUserControl () {
        UserControlTO u = new UserControlTO();
        
        u.setName("snieto");
        u.setSurname("nieto");
        u.setMail("sara.nieto@sciops.esa.int");
        u.setRegisterDate(new Date());
        
        return u;
    }
}
