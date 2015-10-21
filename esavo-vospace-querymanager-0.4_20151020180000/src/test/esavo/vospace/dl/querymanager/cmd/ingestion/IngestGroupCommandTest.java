package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class IngestGroupCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestGroupCommandTest.class);
    
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
    public IngestGroupCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#testDoIngestGroupWithoutOverwrite method.
     */
    @Test
    public final void testDoIngestGroupWithoutOverwrite() {
        
        try {
            log.info("Into IngestGroupCommandTest."
                    + "testDoIngestGroup()");
            GroupTO groupTO = createGroupTO();
            
            Map<String, String> membersMap = new HashMap<String, String>(); //User, Role            
            membersMap.put(DUMMY_STRING_VALUE, "editor");
            
            this.getQueryManager().doInsertGroup(groupTO, false);
            
            // Clean up
            this.getQueryManager().doRemoveGroup(DUMMY_STRING_VALUE, DUMMY_STRING_VALUE);
            
            log.debug("End of IngestGroupCommandTest."
                    + "testDoIngestGroup()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    /**
     * Method to test QueryHandler#testDoIngestGroupWithOverwrite method.
     */
    @Test
    public final void testDoIngestGroupWithOverwrite() {
        
        try {
            log.info("Into IngestGroupCommandTest."
                    + "testDoIngestGroup()");
            GroupTO groupTO = createGroupTO();
            
            
            
            this.getQueryManager().doInsertGroup(groupTO, true);
            
            // Clean up
            //this.getQueryManager().doRemoveGroup(DUMMY_STRING_VALUE, DUMMY_STRING_VALUE);
            
            log.debug("End of IngestGroupCommandTest."
                    + "testDoIngestGroup()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    private GroupTO createGroupTO () {
        GroupTO groupTO = new GroupTO();
        
        groupTO.setName("euclid");
        groupTO.setDescription("dummy description");
        groupTO.setCreationDate(new Date());
        UserTO manager = createUserTO();
        manager.setName("user_a");
        groupTO.setManagerTO(manager);
                
        List<UserTO> userList = new ArrayList<UserTO>();
        UserTO member = createUserTO();
        member.setName("snieto");
        userList.add(member);
        groupTO.setMembers(userList);
        
        return groupTO;
    }
    
    private UserTO createUserTO() {
        UserTO userTO = new UserTO();
        
        userTO.setName(DUMMY_STRING_VALUE);
        userTO.setFullName(DUMMY_STRING_VALUE);
        userTO.setMail(DUMMY_STRING_VALUE);
        userTO.setQuota(DUMMY_LONG_VALUE);
        
        return userTO;
    }
}
