package esavo.vospace.constants;

/**
 * List of constants used by the Client (and probably by the Server layer also.)
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class ClientConstants {

    public static final String LOGIN = "login.jsp"; 
    public static final String LOGOUT = "j_spring_security_logout";
    public static final String VOS_UWS_ASYNC = "/async";
    public static final String VOS_UWS_PHASE = "/phase";
    public final static String ENTRYPOINT_SERVLET = "servlet";        
    public final static String ENTRYPOINT_SERVICE = "service";
    public final static String AUTH_SERVLET_URL = ENTRYPOINT_SERVICE + "/" + "auth";
    public static final String VOS_SERVICE_NODES = ENTRYPOINT_SERVICE + "/" + "nodes/"; 
    public static final String VOS_SERVICE_TRANSFERS = ENTRYPOINT_SERVICE + "/" + "transfers";
    public static final String VOS_SERVICE_UPLOAD = ENTRYPOINT_SERVICE + "/" + "upload/";
    public static final String VOS_SERVICE_DOWNLOAD = ENTRYPOINT_SERVICE + "/" + "download";
    public static final String VOS_SERVICE_USERS = ENTRYPOINT_SERVICE + "/" + "users";
    public static final String VOS_SERVICE_GROUPS = ENTRYPOINT_SERVICE + "/" + "groups/";
    public static final String VOS_SERVICE_SHARE_USER = ENTRYPOINT_SERVICE + "/" + "share/user";
    public static final String VOS_SERVICE_SHARE_GROUP = ENTRYPOINT_SERVICE + "/" + "share/group";
    public static final String VOS_SERVICE_SHARE_ALL = ENTRYPOINT_SERVICE + "/" + "share/all";
    public static final String VOS_SERVICE_ACTIVE = ENTRYPOINT_SERVICE + "/" + "active/";
    public static final String VOS_SERVICE_SIGNOUT = ENTRYPOINT_SERVICE + "/" + "signout/";
    public static final String VOS_SERVICE_SHAREALL = ENTRYPOINT_SERVICE + "/" + "share/all";
    
    /* Web Browser labels */
    public static final String MY_GROUPS = "My Groups";
    public static final String VOS_NAME = "My VOSpace";
    public static final String VOS_SHARED = "Shared with me";
    public static final String QUOTA_EXCEEDED = "Quota limit exceeded";
    
}
