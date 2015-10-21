package esavo.vospace.exceptions;


public class UnsupportedOperationException extends VOSpaceException {

    /** Serial ID */
    private static final long serialVersionUID = -4266953991260873257L;

    public static final int code_501 = 501;
    
    public UnsupportedOperationException(String message) {
        super(message);
    }

    public UnsupportedOperationException(Throwable t) {
        super(t);
    }

    public UnsupportedOperationException(Throwable t, String message) {
        super(t, message);
    }
}
