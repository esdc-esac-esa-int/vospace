package test.esac.archive.vospace.persistence;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.dao.IContainerNodeDao;
import esac.archive.vospace.persistence.dao.IDataNodeDao;
import esac.archive.vospace.persistence.dao.IGroupControlDao;
import esac.archive.vospace.persistence.dao.IGroupsDao;
import esac.archive.vospace.persistence.dao.ILinkNodeDao;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.dao.IStructuredDataNodeDao;
import esac.archive.vospace.persistence.dao.IUnstructuredDataNodeDao;
import esac.archive.vospace.persistence.dao.IUserControlDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.dao.IVoCapabilityDao;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.DataNode;
import esac.archive.vospace.persistence.model.Groups;
import esac.archive.vospace.persistence.model.LinkNode;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.StructuredDataNode;
import esac.archive.vospace.persistence.model.UnstructuredDataNode;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoCapability;
import esac.archive.vospace.persistence.model.VoProperty;
import esac.archive.vospace.persistence.model.VosType;
import esac.archive.vospace.persistence.model.controls.Group;
import esac.archive.vospace.persistence.model.controls.User;
import esac.archive.vospace.persistence.VospaceDaoServiceImpl;

/**
 * Convenience class to group common code for populating dao objects.
 * @author snieto based on eHst HstObjectFactory
 */
public final class HstObjectFactory {

    /**
     * Logging variable.
     */
    private static final Log LOG = LogFactory.getLog(HstObjectFactory.class);

    /**
     * Unique instance of this class.
     */
    private static HstObjectFactory _instance;

    /** Users DAO. */
    private IUsersDao usersDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /** UserControl DAO. */
    private IUserControlDao userControlDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getUserControlDao();
    
    /** Groups DAO */
    private IGroupsDao groupsDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getGroupsDAO();
    
    /** GroupControl DAO */
    private IGroupControlDao groupControlDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getGroupControlDao();
    
    /** Node DAO */
    private INodeDao nodeDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getNodeDAO();
    
    /** DataNode DAO */
    private IDataNodeDao dataNodeDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getDataNodeDAO();
    
    /** LinkNode DAO */
    private ILinkNodeDao linkNodeDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getLinkNodeDAO();
    
    /** ContainerNode DAO */
    private IContainerNodeDao containerNodeDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getContainerNodeDAO();
    
    /** StructuredDataNode DAO */
    private IStructuredDataNodeDao structuredDataNodeDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getStructuredDataNodeDAO();
    
    /** UnstructuredDataNode DAO */
    private IUnstructuredDataNodeDao unstructuredDataNodeDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getUnstructuredDataNodeDAO();

    /** VoProperty DAO */
    private IVoPropertyDao voPropertyDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getVoPropertyDAO();
    
    /** VoCapability DAO */
    private IVoCapabilityDao voCapabilityDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getVoCapabilityDAO();
    
    /** Dummy Integer value. */
    public static final Integer DUMMY_INTEGER_VALUE = new Integer(666);

    /** Dummy Double value. */
    public static final Double DUMMY_DOUBLE_VALUE = new Double(666.666016);

    /** Dummy Long value. */
    public static final Long DUMMY_LONG_VALUE = new Long(666666016);
    
    public static final Long DUMMY_LONG_VALUE2 = new Long(668532016);

    /** Dummy Big decimal value. */
    public static final BigDecimal DUMMY_BIG_DECIMAL_VALUE = new BigDecimal(666.666016);

    /** Dummy Date value. */
    public static final Date DUMMY_DATE_VALUE = new Date(100, 10, 10, 10, 10, 10);

    /** Dummy String value. */
    public static final String DUMMY_STRING_VALUE = "dummyStringValue";

    /** Dummy composite observation id. */
    public static final String DUMMY_COMPOSITE_OBSERVATION_VALUE = "dummyComp";

    /** Dummy simple observation id. */
    public static final String DUMMY_SIMPLE_OBSERVATION_VALUE = "dummySimp";

    /** Dummy Boolean value. */
    public static final Boolean DUMMY_BOOLEAN_VALUE = Boolean.FALSE;

    /**
     * Empty constructor.
     */
    private HstObjectFactory() {
    }

