package esavo.vospace.service.filter;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link Filter} to add cache control headers for GWT generated files to ensure
 * that the correct files get cached.
 * 
 * @author See Wah Cheng
 */
public class GWTCacheControlFilter implements Filter {

    /** Logging variable. */
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(GWTCacheControlFilter.class.getName());
    
 public void destroy() {
 }

 public void init(FilterConfig config) throws ServletException {
 }

 public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
   ServletException {
  
     HttpServletRequest httpRequest = (HttpServletRequest) request;
     String requestURI = httpRequest.getRequestURI();
     
     String data = httpRequest.getParameter("view"); //check if the request is downloading, if so, dont put caching headers 


     /*String msg = "Filtering NON CACHE ACTIVATED!!!!!!!-------------------------------------------------------! ";
     LOG.log(Level.SEVERE, msg);*/
     
     //.nocache.
     if (requestURI.contains("service") && data == null) {
         Date now = new Date();
         HttpServletResponse httpResponse = (HttpServletResponse) response;
         httpResponse.setDateHeader("Date", now.getTime());
         // one day old
         httpResponse.setDateHeader("Expires", now.getTime() - 86400000L);
         httpResponse.setHeader("Pragma", "no-cache");
         httpResponse.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
     }

  filterChain.doFilter(request, response);
 }
}
