package esac.archive.vospace.persistence.util;

public class DaoConstants {

    // Node Constants
    public static final String DB_NODE_VOS = "vospace";
    public static final String DB_NODE_ID = "nodeOid";
    public static final String DB_NODE_CHILD_ID = "nodeId";
    public static final String DB_NODE_OWNER = "ownerId";
    public static final String DB_NODE_PARENT = "containerNode";
    public static final String DB_NODE_NAME = "name";
    public static final String DB_NODE_TYPE = "nodeType";
    public static final String DB_NODE_TARGET = "target";
    public static final String DB_NODE_BUSY = "busy";
    public static final String DB_NODE_DEL = "deleted";
    public static final String DB_NODE_CREATION = "creationDate";
    public static final String DB_NODE_UPDATED = "lastmodifDate";
    public static final String DB_NODE_PROPERTIES = "node_property";
    public static final String DB_NODE_USERS = "users";

    // Type Constants
    public static final String DB_VIEW_TYPE = "viewType";
    public static final String DB_PROTOCOLS_TYPE = "protocolType";
    public static final String DB_PROPERTIES_TYPE = "propertyType";
    public static final String DB_PROPERTIES_ID = "vo_property_oid";
    public static final String DB_PROPERTIES = "vo_property";

    // Properties Constant
    public static final String DB_PROPERTIES_READ = "readonly";
    public static final String DB_PROPERTIES_DESCRIPTION = "description";

    // Specific Constants
    public static final String DB_VIEW_ORIGINAL = "original";
    public static final String DB_NODE_PROPERTIES_ID = "nodePropertyOid";
    public static final String DB_NODE_PROPERTIES_NODE_ID = "nodeId";
    public static final String DB_NODE_PROPERTIES_PROPERTY_ID = "propertyId";
    public static final String DB_NODE_PROPERTIES_NODE = "node";

    // VOSpace Constants
    public static final String DB_VOS_ = "voView";
    public static final String DB_VOS_NAME = "vosName";

    // User
    public static final String DB_USERS_ID = "userOid";
    public static final String DB_USERS_NAME = "name";
    public static final String DB_USERS_FULLNAME = "fullName";
    public static final String DB_USERS_MAIL = "mail";
    public static final String DB_USERS_QUOTA= "quota";

    // Groups
    public static final String DB_GROUPS_NAME = "name";
    public static final String DB_GROUPS_DESC = "description";
    public static final String DB_GROUPS_MANAGER = "manager";
    public static final String DB_GROUPS_ID = "groupOid";

    //User-Groups
    public static final String DB_USERGROUPS_USER_ID = "userId";
    public static final String DB_USERGROUPS_GROUPS_ID = "groupId";
    public static final String DB_USERGROUPS_ROLE_ID = "roleId";

    //Roles
    public static final String DB_ROLES_NAME = "name";
    public static final String DB_ROLES_DESC = "description";

    //share_users
    public static final String DB_SHARE_USERS_NODE = "nodeId";
    public static final String DB_SHARE_USERS_USER = "userId";
    public static final String DB_SHARE_USERS_ROLE = "roleId";

    //share_groups
    public static final String DB_SHARE_GROUP_NODE = "nodeId";
    public static final String DB_SHARE_GROUP_GROUP = "groupId";
    
    //vo_property
    public static final String DB_VOPROPERTIES_NAME = "uri";
    public static final String DB_VOPROPERTIES_VOSID = "vosId";
    public static final String DB_VOPROPERTY_ID = "voPropertyOid";
    public static final String DB_VOPROPERTY = "voProperty";
    
    //vo_capability
    public static final String DB_VOCAPABILITIES_NAME = "uri";
    public static final String DB_VOCAPABILITY_ID = "voCapabilityOid";
    //capability_param
    public static final String DB_VOCAPABILITY_PARAM_ID = "capabilityParamOid";
    
    //vo_protocol
    public static final String DB_VOPROTOCOL_NAME = "uri";
    public static final String DB_PROTOCOL_ID = "voProtocolOid";
    public static final String DB_VOPROTOCOL_END_POINT = "endPoint";
    //protocol_param
    public static final String DB_PROTOCOL_PARAM_ID = "protocolParamOid";
    
    //vo_view
    public static final String DB_VIEW_ID = "voViewOid";
    public static final String DB_VIEW_PARAM_ID = "viewParamOid";
    public static final String DB_VIEW_URI = "uri";
    
    //Controls schema
    public static final String DB_CONTROLS_USER_ID = "userOid";
    public static final String DB_CONTROLS_GROUP_ID = "groupOid";
    public static final String DB_CONTROLS_ACCESS_ID = "accessLogOid";
    public static final String DB_CONTROLS_USER = "user";
    public static final String DB_CONTROLS_TRANSFER_ID = "transferLogOid";
    public static final String DB_CONTROLS_SHARE_ID = "shareLogOid";
    public static final String DB_CONTROLS_METADATA_RETRIEVAL_ID = "metadataRetrievalLogOid";
    public static final String DB_CONTROLS_DATA_MNG_ID = "dataMngLogOid";
    public static final String DB_CONTROLS_USER_MAIL = "mail";
    public static final String DB_CONTROLS_USER_UNAME = "uname";
    //public static final String DB_CONTROLS_ = "";
    
}
