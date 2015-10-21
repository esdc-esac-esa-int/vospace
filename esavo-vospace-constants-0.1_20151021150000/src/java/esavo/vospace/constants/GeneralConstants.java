package esavo.vospace.constants;

/**
 * List of constants used by the VOSpace project components.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class GeneralConstants {

    public static final String VOS_PREFIX = "vos://esavo!vospace";
    
    public static final String VOS_XML_CONTAINER = "vos:ContainerNode";
    public static final String VOS_JSON_CONTAINER = "ContainerNode";
    public static final String VOS_PROPERTIES = "vos:properties";
    public static final String VOS_STRUCTURED_DATA_NODE = "StructuredDataNode";
    public static final String VOS_UNSTRUCTURED_DATA_NODE = "UnstructuredDataNode";     
    public static final String VOS_RESULT_DESTINATION = "result";
    public static final String VOS_TARGET = "target";
    public static final String VOS_DIRECTION = "direction";
    public static final String VOS_KEEPBYTES = "keepBytes";
    public static final String VOS_PROTOCOL = "protocol";
    public static final String VOS_VIEW = "view";
    public static final String VOS_USER = "user";
    public static final String VOS_DETAIL = "detail";
    public static final String VOS_MATCHES = "matches";
    public static final String VOS_URI = "uri";
    public static final String VOS_LIMIT = "limit";
    public static final String VOS_NODE = "node";
    
    public static final String PROPERTIES = "properties";
    public static final String USERREAD = "userread";
    public static final String USERWRITE = "userwrite";
    public static final String GROUPWRITE = "groupwrite";
    public static final String GROUPREAD = "groupread";
    public static final String PUBLIC_READ = "publicread";
    public static final String PERMISSION = "permission";
    public static final String LENGTH = "length";
    public static final String CREATE_TIME = "btime";
    public static final String MODIF_TIME = "mtime";
    public static final String DATE = "date";
    public static final String DESCRIPTION = "description";
    
    // properties
    public static final String PROPERTY_DATE = VOS_PREFIX + "/" + PROPERTIES + "#" + DATE;
    public static final String PROPERTY_LENGTH = VOS_PREFIX + "/" + PROPERTIES + "#" + LENGTH;
    public static final String PROPERTY_DESCRIPTION = VOS_PREFIX + "/" + PROPERTIES + "#" + DESCRIPTION;
    public static final String PROPERTY_GROUPREAD = VOS_PREFIX + "/" + PROPERTIES + "#" + GROUPREAD;
    public static final String PROPERTY_GROUPWRITE = VOS_PREFIX + "/" + PROPERTIES + "#" + GROUPWRITE;
    public static final String PROPERTY_USERREAD = VOS_PREFIX + "/" + PROPERTIES + "#" + USERREAD;
    public static final String PROPERTY_USERWRITE = VOS_PREFIX + "/" + PROPERTIES + "#" + USERWRITE;
    public static final String PROPERTY_PUBLICREAD = VOS_PREFIX + "/" + PROPERTIES + "#" + PUBLIC_READ;
    public static final String PROPERTY_MTIME = VOS_PREFIX + "/" + PROPERTIES + "#" + MODIF_TIME;
    public static final String PROPERTY_BTIME = VOS_PREFIX + "/" + PROPERTIES + "#" + CREATE_TIME;
    public static final String PROPERTY_PERMISSION = VOS_PREFIX + "/" + PROPERTIES + "#" + PERMISSION;
    
    public static final String JSON = "json";
    
    public static final String DATE_PATTERN = "MM/dd/yyyy HH:mm:ss";
    
    //Upload
    //public static final long USER_QUOTA_LIMIT = 10737418240L; //10GB
    //public static final long MAX_FILESIZE = 10737418240L; //10GB
    public static final long USER_QUOTA_LIMIT = 53687091200L; //50GB
    public static final long MAX_FILESIZE = 21474836480L; //20GB
}
