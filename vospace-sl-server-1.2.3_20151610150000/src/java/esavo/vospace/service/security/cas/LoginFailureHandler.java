package esavo.vospace.service.security.cas;

/*import java.esac.archive.gacs.security.authentication.AuthenticationException;
import java.esac.archive.gacs.security.authentication.HttpServletRequest;
import java.esac.archive.gacs.security.authentication.HttpServletResponse;
import java.esac.archive.gacs.security.authentication.Logger;
import java.esac.archive.gacs.security.authentication.LoginFailureHandler;
import java.esac.archive.gacs.security.authentication.ServletException;*/
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    static Log logger = LogFactory.getLog(LoginFailureHandler.class);

    @Override
    public void  onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws ServletException, IOException {
        
        /*// Ask for basic athentication only if no username is provided
        if(exception.getAuthentication().getName()==null || exception.getAuthentication().getName().trim().length()==0){
            response.setHeader("WWW-Authenticate", "Basic realm=\"Authentication needed\"");
        }
        response.sendError( HttpConstants.LOGIN_CREDENTIALS_NOT_VALID, "Unauthorized" );*/
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
