package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class QueryGroupCommand extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryGroupCommand.class);
    
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
    public QueryGroupCommand() {
        super();
    }
        
    @Test
    public final void testQueryGroupCommandTest() {
        try {
            log.info("Into QueryGroupCommandTest.testQueryGroup()");

            VOSpaceURI uri = new VOSpaceURI("vos://esavo!vospace/user_a/test"); //-> vos://esavo!vospace!snieto/
                        
            List<GroupTO> nodeResult = this.getQueryManager().doQueryGroup("user_a", "euclid");
            for (GroupTO groupTO : nodeResult) {
                String name = groupTO.getName();
            }
                        
            log.debug("End of QueryGroupCommandTest.testQueryGroup()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    
}
