package esavo.vospace.dl.querymanager.cmd.ingestion;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class UpdateParentNodeCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UpdateParentNodeCommandTest.class);
    
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
    public UpdateParentNodeCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#UpdateNodePropertiesCommandTest method.
     */
    @Test
    public final void testDoUpdateParentNode() {
        try {
            log.info("Into UpdateParentNodeCommandTest."
                    + "testDoUpdateParentNode()");

            NodeTO node = this.getQueryManager().doQueryNode(
                    new VOSpaceURI("vos://esavo!vospace/snieto/Local/local-folder"
                            + "/local-folder1/EUCL-OTS-TN-8-002_v0.9_EuclidSGS_Architecture_Dossier.pdf"), false);
            ContainerNodeTO parent = (ContainerNodeTO) this.getQueryManager().doQueryNode(
                    new VOSpaceURI("vos://esavo!vospace/snieto/Local"), false);
            
            node.setParent(parent);
            
            this.getQueryManager().doUpdateNodeParent(node);
            
            log.debug("End of UpdateParentNodeCommandTest."
                    + "testDoUpdateParentNode()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
