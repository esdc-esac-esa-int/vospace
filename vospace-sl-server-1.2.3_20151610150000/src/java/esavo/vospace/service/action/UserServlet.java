package esavo.vospace.service.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.UserService;
import esavo.vospace.service.utils.exceptions.VOSpaceExceptionHandler;

public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    /* Unique UserService to the servlet instance. */
    UserService service = new UserService();
    
    
    /** Logging variable. */
    private static Log Log = LogFactory.getLog(UserServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        Log.debug("Into UserServlet.doGet()");
        //URL location = getServletContext().getResource("/WEB-INF/data/directory.txt");
        //service.setResourceLocation(location);
        try {
            service.executeGetRequest(req, resp);
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, resp);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, resp);
        }
        
        Log.debug("End of UserServlet.doGet()");
    }

}
