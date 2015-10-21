package esavo.vospace.dl.querymanager.cmd.controls;

import java.util.Date;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esac.archive.vospace.persistence.model.controls.TransferOperType;
import esavo.vospace.common.model.transferobjects.control.TransferLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class IngestTransferLogCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestTransferLogCommandTest.class);
    
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
    public IngestTransferLogCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoIngestTransferLog() {
        try {
            log.info("Into IngestTransferLogCommandTest."
                    + "testDoIngestTransferLog()");

            insertTransferLog();
            //updateTransferLog();
            
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    private void insertTransferLog () throws Exception {
        TransferLogTO log = createTransferLog();
        this.getQueryManager().doInsertTransferLogTO(log);
    }
    
    /*private void updateTransferLog () throws Exception {
        TransferLogTO log = createTransferLog();
        this.getQueryManager().doInsertTransferLogTO(log, true);
    }*/
    
    private UserControlTO createUserContro () {
        UserControlTO u = new UserControlTO();        
        
        u.setName("snieto");
        u.setSurname("nieto");
        u.setMail("snieto@sciops.esa.int");
        u.setRegisterDate(new Date());
        
        return u;
    }
    
    private TransferLogTO createTransferLog () {
        TransferLogTO log = new TransferLogTO();
        
        log.setOperType(TransferOperType.PUSH_TO_VOSPACE.toString());
        log.setProtocolParam(DUMMY_STRING_VALUE);
        log.setViewParam(DUMMY_STRING_VALUE);
        log.setStartTimestamp(DUMMY_DATE_VALUE);
        log.setTotalSize(DUMMY_LONG_VALUE);
        log.setUserControlTO(createUserContro());
        
        return log;
    }
}
