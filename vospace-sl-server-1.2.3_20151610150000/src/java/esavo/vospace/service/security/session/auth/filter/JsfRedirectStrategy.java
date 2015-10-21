package esavo.vospace.service.security.session.auth.filter;

import java.io.IOException;
import java.util.logging.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.InvalidSessionStrategy;

import esavo.vospace.constants.ClientConstants;
import esavo.vospace.service.security.session.error.TimeoutException;
import esavo.vospace.service.utils.VOSConstants;

public class JsfRedirectStrategy implements InvalidSessionStrategy {
    
    /** Logging variable. */
    private static final Logger LOG = Logger.getLogger(JsfRedirectStrategy.class.getName());
    
    public JsfRedirectStrategy () {}

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        
        
        String msg = "Invalid session detected!";
        LOG.log(Level.SEVERE, msg + " " + VOSConstants.SERVER_URL + ClientConstants.LOGIN);        
        
         String url = request.getPathInfo(); 
         String servletPath = request.getServletPath();
         if (url == null && servletPath.indexOf("download") == -1) {
            //refreshing the page
            msg = "Invalid session detected at refresh! " + url;
            LOG.log(Level.SEVERE, msg);
            response.sendRedirect(VOSConstants.SERVER_URL + ClientConstants.LOGIN);
            return;
        } else {
            msg = "Invalid session detected.";
            LOG.log(Level.SEVERE, msg);
            throw new TimeoutException();
        }
    }

}
