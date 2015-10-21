package esavo.vospace.exceptions;


public class PermissionDeniedException extends VOSpaceException {

    private static final long serialVersionUID = 6094744153476860492L;

    public static final int code_401 = 401;
    
    public PermissionDeniedException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public PermissionDeniedException(Throwable t) {
        super(t);
    }

    public PermissionDeniedException(Throwable t, String message) {
        super(t, message);
    }
}