    /**
     * Unique method to get object instance.
     * @return Unique instance of this object.
     */
    public static HstObjectFactory getInstance() {
        if (HstObjectFactory._instance == null) {
            HstObjectFactory._instance = new HstObjectFactory();
        }
        return HstObjectFactory._instance;
    }

    /****************************************************************/
    /****************************************************************/
    /************* CREATE METHODS ***********************************/
    /****************************************************************/
    /****************************************************************/

    /**
     * Create Users object.
     * @return Users object.
     */
    public Users createUsers() {
        Users user = this.usersDao.getEmptyUsers();
        user.setUserOid(DUMMY_INTEGER_VALUE);
        user.setName(DUMMY_STRING_VALUE);
        user.setMail(DUMMY_STRING_VALUE);
        user.setFullName(DUMMY_STRING_VALUE);
        user.setCreationDate(DUMMY_DATE_VALUE);        
        return user;
    }
    
    /**
     * Create UserControl object.
     * @return UserControl object.
     */
    public User createUserControl() {
        User user = this.userControlDao.getEmptyUserControl();
        user.setUserOid(DUMMY_INTEGER_VALUE);
        user.setUname(DUMMY_STRING_VALUE);
        user.setMail(DUMMY_STRING_VALUE);
        user.setSurname(DUMMY_STRING_VALUE);
        user.setRegisterDate(DUMMY_DATE_VALUE);        
        return user;
    }
    
    /**
     * Create share Users object.
     * @return share Users object.
     */
    public Users createShareUsers() {
        Users user = this.usersDao.getEmptyUsers();

        user.setUserOid(DUMMY_INTEGER_VALUE);
        user.setName(DUMMY_STRING_VALUE);
        user.setMail(DUMMY_STRING_VALUE);
        user.setFullName(DUMMY_STRING_VALUE);
        user.setCreationDate(DUMMY_DATE_VALUE);
        
        return user;
    }
    
    /**
     * Create Groups object
     * @return Groups object.
     */
    public Groups createGroups(Users manager) {
        Groups group = this.groupsDao.getEmptyGroup();
        
        group.setGroupOid(DUMMY_INTEGER_VALUE);
        group.setName(DUMMY_STRING_VALUE);
        group.setDescription(DUMMY_STRING_VALUE);
        group.setManager(manager);
        
        return group;
    }
    
    /**
     * Create GroupControl object
     * @return GroupControl object.
     */
    public Group createGroupControl(User manager) {
        Group group = this.groupControlDao.getEmptyGroupControl();
        
        group.setGroupOid(DUMMY_INTEGER_VALUE);
        group.setName(DUMMY_STRING_VALUE);
        group.setCreationDate(DUMMY_DATE_VALUE);
        group.setUser(manager);
        
        return group;
    }
    
    /**
     * Create UserGroup object
     * @return UserGroup object.
     */
    /*public UserGroup createUserGroup(Groups group, Users user, Role role) {
        UserGroup userGroup = this.userGroupDao.getEmptyUserGroup();
        
        userGroup.setUserGroupOid(DUMMY_LONG_VALUE);
        
        userGroup.setGroups(group);
        userGroup.setUsers(user);
        userGroup.setRole(role);
        
        return userGroup;
    }*/
    
    /**
     * Create Node object
     * @return Node object.
     */
    public Node createNode(Users user) {
        Node node = this.nodeDao.getEmptyNode();
        
        node.setNodeOid(DUMMY_INTEGER_VALUE);
        node.setName(DUMMY_STRING_VALUE);
        node.setUsers(user);
        
        return node;
    }
    
    /**
     * Create Node object
     * @return Node object.
     */
    public Node createNode(Users owner, VoProperty voProperty, String value) {
        Node node = this.nodeDao.getEmptyNode();
        
        node.setNodeOid(DUMMY_INTEGER_VALUE);
        node.setName(DUMMY_STRING_VALUE);
        node.setUsers(owner);
        
        //add a property to the node
        NodeProperty nodePropertyTest = createNodeProperty(node, voProperty, value);
        node.getNodePropertySet().add(nodePropertyTest);
        //node.getNodePropertySet().put(voProperty, value);
        
        return node;
    }
    
