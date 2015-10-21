package esavo.vospace.service.action;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.TransferService;
import esavo.vospace.service.utils.exceptions.VOSpaceExceptionHandler;

/**
 * Servlet implementation class DataServlet.
 */
public class DataServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
	/** Logging variable. */
    private static Log Log = LogFactory.getLog(DataServlet.class);
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
    @Override
	public void init(ServletConfig config) throws ServletException {
        super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Log.debug("Into DataServlet.doGet()");
		
		TransferService transferService = new TransferService();
		try {
			transferService.executeGetRequest(request, response);
		} catch (Exception e) {		    
		    VOSpaceExceptionHandler.handleGenericException(e, response);
		}
		
		Log.debug("End of DataServlet.doGet()");
	};
	
	/**
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Log.debug("Into DataServlet.doPut()");
        
        TransferService transferService = new TransferService();                
        try {
            transferService.executePutRequest(request, response);
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, response);
        }
       
       Log.debug("End of DataServlet.doPut()");
    }
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	/*protected void doPut(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    Log.debug("Into DataServlet.doPut()");
	    
	    TransferService transferService = new TransferService();
	    try {
            transferService.executePutRequest(request, response);
        } catch (InvalidURIException e) {
            response.setStatus(400 );
            throw new ServletException(e);
        } catch (QuotaExceededException e) {
            response.setStatus(409);
            throw new ServletException(e);
        } catch (VOSpaceException e) {
            Log.debug("VOSpaceException");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            Log.debug("Exception");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
	   
	   Log.debug("End of DataServlet.doPut()");
	}*/
}
