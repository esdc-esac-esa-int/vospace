package esavo.vospace.constants;

import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.exceptions.InvalidURIException;
import esavo.vospace.exceptions.PermissionDeniedException;
import esavo.vospace.exceptions.QuotaExceededException;
import esavo.vospace.exceptions.TypeNotSupportedException;
import esavo.vospace.exceptions.UnsupportedOperationException;
import esavo.vospace.exceptions.database.ContainerNotFoundException;
import esavo.vospace.exceptions.database.DuplicateNodeException;
import esavo.vospace.exceptions.database.LinkFoundException;
import esavo.vospace.exceptions.database.NodeNotFoundException;

public class ErrorConstants {

    // VOSpace exceptions
    public static final int INTERNAL_FAULT_EXCEPTION = InternalFaultException.code_500;
    public static final int INVALID_URI_EXCEPTION = InvalidURIException.code_400;
    public static final int PERMISSION_DENIED_EXCEPTION = PermissionDeniedException.code_401;
    public static final int QUOTA_EXCEEDED_EXCEPTION = QuotaExceededException.code_507;
    public static final int TYPE_NOT_SUPPORTED_EXCEPTION = TypeNotSupportedException.code_500;
    public static final int UNSUPPORTED_OPERATION_EXCEPTION = UnsupportedOperationException.code_501;
    public static final int CONTAINER_NOT_FOUND_EXCEPTION = ContainerNotFoundException.code_404;
    public static final int DUPLICATE_NODE_EXCEPTION = DuplicateNodeException.code_409;
    public static final int LINK_FOUND_EXCEPTION = LinkFoundException.code_404;
    public static final int NODE_NOT_FOUND_EXCEPTION = NodeNotFoundException.code_404;
    
    // Http exceptions
    public static final int OK = 200;
    
    // Target Resolver Service Not Available
    public static final int STATUS_CODE_TARGET_RESOLVER_SERVICE_NOT_AVAILABLE =
            998;

    // Target Not Found Status code
    public static final int STATUS_CODE_TARGET_NOT_FOUND = 999;

    // /////////////////////////////////////////////////////////////////////////
    // / CREDENTIALS HTTP status codes
    // /////////////////////////////////////////////////////////////////////////

    // Login Credentials OK
    public static final int LOGIN_CREDENTIALS_VALID = 800;

    // Login Credentials OK
    public static final int LOGOUT_OK = 801;

    // User logged in (cookie exists)
    public static final int USER_LOGGED_IN = 805;

    // Default (guest) user logged in (cookie exists)
    public static final int DEFAULT_USER_LOGGED_IN = 806;

    // Login CREDENTIALS Not valid
    public static final int LOGIN_CREDENTIALS_NOT_VALID = 810;

    // Login REQUIRED
    public static final int LOGIN_REQUIRED = 815;

    // User details update
    public static final int USER_DETAILS_UPDATED_SUCCESSFULLY = 819;
    public static final int ERROR_UPDATING_USER_DETAILS = 820;

    // User registration
    public static final int USER_REGISTRATION_OK = 821;
    public static final int USER_REGISTRATION_USER_ALREADY_EXISTS_ERROR = 822;
    public static final int USER_REGISTRATION_UNKNOWN_ERROR = 823;

    // Forgotten password
    public static final int USER_FORGOTTEN_PASSWORD_PROCESSED_OK = 830;
    public static final int USER_FORGOTTEN_PASSWORD_USERNAME_NOT_FOUND = 831;
    public static final int USER_FORGOTTEN_PASSWORD_UNKNOWN_ERROR = 832;

    // User password update
    public static final int USER_UPDATE_PASSWORD_PROCESSED_OK = 840;
    public static final int USER_UPDATE_PASSWORD_CURRENT_PASSWORD_NOT_CORRECT =
            841;
    public static final int USER_UPDATE_PASSWORD_UNKWNON_ERROR = 842;
    
}