    /*
     * Create a NodePropertyTest object.
     */
    private NodeProperty createNodeProperty(Node node, 
            VoProperty voProperty, String value) {
        NodeProperty nodePropertyTest = new NodeProperty();
        
        nodePropertyTest.setNode(node);
        nodePropertyTest.setVoProperty(voProperty);
        nodePropertyTest.setValue(value);
        
        Set<Users> userList = new HashSet<Users>();
        userList.add(node.getUsers());
        //nodePropertyTest.setShareUserList(userList);
        
        return nodePropertyTest;
    }
    
    /**
     * Create LinkNode object
     * @return LinkNode object.
     */
    public LinkNode createLinkNode(Users user) {
        
        LinkNode node = this.linkNodeDao.getEmptyLinkNode();
        
        node.setNodeOid(DUMMY_INTEGER_VALUE);
        node.setName(DUMMY_STRING_VALUE);
        node.setUsers(user);
        node.setTarget(DUMMY_STRING_VALUE);
        
        return node;
    }
    
    public LinkNode createLinkNode(Users user, VoCapability capability) {
        
        LinkNode node = this.linkNodeDao.getEmptyLinkNode();
        
        node.setNodeOid(DUMMY_INTEGER_VALUE);
        node.setName(DUMMY_STRING_VALUE);
        node.setUsers(user);
        node.setTarget(DUMMY_STRING_VALUE);
        
        Set<VoCapability> listCapabilities = new HashSet<VoCapability>(1);
        listCapabilities.add(capability);
        node.setNodeCapabilities(listCapabilities);
        
        return node;
    }
    
    public DataNode createDataNode(Users user) {
        DataNode node = (DataNode) createNode(user);
        
        node.setBusy(false);
        
        return node;
    }
    
    public ContainerNode createContainerNode(Node child, Users user) {
        ContainerNode container = (ContainerNode) createDataNode(user);
        
        Set<Node> children = new HashSet<Node>();
        children.add(child);
        container.setChildrens(children);
        
        return container;
    }
    
    public UnstructuredDataNode createUnstructuredDataNode(Users user) {
        UnstructuredDataNode node = (UnstructuredDataNode) createDataNode(user);
                
        return node;
    }
    
    public StructuredDataNode createStructuredDataNode(Users user) {
        StructuredDataNode node = (StructuredDataNode) createDataNode(user);
                
        return node;
    }
    
    public VoCapability createVoCapability() {
        VoCapability voCapability = voCapabilityDao.getEmptyVoCapability();
        
        voCapability.setVoCapabilityOid(DUMMY_INTEGER_VALUE);
        voCapability.setUri(DUMMY_STRING_VALUE);
        voCapability.setEndPoint(DUMMY_STRING_VALUE);
        
        return voCapability;
    }
    
    public VoProperty createVoProperty() {
        VoProperty voProperty = voPropertyDao.getEmptyVoProperty();
        
        voProperty.setVoPropertyOid(DUMMY_INTEGER_VALUE);
        voProperty.setUri(DUMMY_STRING_VALUE);
        voProperty.setReadonly(false);
        voProperty.setDescription(DUMMY_STRING_VALUE);
        voProperty.setPropertyType(VosType.PROVIDE);
        
        return voProperty;
    }
    
    

    /****************************************************************/
    /****************************************************************/
    /************* INSERT AND RETURN METHODS ************************/
    /****************************************************************/
    /****************************************************************/

    /**
     * Create, insert and return a user object.
     * @return Users persistent object.
     * @throws Exception If any error is found.
     */
    public Users insertAndReturnUsersObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnUsersObject()");

