package esavo.vospace.service.action;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This servlet logs the logoff of a user from the Web VOspace application.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class SignoutLogServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    /** Logging variable. */
    private static Log Log = LogFactory.getLog(SignoutLogServlet.class);
    
    /**
     * @see Servlet#init(ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Log.debug("Into SignoutLogServlet.doGet()");
        
        
        //response.sendRedirect("http://eacs.esac.esa.int:8080/cas/logout");
        
        /*UserControlTO userControl = UserUtils.getCurrentContextControlUser();
        UserTO user = UserUtils.getCurrentContextUser();
        AccessLogTO log = ControlUtils.createAccessLogTO(userControl, 
                null, new Date(), user.getQuota(), null, null);
        VospaceServiceImpl vospaceService = new VospaceServiceImpl();
        
        try {
            vospaceService.doInsertAccessLog(log, true);
        } catch (VOSpaceException e) {
            e.printStackTrace();
        }*/
        
        Log.debug("End of SignoutLogServlet.doGet()");
    };

}
