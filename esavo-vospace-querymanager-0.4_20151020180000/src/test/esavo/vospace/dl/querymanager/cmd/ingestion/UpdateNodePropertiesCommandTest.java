package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.ArrayList;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.GroupNodePropertyTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.UserNodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class UpdateNodePropertiesCommandTest extends
        AbstractQueryManagerTestClass {
    
    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UpdateNodePropertiesCommandTest.class);
    
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
    public UpdateNodePropertiesCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#UpdateNodePropertiesCommandTest method.
     */
    @Test
    public final void testDoUpdateNodeProperties() {
        try {
            log.info("Into UpdateNodePropertiesCommandTest."
                    + "testDoUpdateNodeProperties()");

            ContainerNodeTO containerNodeTO = createContainerNodeTOWithProperty();
            //ContainerNodeTO containerNodeTO = createContainerNodeTOWithProperties();
            //ContainerNodeTO containerNodeTO = deleteSharingGroupProperty();
            //ContainerNodeTO containerNodeTO = deleteSharingGroupProperty();
            
            /*NodeTO nodeTO = this.getQueryManager().doQueryNode(new VOSpaceURI("vos://esavo!vospace/snieto/sim-data"), true);
            
            List<NodePropertyTO> list = nodeTO.getNodeProperties();
            for (NodePropertyTO prop : list) {
                if (prop.getVoPropertyTO().getURI().compareToIgnoreCase("vos://esavo!vospace/properties#userwrite") == 0) {
                    //UserNodePropertyTO p1 = new UserNodePropertyTO();
                    UserNodePropertyTO property = (UserNodePropertyTO) prop;
                    List<UserTO> userList = property.getUserList();
                    
                    //Add user
                    UserTO u1 = new UserTO();
                    u1.setName("bmartine");
                    userList.add(u1);
                    
                    //Remove user
                    userList.remove(0);
                }
                
                if (prop.getVoPropertyTO().getURI().compareToIgnoreCase("vos://esavo!vospace/properties#groupwrite") == 0) {
                    //UserNodePropertyTO p1 = new UserNodePropertyTO();
                    GroupNodePropertyTO property = (GroupNodePropertyTO) prop;
                    List<GroupTO> groupList = property.getGroupList();
                    
                    //Add group
                    GroupTO g = new GroupTO();
                    g.setName("Test");
                    UserTO manager = new UserTO();
                    manager.setName("snieto");
                    g.setManagerTO(manager);
                    groupList.add(g);
                    
                    //Remove group
                    groupList.remove(0);
                }
            }*/
            
            
            this.getQueryManager().doUpdateNodeProperties(containerNodeTO);

            log.debug("End of UpdateNodePropertiesCommandTest."
                    + "testDoUpdateNodeProperties()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
    
    private ContainerNodeTO createContainerNodeTOWithProperty() {
        ContainerNodeTO container = new ContainerNodeTO();
        
        String uri = "vos://esavo!vospace/snieto/sim-data";
        container.setUri(new VOSpaceURI(uri));
        container.setBusy(DUMMY_BOOLEAN_VALUE);
                
        UserTO userTO = new UserTO();
        userTO.setName("snieto");
        container.setUserTO(userTO);
        
        VoPropertyTO voProperty = new VoPropertyTO();
        voProperty.setURI("vos://esavo!vospace/properties#userwrite");
        /*NodePropertyTO p1 = new NodePropertyTO();
        p1.setVoPropertyTO(voProperty);
        p1.setValue("16-06-2014");*/
        UserNodePropertyTO p1 = new UserNodePropertyTO();
        p1.setVoPropertyTO(voProperty);
        //p1.setNill(true);
        
        UserTO u1 = new UserTO();
        u1.setName("bmartine");
        UserTO u2 = new UserTO();
        u2.setName("jsalgado");        
        UserTO u3 = new UserTO();
        u3.setName("ileon");        
        List<UserTO> lista = new ArrayList<UserTO>();
        lista.add(u1);
        /*lista.add(u2);
        lista.add(u3);*/        
        p1.setUserList(lista);
        
        VoPropertyTO voProperty1 = new VoPropertyTO();
        voProperty1.setURI("vos://esavo!vospace/properties#groupwrite");
        
        GroupNodePropertyTO p2 = new GroupNodePropertyTO();
        p2.setVoPropertyTO(voProperty1);
        
        GroupTO g1 = new GroupTO();
        g1.setName("TestV");
        g1.setManagerTO(userTO);
        
        GroupTO g2 = new GroupTO();
        g2.setName("Test");
        g2.setManagerTO(userTO);
        
        List<GroupTO> lista2 = new ArrayList<GroupTO>();
        lista2.add(g1);
        lista2.add(g2);
        
        p2.setGroupList(lista2);
        
        List<NodePropertyTO> nodePropertyTOList = new ArrayList<NodePropertyTO>();
        nodePropertyTOList.add(p1);
        nodePropertyTOList.add(p2);
        container.setNodeProperties(nodePropertyTOList);
        
        return container;
    }
    
    private ContainerNodeTO createContainerNodeTOWithProperties() {
        ContainerNodeTO container = new ContainerNodeTO();
        
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
        p1.setValue("16-06-2014");
        
        VoPropertyTO voProperty2 = new VoPropertyTO();
        voProperty2.setURI("vos://esavo!vospace/properties#groupread");
        GroupNodePropertyTO p2 = new GroupNodePropertyTO();
        p2.setVoPropertyTO(voProperty2);
        
        // group1
        GroupTO group1 = new GroupTO();
        group1.setName("euclid");
        UserTO manager = new UserTO();
        manager.setName("user_a");
        group1.setManagerTO(manager);
                
        List<GroupTO> shareGroupList = new ArrayList<GroupTO>();
        shareGroupList.add(group1);
        p2.setGroupList(shareGroupList);
                
        List<NodePropertyTO> nodePropertyTOList = new ArrayList<NodePropertyTO>();
        nodePropertyTOList.add(p1);
        nodePropertyTOList.add(p2);
        container.setNodeProperties(nodePropertyTOList);
        
        return container;
    }
    
    private ContainerNodeTO createSharingGroupProperty() {
        
        ContainerNodeTO container = new ContainerNodeTO();
        String uri = "vos://esavo!vospace/user_a/test";
        container.setUri(new VOSpaceURI(uri));
        container.setBusy(DUMMY_BOOLEAN_VALUE);
                
        UserTO userTO = new UserTO();
        userTO.setName("user_a");
        container.setUserTO(userTO);
                        
        VoPropertyTO voProperty2 = new VoPropertyTO();
        voProperty2.setURI("vos://esavo!vospace/properties#groupwrite");
        GroupNodePropertyTO p2 = new GroupNodePropertyTO();
        p2.setVoPropertyTO(voProperty2);
        
        // group1
        GroupTO group1 = new GroupTO();
        group1.setName("euclid");
        UserTO manager = new UserTO();
        manager.setName("user_a");
        group1.setManagerTO(manager);
        // group2
        GroupTO group2 = new GroupTO();
        group2.setName("group_a");
        UserTO manager2 = new UserTO();
        manager2.setName("snieto");
        group2.setManagerTO(manager2);
                
        List<GroupTO> shareGroupList = new ArrayList<GroupTO>();
        shareGroupList.add(group2);
        shareGroupList.add(group1);
        p2.setGroupList(shareGroupList);
        
        List<NodePropertyTO> nodePropertyTOList = new ArrayList<NodePropertyTO>();
        nodePropertyTOList.add(p2);
        container.setNodeProperties(nodePropertyTOList);
        
        return container;
    }
    
    private ContainerNodeTO deleteSharingGroupProperty() {
        
        ContainerNodeTO container = new ContainerNodeTO();
        String uri = "vos://esavo!vospace/snieto/ContainerTest2";
        container.setUri(new VOSpaceURI(uri));
        container.setBusy(DUMMY_BOOLEAN_VALUE);
                
        /*UserTO userTO = new UserTO();
        userTO.setName("snieto");
        container.setUserTO(userTO);*/
                        
        VoPropertyTO voProperty2 = new VoPropertyTO();
        voProperty2.setURI("vos://esavo!vospace/properties#groupwrite");
        GroupNodePropertyTO p2 = new GroupNodePropertyTO();
        p2.setVoPropertyTO(voProperty2);
        p2.setNill(false); //delete sharing property
        
        // group1
        GroupTO group1 = new GroupTO();
        group1.setName("Test");
        UserTO manager = new UserTO();
        manager.setName("snieto");
        group1.setManagerTO(manager);
        // user
        /*UserTO jSegovia = new UserTO();
        jSegovia.setName("jsegovia");
        
        UserTO userA = new UserTO();
        userA.setName("user_a");
        
        UserTO userB = new UserTO();
        userB.setName("user_b");*/
                
        List<GroupTO> shareGroupList = new ArrayList<GroupTO>();
        shareGroupList.add(group1);
        p2.setGroupList(shareGroupList);
        /*List<UserTO> shareUserList = new ArrayList<UserTO>();
        shareUserList.add(jSegovia);
        //shareUserList.add(userA);
        shareUserList.add(userB);
        p2.setShareUserList(shareUserList);*/
        
        List<NodePropertyTO> nodePropertyTOList = new ArrayList<NodePropertyTO>();
        nodePropertyTOList.add(p2);
        container.setNodeProperties(nodePropertyTOList);
        
        return container;
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
        
        List<NodePropertyTO> list = new ArrayList<NodePropertyTO>();
        NodePropertyTO prop = new NodePropertyTO();
        prop.setNill(false);
        prop.getVoPropertyTO().setURI("vos://esavo!vospace/properties#description");
        prop.setValue("My first persistent persistent.");
        list.add(prop);
        linkNodeTO.setNodeProperties(list);
        
        return linkNodeTO;
    }
    
    

}
