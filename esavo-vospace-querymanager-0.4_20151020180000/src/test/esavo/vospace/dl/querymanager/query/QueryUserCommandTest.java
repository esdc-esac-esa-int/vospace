package esavo.vospace.dl.querymanager.query;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class QueryUserCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryUserCommandTest.class);
    
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
    public QueryUserCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoQueryUserCommandTest() {
        try {
            log.info("Into QueryUserCommandTest."
                    + "testDoQueryUserCommandTest()");

            //UserTO userTO = this.getQueryManager().doQueryUser("user_a");
            UserTO userTO = this.getQueryManager().doQueryUser("mfernand");
            String name = userTO.getName();
            String fullname = userTO.getFullName();
            Long quota = userTO.getQuota();
            String name2 = userTO.getName();
            Long qlimit = userTO.getQLimit();

            log.debug("End of QueryUserCommandTest."
                    + "testDoQueryUserCommandTest()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
