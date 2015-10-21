package esavo.vospace.service.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.NodeService;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.exceptions.VOSpaceExceptionHandler;

/**
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 *
 */
public class NodeServlet extends HttpServlet {

    /** Generated serival version uid. */
    private static final long serialVersionUID = 1L;
    
    /** Logging variable. */
    private static Log Log = LogFactory.getLog(NodeServlet.class);
    
    protected static final String VOS_PREFIX = VOSConstants.VOS_PREFIX;

    /**
    * GET requests will be handled.
    * @param request Input http request.
    * @param response Input http response.
    * @throws ServletException If any Servlet related error is found.
    * @throws IOException If any i/o error is found.
    */
    @Override
    public final void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws ServletException, IOException {
        
    	processGetRequest(request, response);
    }
    
    /**
     * getNode request
     * @param request
     * @param response
     * @throws ServletException 
     * @throws IOException 
     */
    protected void processGetRequest (final HttpServletRequest request, 
            final HttpServletResponse response) throws ServletException, IOException {
        Log.debug("Into NodeServlet.doGet()");
        
        String path = request.getPathInfo();        
        if (path == null) {
            path = "";
        }
        @SuppressWarnings("unused")
        String uri = VOS_PREFIX + path;
        
        NodeService nodeService = new NodeService();
        String view = request.getParameter("view");
        if (view != null && view.compareTo("data") == 0) {        	
        	//pullFromVospace
        	try {        	    
        	    nodeService.executePullFromVospaceRequest(request, response);        	    
            } catch (VOSpaceException e) {
                VOSpaceExceptionHandler.handleVOSException(e, response);
            } catch (Exception e) {
                VOSpaceExceptionHandler.handleGenericException(e, response);
            }
        	
        } else {
        	// GET Request	        
	        try {
	            response.setContentType("text/html");
	            nodeService.executeGetRequest(request, response);
	        } catch (VOSpaceException e) {
	            VOSpaceExceptionHandler.handleVOSException(e, response);
	        } catch (Exception e) {
	            VOSpaceExceptionHandler.handleGenericException(e, response);
	        }
        }
        
        Log.debug("End of NodeServlet.doGet()");
    }

    /**
     * SET requests will be handled.
     */
    @Override
    public final void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        
        Log.debug("Into NodeServlet.doPost()");
        
        NodeService nodeService = new NodeService();
        String path = request.getPathInfo();        
        if (path == null) {
            path = "";
        }        
        @SuppressWarnings("unused")
        String uri = VOS_PREFIX + path;
        
        try {
            response.setContentType("text/html");
            nodeService.executePostRequest(request, response);
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, response);
        }
        
        Log.debug("End of NodeServlet.doPost()");
    }
    
    
    
    /**
     * CreateNode
     */
    @Override
    public final void doPut(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {        
        Log.debug("Into NodeServlet.doPut()");
        
        NodeService nodeService = new NodeService();
        String path = request.getPathInfo();
        if (path == null) {
            path = "";
        }
        @SuppressWarnings("unused")
        String uri = VOS_PREFIX + path;
        
        try {
            response.setContentType("text/html");
            nodeService.executePutRequest(request, response);
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, response);
        }

        Log.debug("End of NodeServlet.doPut()");
    }

    /**
     * DeleteNode
     */
    @Override
    public final void doDelete(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        
        Log.debug("Into NodeServlet.doDelete()");
        
        NodeService nodeService = new NodeService();
        String path = request.getPathInfo();        
        if (path == null) {
            path = "";
        }
        @SuppressWarnings("unused")
        String uri = VOS_PREFIX + path;
        
        try {
            nodeService.executeDeleteRequest(request, response);            
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, response);
        }
        
        Log.debug("End of NodeServlet.doDelete()");
    }
}
