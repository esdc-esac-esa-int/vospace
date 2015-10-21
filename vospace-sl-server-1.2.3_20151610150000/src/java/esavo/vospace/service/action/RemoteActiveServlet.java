package esavo.vospace.service.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet allows to check if the server is active.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public class RemoteActiveServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        resp.setStatus(HttpServletResponse.SC_OK); // 200
    }

}
