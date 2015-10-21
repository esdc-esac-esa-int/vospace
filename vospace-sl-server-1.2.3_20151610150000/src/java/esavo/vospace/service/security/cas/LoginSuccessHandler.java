package esavo.vospace.service.security.cas;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.control.AccessLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.utils.ControlUtils;
import esavo.vospace.service.utils.access.UserUtils;

public class LoginSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(LoginSuccessHandler.class);
        
    @Override
    public void  onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        
        UserTO vosUser = null;
        UserControlTO vosControl = null;
        try {
            
            vosUser = UserUtils.getCurrentContextUser();
            vosControl = UserUtils.getCurrentContextControlUser();
            
            /*UserContextServiceImpl context = new UserContextServiceImpl();
            String username = context.getCurrentUser();
            vosUser = UserManager.getInstance().getOrCreateUser(username); // Singleton call
            UserControlTO vosControl = ControlManager.getInstance().getOrCreateControlUser(username); // Singleton call*/
            
            VospaceServiceImpl vospaceService = new VospaceServiceImpl();
            AccessLogTO log = ControlUtils.createAccessLogTO(vosControl, 
                    new Date(), null, vosUser.getQuota(), "", "");
            vospaceService.doInsertAccessLog(log);
            
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (VOSpaceException e) {
            log.debug("Error creating user account for user [" + vosUser.getName() + "]. " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
