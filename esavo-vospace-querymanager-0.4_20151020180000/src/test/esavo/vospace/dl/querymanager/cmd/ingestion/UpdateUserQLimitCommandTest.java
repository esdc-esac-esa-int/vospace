package esavo.vospace.dl.querymanager.cmd.ingestion;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

/**
 * Testing UpdateUserQLimitCommandTest.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2015
 */
public class UpdateUserQLimitCommandTest extends AbstractQueryManagerTestClass {
    
    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UpdateUserQLimitCommandTest.class);
    
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
    public UpdateUserQLimitCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoUpdateUserQLimitCommandTest() {
        try {
            log.info("Into UpdateUserQLimitCommandTest."
                    + "testDoUpdateUserQLimitCommandTest()");

            long qlimit1 = 64424509440L; //60GB
            long qlimit2 = 21474836480L; //20GB
            long qlimit3 = 5368709120L; //5GB
            long qlimit4 = 9120L; //under current quota usage
            long qlimit5 = 61222621L; //under current quota usage
            long qlimit6 = 61222622L; //equals to current quota usage
            long qlimit7 = 61222623L; //equals to current quota usage
            
            long qlimit = qlimit6; // <- Change this value to test
            
            UserTO snietoTO = this.getQueryManager().doUpdateUserQLimit("platest", qlimit);            
            long result = 0L;
            
            if (snietoTO == null) {
                log.info("QLimit NOT updated, QLimit under current usage!");
            } else {
                result = snietoTO.getQLimit();
                
                if (qlimit == result) {
                    log.info("QLimit updated successfully!");
                } else {
                    log.info("QLimit NOT updated, please check!");
                }
            }
            
            log.debug("End of UpdateUserQLimitCommandTest."
                    + "testDoUpdateUserQLimitCommandTest()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
