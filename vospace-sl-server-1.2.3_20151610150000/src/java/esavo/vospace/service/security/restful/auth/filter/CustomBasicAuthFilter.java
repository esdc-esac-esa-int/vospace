package esavo.vospace.service.security.restful.auth.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.control.AccessLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.utils.ControlUtils;
import esavo.vospace.service.utils.access.UserUtils;

public class CustomBasicAuthFilter extends BasicAuthenticationFilter {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(CustomBasicAuthFilter.class);
    
    public CustomBasicAuthFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            Authentication authResult) {        
        
        UserTO vosUser = null;
        UserControlTO vosControl = null;
        try {
            
            vosUser = UserUtils.getCurrentContextUser();
            vosControl = UserUtils.getCurrentContextControlUser();
            
            /*UserContextServiceImpl context = new UserContextServiceImpl();
            String username = context.getCurrentUser();
            vosUser = UserManager.getInstance().getOrCreateUser(username); // Singleton call
            UserControlTO vosControl = ControlManager.getInstance().getOrCreateControlUser(username); // Singleton call */
            
            VospaceServiceImpl vospaceService = new VospaceServiceImpl();            
            AccessLogTO log = ControlUtils.createAccessLogTO(vosControl, 
                    new Date(), null, vosUser.getQuota(), "", "");
            vospaceService.doInsertAccessLog(log);
            
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (VOSpaceException e) {
            log.debug("Error creating user account for user [" + vosUser.getName() + "].");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
            AuthenticationException failed) throws IOException {
        
        // commented to avoid Apache Error Report as output when introducing 
        // wrong credentials through command line
        //response.sendError( 810, "Unauthorized" );
    }
}
