package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.ArrayList;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class IngestContainerNodeCommandTest extends
        AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestContainerNodeCommandTest.class);
    
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
    public IngestContainerNodeCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoIngestContainerNodeWithOverwrite() {
        try {
            log.info("Into IngestContainerNodeCommandTest."
                    + "testDoIngestContainerNodeWithOverwrite()");

            ContainerNodeTO containerNodeTO = createContainerNodeTO();
            this.getQueryManager().doInsertContainerNode(containerNodeTO, true);

            // Clean up
            //this.getQueryManager().doRemoveNode(DUMMY_STRING_VALUE);

            log.debug("End of IngestContainerNodeCommandTest."
                    + "testDoIngestContainerNodeWithOverwrite()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    private ContainerNodeTO createContainerNodeTO() {
        ContainerNodeTO container = new ContainerNodeTO();
        
        //container.setName(DUMMY_STRING_VALUE);
        String uri = "vos://esavo!vospace/user_a/test";
        container.setUri(new VOSpaceURI(uri));
        container.setBusy(DUMMY_BOOLEAN_VALUE);
                
        UserTO userTO = new UserTO();
        userTO.setName("user_a");
        container.setUserTO(userTO);
        
        VoPropertyTO voProperty = new VoPropertyTO();
        voProperty.setURI("vos://esavo!vospace/properties#date");
        NodePropertyTO p1 = new NodePropertyTO();
        p1.setVoPropertyTO(voProperty);
        p1.setValue("12-06-2014");
        //p1.setNodeTO(container);
        
        List<NodePropertyTO> nodePropertyTOList = new ArrayList<NodePropertyTO>();
        nodePropertyTOList.add(p1);
        container.setNodeProperties(nodePropertyTOList);
        
        return container;
    }
}
