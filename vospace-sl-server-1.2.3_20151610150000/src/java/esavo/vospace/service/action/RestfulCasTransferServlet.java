package esavo.vospace.service.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestfulCasTransferServlet extends HttpServlet {

 private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        InputStream is = req.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("---" + inputLine +"---");
        }
        
        
        PrintWriter out = resp.getWriter();
        out.println("Headers<hr/>");
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            out.print("Header Name: <em>" + headerName);
            String headerValue = req.getHeader(headerName);
            out.print("</em>, Header Value: <em>" + headerValue);
            out.println("</em><br/>");
        }
        
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
