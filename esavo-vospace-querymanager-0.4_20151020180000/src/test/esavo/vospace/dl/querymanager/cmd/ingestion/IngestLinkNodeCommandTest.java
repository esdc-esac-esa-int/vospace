package esavo.vospace.dl.querymanager.cmd.ingestion;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class IngestLinkNodeCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestLinkNodeCommandTest.class);
    
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
    public IngestLinkNodeCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoIngestLinkNodeWithOverwrite() {
        try {
            log.info("Into testDoIngestLinkNodeWithOverwrite."
                    + "testDoIngestLinkNodeWithOverwrite()");

            LinkNodeTO linkNodeTO = createLinkNodeTO();
            this.getQueryManager().doInsertLinkNode(linkNodeTO, false);
            
            log.debug("End of IngestContainerNodeCommandTest."
                    + "testDoIngestContainerNodeWithOverwrite()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }

    private LinkNodeTO createLinkNodeTO() {
        LinkNodeTO linkNodeTO = new LinkNodeTO();
        
        String uri = "vos://esavo!vospace!snieto/" + DUMMY_STRING_VALUE;
        linkNodeTO.setUri(new VOSpaceURI(uri + "_link_1"));
        linkNodeTO.setTarget(new VOSpaceURI(uri));
             
        UserTO vospaceUserTO = new UserTO();
        vospaceUserTO.setName("snieto");
        
        UserTO userTO = new UserTO();
        userTO.setName("snieto");
        linkNodeTO.setUserTO(userTO);
        
        return linkNodeTO;
    }
}
