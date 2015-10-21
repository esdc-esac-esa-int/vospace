package esavo.vospace.service.security.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;

public class AuthenticationServlet extends HttpServlet {

  private static final long serialVersionUID = 3734010429426244469L;
  private static final Logger log = Logger
      .getLogger(AuthenticationServlet.class.getName());

  @Override
  public final void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  @Override
  public final void doGet(final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    log.info("Into AuthenticationServlet.doGet()");
    processRequest(request, response);
  }

  @Override
  public final void doPost(final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    log.info("Into PropertyServlet.doPost()");
    processRequest(request, response);
  }

  private void processRequest(final HttpServletRequest request,
      final HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    PrintWriter writer = response.getWriter();    
    
    // retrieve authentication using Spring security
    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();
                
    if(authentication == null) {
      writer.write("{\"username\": \"null\"}");
    } else {
        
        String userName = authentication.getName();
        
        // Query User to retrieve all its values
        VospaceServiceImpl vospaceService = new VospaceServiceImpl();
        UserTO userSystem = null;
        try {
            userSystem = vospaceService.doQueryUser(userName);
        } catch (VOSpaceException e) {
        }
        
        writer.write("{\"Name\": \"" + userSystem.getName() + "\", \"FullName\": \"" + userSystem.getFullName() +
                "\", \"Mail\": \""+ userSystem.getMail() +"\", \"Quota\": " + userSystem.getQuota() + 
                ", \"QLimit\": " + userSystem.getQLimit() + "}");
        
    }
    writer.flush();
    writer.close();
  }  
}