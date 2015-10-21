package esavo.vospace.service.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.ShareService;
import esavo.vospace.service.utils.exceptions.VOSpaceExceptionHandler;

/**
 * This servlet takes the requests related getting lists with sharing purposes.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class ShareServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    /** Logging variable. */
    private static Log Log = LogFactory.getLog(ShareServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        Log.debug("Into ShareServlet.doGet()");
                        
        String value = req.getPathInfo(); // /user || /group
        if (value == null) {
            throw new ServletException();
        }
        
        if (value.length() > 1) {
            value = value.substring(1);
        }
        
        ShareService shareService = new ShareService();
        try {
            if (value.compareTo("user") == 0) {
                shareService.executeGetRequestForUsers(req, resp);
            } else if (value.compareTo("group") == 0) {                
                shareService.executeGetRequestForGroups(req, resp);                
            } else if (value.compareTo("all") == 0) {                
                shareService.executeGetRequestForAll(req, resp);                
            } else 
                throw new ServletException("Wrong URL request.");
            
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, resp);
        }
        
        Log.debug("End of ShareServlet.doGet()");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException ,IOException {
        Log.debug("Into ShareServlet.doPost()");
        
        ShareService shareService = new ShareService();            
        try {
            shareService.executePostRequest(req, resp);
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, resp);
        }
            
        Log.debug("End of ShareServlet.doPost()");
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException ,IOException {
        Log.debug("Into ShareServlet.doDelete()");
        
        ShareService shareService = new ShareService();            
        try {
            shareService.executeDeleteRequest(req, resp);
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, resp);
        }        
        
        Log.debug("End of ShareServlet.doDelete()");
    }   
    
}
