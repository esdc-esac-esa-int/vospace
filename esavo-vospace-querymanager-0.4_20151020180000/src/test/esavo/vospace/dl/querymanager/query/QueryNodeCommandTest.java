package esavo.vospace.dl.querymanager.query;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.GroupNodePropertyTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class QueryNodeCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryNodeCommandTest.class);
    
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
    public QueryNodeCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#testQueryNode method.
     */
    @Test
    public final void testQueryNode() {
        try {
            log.info("Into QueryNodeCommandTest.testQueryNode()");

            VOSpaceURI uri = new VOSpaceURI("vos://esavo!vospace/snieto"); //-> vos://esavo!vospace!snieto/
            NodeTO nodeResult = this.getQueryManager().doQueryNode(uri, true);
            List<NodeTO> children = nodeResult.getChildren();
            
            for (NodeTO nodeTO : children) {
                VOSpaceURI nodeuri = nodeTO.getURI();
            }
            /*List<NodePropertyTO> nodePropertiesList = nodeResult.getNodeProperties();
            
            for (NodePropertyTO element : nodePropertiesList) {
                String propUri = element.getVoPropertyTO().getURI();
                if (propUri.compareTo("vos://esavo!vospace/properties#groupread") == 0) {
                    if (element instanceof GroupNodePropertyTO) {
                        List<GroupTO> groupList = ((GroupNodePropertyTO) element).getGroupList();
                        for (GroupTO group : groupList) {
                            String name = group.getName();
                            System.out.println("Name: " + name);
                        }
                    }
                }
            }*/
            
            log.debug("End of testQueryNode.testQueryNode()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
