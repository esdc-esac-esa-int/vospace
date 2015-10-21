package esavo.vospace.service.action;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import esac.archive.absi.modules.cl.aio.servlet.AIOServlet;
import esavo.vospace.service.utils.VOSConstants;

public class CasServlet extends AIOServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 610442591950539482L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        super.init(config);
                
        /*ServletContext context = getServletContext();
        appid = UwsUtils.getAppIdFromContext(context, config);
        if(appid == null){
            throw new IllegalArgumentException("Application identifier must be defined. Use configuration variable: '"+UwsConfiguration.CONFIG_APP_ID+"'");
        }

        UwsConfiguration configuration = UwsConfigurationManager.getConfiguration(appid);
        UwsUtils.updateConfiguration(configuration, context);
        UwsUtils.updateConfiguration(configuration, config);
        
        //Initialize
        try {
            service = TapServiceConnection.getInstance(appid);
        } catch (UwsException e) {
            throw new ServletException(e);
        } catch (TAPException e) {
            throw new ServletException(e);
        }*/
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
        boolean logout = false;
        if(request.getRequestURI().endsWith("logout")){
            logout=true;
        }
        if (logout) {
            response.sendRedirect(VOSConstants.SERVER_URL+"logout?service=https://eacs.esac.esa.int/vospace-oper/");
        }
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        boolean logout = false;
        if(request.getRequestURI().endsWith("logout")){
            logout=true;
        }
        if (logout) {
            response.sendRedirect(VOSConstants.SERVER_URL+"logout?service=https://eacs.esac.esa.int/vospace-oper/");
            //response.sendRedirect(VOSConstants.SERVER_URL+"cas/logout");
        }
        
        /*String redirect = request.getParameter("redirect");
        boolean logout = false;
        if(request.getRequestURI().endsWith("Logout")){
            logout=true;
        }

        if(redirect!=null && !redirect.trim().isEmpty()){
            if(logout){
                response.sendRedirect(VOSConstants.SERVER_URL+"/j_spring_cas_security_logout");
            }else{
                response.sendRedirect(redirect);
            }
        }*/
    }
}
