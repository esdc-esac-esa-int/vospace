package esavo.vospace.dl.querymanager.cmd.ingestion;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class RemoveNodeCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(RemoveNodeCommandTest.class);
    
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
    public RemoveNodeCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doRemoveNode method.
     */
    @Test
    public final void testDoRemoveNode() {
        try {
            log.info("Into RemoveNodeCommandTest."
                    + "testDoRemoveNode()");

            this.getQueryManager().doRemoveNode(new VOSpaceURI("vos://esavo!vospace/snieto/ContainerTest"));

            log.debug("Into RemoveNodeCommandTest."
                    + "testDoRemoveNode()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
