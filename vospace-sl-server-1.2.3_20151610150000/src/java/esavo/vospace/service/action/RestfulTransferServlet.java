package esavo.vospace.service.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides a Restful entry point for transfers to redirect 
 * the request to the proper servlet '/transfers/async'
 * 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014 *
 */
public class RestfulTransferServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
       
        RequestDispatcher rd = req.getRequestDispatcher("/transfers/async");
        rd.forward(req, resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {        
        
        String pathInfo = req.getPathInfo();
        RequestDispatcher rd = req.getRequestDispatcher("/transfers" + pathInfo);
        rd.forward(req, resp);
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String pathInfo = req.getPathInfo();
        RequestDispatcher rd = req.getRequestDispatcher("/transfers" + pathInfo);
        rd.forward(req, resp);
    }
    
}
