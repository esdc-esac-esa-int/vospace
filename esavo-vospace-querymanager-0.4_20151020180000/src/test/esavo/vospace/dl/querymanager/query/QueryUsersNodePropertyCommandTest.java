package esavo.vospace.dl.querymanager.query;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class QueryUsersNodePropertyCommandTest extends
        AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryUsersNodePropertyCommandTest.class);
    
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
    public QueryUsersNodePropertyCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#testQueryNodes method.
     */
    @Test
    public final void testQueryUsersNodeProperty() {
        try {
            log.info("Into QueryUsersNodePropertyCommandTest.testQueryUsersNodeProperty()");

            String propertyUri = "vos://esavo!vospace/properties#groupwrite";
            List<NodeTO> nodeList = 
                    this.getQueryManager().doQueryUsersNodePropertyCommand("snieto", propertyUri);
            String propertyUri2 = "vos://esavo!vospace/properties#userwrite";
            List<NodeTO> nodeList2 = 
                    this.getQueryManager().doQueryUsersNodePropertyCommand("carviset", propertyUri2);
            for (NodeTO node : nodeList) {
                System.out.println("Node URI: " + node.getURI());
            }
            
            for (NodeTO node : nodeList2) {
                System.out.println("Node URI: " + node.getURI());
            }
            
            log.debug("End of QueryUsersNodePropertyCommandTest.testQueryUsersNodeProperty()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