        // Inserting proposal element...
        Users user = HstObjectFactory.getInstance().createUsers();
        // Wait.. what if our test object exists already?
        Users insertedUser = this.usersDao.queryUserById(user.getUserOid());
        if (insertedUser != null) {
            return insertedUser;
        }
        this.usersDao.insert(user);
        this.usersDao.flushPendingActionsToDatabase();
        return user;
    }
    
    public User insertAndReturnUserControlObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnUserControlObject()");

        // Inserting proposal element...
        User user = HstObjectFactory.getInstance().createUserControl();
        // Wait.. what if our test object exists already?
        User insertedUser = this.userControlDao.getUserControlById(user.getUserOid());
        if (insertedUser != null) {
            return insertedUser;
        }
        this.userControlDao.insert(user);
        this.userControlDao.flushPendingActionsToDatabase();
        return user;
    }
    
    /**
     * Create, insert and return a user object.
     * @return Users persistent object.
     * @throws Exception If any error is found.
     */
    public Users insertAndReturnUsers2Object() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnUsersObject()");

        // Inserting proposal element...
        Users user = HstObjectFactory.getInstance().createShareUsers();
        // Wait.. what if our test object exists already?
        Users insertedUser = this.usersDao.queryUserById(user.getUserOid());
        if (insertedUser != null) {
            return insertedUser;
        }
        this.usersDao.insert(user);
        this.usersDao.flushPendingActionsToDatabase();
        return user;
    }
    
    /**
     * Create, insert and return a group object.
     * @return groups persistent object.
     * @throws Exception If any error is found.
     */
    public Groups insertAndReturnGroupsObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnGroupsObject()");

        Users manager = insertAndReturnUsersObject();
        
        // Inserting proposal element...
        Groups group = HstObjectFactory.getInstance().createGroups(manager);
        // Wait.. what if our test object exists already?
        Groups insertedGroup = this.groupsDao.queryGroupByOwnerAndName(manager, DUMMY_STRING_VALUE);
        if (insertedGroup != null) {
            return insertedGroup;
        }
        this.groupsDao.insert(group);
        this.groupsDao.flushPendingActionsToDatabase();
        return group;
    }
    
    public Group insertAndReturnGroupControlObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnGroupControlObject()");

        User manager = insertAndReturnUserControlObject();
        
        // Inserting proposal element...
        Group group = HstObjectFactory.getInstance().createGroupControl(manager);
        // Wait.. what if our test object exists already?
        Group insertedGroup = this.groupControlDao.getGroupControlById(group.getGroupOid());
        if (insertedGroup != null) {
            return insertedGroup;
        }
        this.groupControlDao.insert(group);
        this.groupControlDao.flushPendingActionsToDatabase();
        return group;
    }
    
    /**
     * Create, insert and return a user group object.
     * @return user group persistent object.
     * @throws Exception If any error is found.
     */
    /*public UserGroup insertAndReturnUserGroupObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnUserGroupObject()");

        Users user = this.insertAndReturnUsersObject();
        Role role = this.insertAndReturnRoleObject();
        Groups group = this.insertAndReturnGroupsObject();
        
        // Inserting proposal element...
        UserGroup userGroup = HstObjectFactory.getInstance().createUserGroup(group, user, role);
        // Wait.. what if our test object exists already?
        UserGroup insertedUserGroup = this.userGroupDao.queryUserGroupById(userGroup.getUserGroupOid());
        if (insertedUserGroup != null) {
            return insertedUserGroup;
        }
        this.userGroupDao.insert(userGroup);
        this.userGroupDao.flushPendingActionsToDatabase();
        return userGroup;
    }*/
    
    /**
     * Create, insert and return a node object.
     * @return Node persistent object.
     * @throws Exception If any error is found.
     */
    public Node insertAndReturnNodeObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnNodeObject()");

        Users owner = insertAndReturnUsersObject();
        
        // Inserting proposal element...
        Node node = HstObjectFactory.getInstance().createNode(owner);
        // Wait.. what if our test object exists already?
        Node insertedNode = this.nodeDao.queryNodeById(DUMMY_INTEGER_VALUE);
        if (insertedNode != null) {
            return insertedNode;
        }
        this.nodeDao.insert(node);
        this.nodeDao.flushPendingActionsToDatabase();
        return node;
    }
    
    /**
     * Create, insert and return a LinkNode object.
     * @return Node persistent object.
     * @throws Exception If any error is found.
     */
    public LinkNode insertAndReturnLinkNodeObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnLinkNodeObject()");

        Users owner = insertAndReturnUsersObject();
        VoCapability capability = insertAndReturnCapabilityObject();
        
        // Inserting node element...
        //LinkNode node = HstObjectFactory.getInstance().createLinkNode(owner, vos);
        LinkNode node = HstObjectFactory.getInstance().createLinkNode(owner, capability);
        // Wait.. what if our test object exists already?
        LinkNode insertedNode = this.linkNodeDao.queryLinkNodeById(DUMMY_INTEGER_VALUE);
        if (insertedNode != null) {
            return insertedNode;
        }
        this.linkNodeDao.insert(node);
        this.linkNodeDao.flushPendingActionsToDatabase();
        return node;
    }
    
    public VoCapability insertAndReturnCapabilityObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnCapabilityObject()");

        // Inserting voCapability element...
        VoCapability voCapability = HstObjectFactory.getInstance().createVoCapability();
        // Wait.. what if our test object exists already?
        VoCapability insertedCapability = this.voCapabilityDao.queryVoCapabilityById(DUMMY_INTEGER_VALUE);
        if (insertedCapability != null) {
            return insertedCapability;
        }
        this.voCapabilityDao.insert(voCapability);
        this.voCapabilityDao.flushPendingActionsToDatabase();
        return voCapability;
    }
    
    public VoProperty insertAndReturnPropertyObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnPropertyObject()");
        
        // Inserting voProperty element...
        VoProperty voProperty = HstObjectFactory.getInstance().createVoProperty();
        // Wait.. what if our test object exists already?
        VoProperty insertedVoProperty = this.voPropertyDao.queryVoPropertyById(voProperty.getVoPropertyOid());
        if (insertedVoProperty != null) {
            return insertedVoProperty;
        }
        this.voPropertyDao.insert(voProperty);
        this.voPropertyDao.flushPendingActionsToDatabase();
        return voProperty;
    }
    
    /*public VoProperty insertAndReturnNodePropertyObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnNodePropertyObject()");
        
        // Inserting voProperty element...
        VoProperty voProperty = HstObjectFactory.getInstance().createVoProperty();
        VoProperty voProperty = HstObjectFactory.getInstance().createNodeProperty();
        // Wait.. what if our test object exists already?
        VoProperty insertedVoProperty = this.voPropertyDao.queryVoPropertyById(voProperty.getVoPropertyOid());
        if (insertedVoProperty != null) {
            return insertedVoProperty;
        }
        this.voPropertyDao.insert(voProperty);
        this.voPropertyDao.flushPendingActionsToDatabase();
        return voProperty;
    }*/
    
    public Node insertAndReturnCompleteNodeObject() throws Exception {
        LOG.info("Into HstObjectFactory.insertAndReturnShareUserNodeObject()");
        
        Users owner = insertAndReturnUsersObject();
        
        VoProperty voProperty = insertAndReturnPropertyObject();
        
        Node node = HstObjectFactory.getInstance().createNode(owner, 
                voProperty, this.DUMMY_STRING_VALUE);
        
        Node insertedNode = this.nodeDao.queryNodeById(DUMMY_INTEGER_VALUE);
        if (insertedNode != null) {
            return insertedNode;
        }
        
        this.nodeDao.insert(node);
        this.nodeDao.flushPendingActionsToDatabase();
        return node;
    }

    /****************************************************************/
    /****************************************************************/
    /************* DELETE METHODS ***********************************/
    /****************************************************************/
    /****************************************************************/

    /**
     * Delete test user from database.
     */
    public void deleteTestUsersFromDatabase() {
        LOG.info("Into HstObjectFactory.deleteTestUsersFromDatabase()");
        try {
            // Query for the test object we want to delete...
            Users user = this.usersDao.queryUserById(DUMMY_INTEGER_VALUE);
            if (user != null) {
                this.usersDao.delete(user);
                this.usersDao.flushPendingActionsToDatabase();
            }
        } catch (Exception e) {
            LOG.error("Exception in HstObjectFactory.deleteTestUsersFromDatabase()", e);
        }
        LOG.info("End of HstObjectFactory.deleteTestUsersFromDatabase()");
    }
    
    /**
     * Delete test group from database.
     */
    public void deleteTestGroupsFromDatabase() {
        LOG.info("Into HstObjectFactory.deleteTestGroupsFromDatabase()");
        try {
            // Query for the test object we want to delete...
            Groups group = this.groupsDao.queryGroupById(DUMMY_INTEGER_VALUE);
            if (group != null) {
                this.groupsDao.delete(group);
                this.groupsDao.flushPendingActionsToDatabase();
            }
        } catch (Exception e) {
            LOG.error("Exception in HstObjectFactory.deleteTestGroupsFromDatabase()", e);
        }
        LOG.info("End of HstObjectFactory.deleteTestGroupsFromDatabase()");
    }
    
    /**
     * Delete test group from database.
     */
    public void deleteTestGroupControlFromDatabase() {
        LOG.info("Into HstObjectFactory.deleteTestGroupControlFromDatabase()");
        try {
            // Query for the test object we want to delete...
            Group group = this.groupControlDao.getGroupControlById(DUMMY_INTEGER_VALUE);
            if (group != null) {
                this.groupControlDao.delete(group);
                this.groupControlDao.flushPendingActionsToDatabase();
            }
        } catch (Exception e) {
            LOG.error("Exception in HstObjectFactory.deleteTestGroupControlFromDatabase()", e);
        }
        LOG.info("End of HstObjectFactory.deleteTestGroupControlFromDatabase()");
    }
    
    /**
     * Delete test user group from database.
     */
    /*public void deleteTestUserGroupFromDatabase() {
        LOG.info("Into HstObjectFactory.deleteTestUserGroupFromDatabase()");
        try {
            // Query for the test object we want to delete...
            UserGroup userGroup = this.userGroupDao.queryUserGroupById(DUMMY_LONG_VALUE);
            if (userGroup != null) {
                this.userGroupDao.delete(userGroup);
                this.userGroupDao.flushPendingActionsToDatabase();
            }
        } catch (Exception e) {
            LOG.error("Exception in HstObjectFactory.deleteTestUserGroupFromDatabase()", e);
        }
        LOG.info("End of HstObjectFactory.deleteTestUserGroupFromDatabase()");
    }*/
    
    /**
     * Delete test node from database.
     */
    public void deleteTestNodeFromDatabase() {
        LOG.info("Into HstObjectFactory.deleteTestNodeFromDatabase()");
        try {
            // Query for the test object we want to delete...
            Node node = this.nodeDao.queryNodeByUri("snieto/ContainerTest");
            if (node != null) {
                this.nodeDao.delete(node);
                this.nodeDao.flushPendingActionsToDatabase();
            }
        } catch (Exception e) {
            LOG.error("Exception in HstObjectFactory.deleteTestNodeFromDatabase()", e);
        }
        LOG.info("End of HstObjectFactory.deleteTestNodeFromDatabase()");
    }
    
    /**
     * Delete test node from database.
     */
    public void deleteTestLinkNodeFromDatabase() {
        LOG.info("Into HstObjectFactory.deleteTestLinkNodeFromDatabase()");
        try {
            // Query for the test object we want to delete...
            LinkNode node = this.linkNodeDao.queryLinkNodeById(DUMMY_INTEGER_VALUE);
            if (node != null) {
                this.nodeDao.delete(node);
                this.nodeDao.flushPendingActionsToDatabase();
            }
        } catch (Exception e) {
            LOG.error("Exception in HstObjectFactory.deleteTestLinkNodeFromDatabase()", e);
        }
        LOG.info("End of HstObjectFactory.deleteTestLinkNodeFromDatabase()");
    }
       
    public void deleteTestVoCapabilityFromDatabase() {
        LOG.info("Into HstObjectFactory.deleteTestVoCapabilityFromDatabase()");
        try {
            // Query for the test object we want to delete...
            VoCapability voCapability = this.voCapabilityDao.queryVoCapabilityById(DUMMY_INTEGER_VALUE);
            if (voCapability != null) {
                this.voCapabilityDao.delete(voCapability);
                this.voCapabilityDao.flushPendingActionsToDatabase();
            }
        } catch (Exception e) {
            LOG.error("Exception in HstObjectFactory.deleteTestVoCapabilityFromDatabase()", e);
        }
        LOG.info("End of HstObjectFactory.deleteTestVoCapabilityFromDatabase()");
    }


    /**
     * Delete all test objects from database.
     */
    public void deleteTestObjectsFromDatabase() {
        LOG.info("Into HstObjectFactory.deleteTestObjectsFromDatabase()");
        try {
            // Ok, remove them all(order is important!!)... if any of them exist
            deleteTestUsersFromDatabase();
            deleteTestLinkNodeFromDatabase();
            //deleteTestNodeFromDatabase();
        } catch (Exception e) {
            LOG.error("Exception in HstObjectFactory.deleteTestObjectsFromDatabase()", e);
        }
        LOG.info("End of HstObjectFactory.deleteTestObjectsFromDatabase()");
    }
}
