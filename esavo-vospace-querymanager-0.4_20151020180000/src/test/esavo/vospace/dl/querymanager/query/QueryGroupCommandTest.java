package esavo.vospace.dl.querymanager.query;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class QueryGroupCommandTest extends AbstractQueryManagerTestClass {
    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryGroupCommandTest.class);
    
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
    public QueryGroupCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#testQueryGroup method.
     */
    @Test
    public final void testQueryGroup() {
        try {
            log.info("Into QueryNodeCommandTest.testQueryGroup()");

            List<GroupTO> groupList = this.getQueryManager().doQueryGroup("snieto", null);
            
            for (GroupTO group : groupList) {
                System.out.println("Name: " + group.getName());
            }
            
            log.debug("End of QueryNodeCommandTest.testQueryGroup()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
