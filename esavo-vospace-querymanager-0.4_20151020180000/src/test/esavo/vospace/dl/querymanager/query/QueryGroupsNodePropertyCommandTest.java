package esavo.vospace.dl.querymanager.query;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class QueryGroupsNodePropertyCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryGroupsNodePropertyCommandTest.class);
    
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
    public QueryGroupsNodePropertyCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#testQueryNodes method.
     */
    @Test
    public final void testQueryNodes() {
        try {
            log.info("Into QueryNodeCommandTest.testQueryNodes()");

            String propertyUri = "vos://esavo!vospace/properties#groupread";
            List<NodeTO> nodeList = 
                    this.getQueryManager().doQueryGroupsNodePropertyCommand("carviset", propertyUri);
            for (NodeTO node : nodeList) {
                System.out.println("Node URI: " + node.getURI());
            }           
            
            log.debug("End of testQueryNode.testQueryNodes()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
