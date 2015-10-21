package esavo.vospace.exceptions;

import esavo.vospace.exceptions.VOSpaceException;

/**
 * User quota limit exceeded. 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class QuotaExceededException extends VOSpaceException {

    /** Serial ID */
    private static final long serialVersionUID = 4903086941307343969L;
    
    public static final int code_507 = 507;
    
    public QuotaExceededException(String message) {
        super(message);
    }
}
