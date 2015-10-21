package esavo.vospace.dl.querymanager.query;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class QueryUserQuotaCommandTest extends AbstractQueryManagerTestClass {

    
    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryUserQuotaCommandTest.class);
    
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
    public QueryUserQuotaCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#testQueryGroup method.
     */
    @Test
    public final void testUserQuota() {
        try {
            log.info("Into QueryNodeCommandTest.testUserQuota()");

            Long quota = this.getQueryManager().doQueryUserQuota("snieto");
            
            System.out.println("User quota: " + quota);
            
            log.debug("End of QueryUserQuotaCommandTest.testUserQuota()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
