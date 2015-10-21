package esavo.vospace.service.security.restful.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

public class CustomBasicAuthenticationEntryPoint implements
        AuthenticationEntryPoint {

    private String realmName;

    //~ Methods ========================================================================================================
    
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        
        // this code shows the HTML Apache Error Report when authentication fails
        /*response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("{\"status\":" + HttpServletResponse.SC_UNAUTHORIZED + "\"}");*/
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);        
        response.setContentLength(0);
    }

    public void afterPropertiesSet() throws Exception {
        Assert.hasText(realmName, "realmName must be specified");
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

}
