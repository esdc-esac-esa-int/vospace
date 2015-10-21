package esavo.vospace.service.utils.exceptions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.exceptions.InvalidURIException;
import esavo.vospace.exceptions.PermissionDeniedException;
import esavo.vospace.exceptions.QuotaExceededException;
import esavo.vospace.exceptions.TypeNotSupportedException;
import esavo.vospace.exceptions.UnsupportedOperationException;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.database.ContainerNotFoundException;
import esavo.vospace.exceptions.database.DuplicateNodeException;
import esavo.vospace.exceptions.database.LinkFoundException;
import esavo.vospace.exceptions.database.NodeNotFoundException;

public class VOSpaceExceptionHandler {

    /** Logging variable. */
    private static Log Log = LogFactory.getLog(VOSpaceExceptionHandler.class);
    
    public static void handleVOSException (VOSpaceException e, HttpServletResponse response) {
        Log.debug("Into VOSpaceExceptionHandler.handleVOSException()");
        
        if (e instanceof InternalFaultException) {
            Log.debug("Processing InternalFaultException...");
            response.setStatus(InternalFaultException.code_500);
        } else if (e instanceof InvalidURIException) {
            Log.debug("Processing InvalidURIException...");
            response.setStatus(InvalidURIException.code_400);
        }else if (e instanceof PermissionDeniedException) {
            Log.debug("Processing PermissionDeniedException...");
            response.setStatus(PermissionDeniedException.code_401);
        }else if (e instanceof QuotaExceededException) {
            Log.debug("Processing QuotaExceededException...");
            response.setStatus(QuotaExceededException.code_507);
        }else if (e instanceof TypeNotSupportedException) {
            Log.debug("Processing TypeNotSupportedException...");
            response.setStatus(TypeNotSupportedException.code_500);
        }else if (e instanceof UnsupportedOperationException) {
            Log.debug("Processing UnsupportedOperationException...");
            response.setStatus(UnsupportedOperationException.code_501);
        }else if (e instanceof ContainerNotFoundException) {
            Log.debug("Processing ContainerNotFoundException...");
            response.setStatus(ContainerNotFoundException.code_404);
        }else if (e instanceof LinkFoundException) {
            Log.debug("Processing LinkFoundException...");
            response.setStatus(LinkFoundException.code_404);
        }else if (e instanceof NodeNotFoundException) {
            Log.debug("Processing NodeNotFoundException...");
            response.setStatus(NodeNotFoundException.code_404);
        }else if (e instanceof DuplicateNodeException) {
            Log.debug("Processing DuplicateNodeException...");
            response.setStatus(DuplicateNodeException.code_409);
        } else {
            Log.debug("Error, Exception " + e.getClass() + " not supported by VOSpace!");
        }
        
        e.printStackTrace();
        
        String message = e.getMessage();
        Log.debug("Exception message: " + message);
        writeResponse(response, message);
        Log.debug("End of VOSpaceExceptionHandler.handleVOSException()");
    }
    
    public static void handleGenericException (Exception e, HttpServletResponse response) {
        Log.debug("Into VOSpaceExceptionHandler.handleGenericException()");        
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        writeResponse(response, e.getMessage());
        Log.debug("End of VOSpaceExceptionHandler.handleGenericException()");
    }
    
    private static void writeResponse (HttpServletResponse response, String textMessage) {        
        if (response == null) {
            return;
        }
        
        response.setContentType("text/html");        
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(textMessage);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
