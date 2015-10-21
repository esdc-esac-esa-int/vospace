/**
 * 
 */
package esavo.vospace.service.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.common.model.transferobjects.control.MetadataRetrievalLogTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.utils.ControlUtils;
import esavo.vospace.service.utils.access.UserUtils;
import esavo.vospace.service.utils.exceptions.VOSpaceExceptionHandler;
import esavo.vospace.xml.XMLWriter;

/**
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 *
 */
public class ProtocolServlet extends HttpServlet {

	/** Generated serival version uid. */
	private static final long serialVersionUID = 1L;
		
	/** Logging variable. */
	private static Log Log = LogFactory.getLog(ProtocolServlet.class);
	
	@Override
	public final void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	/**
	* GET requests will be handled.
	* @param request Input http request.
	* @param response Input http response.
	* @throws ServletException If any Servlet related error is found.
	* @throws IOException If any i/o error is found.
	*/
	public final void doGet(final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException {
	    Log.debug("Into ProtocolServlet.doGet()");
	    processRequest(request, response);
	}
	
	/**
	 * Retrieve the list of VoProtocols of the service.
	 * @param request
	 * @param response
	 */
	protected void processRequest (final HttpServletRequest request, final HttpServletResponse response){
	    Log.debug("Into ProtocolServlet.processRequest()");
	    
	    VospaceServiceImpl vospaceService = new VospaceServiceImpl();
	    
	    Date start = new Date();
	    
	    List<VoProtocolTO> acceptProtocols = new ArrayList<VoProtocolTO>();
	    List<VoProtocolTO> provideProtocols = new ArrayList<VoProtocolTO>(); 
        try {
            acceptProtocols = vospaceService.doQueryProtocols("ACCEPT");
            provideProtocols = vospaceService.doQueryProtocols("PROVIDE");
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        }
	    
	    try {	        
            XMLWriter.writeGetProtocols(acceptProtocols, provideProtocols, response.getOutputStream());
            response.setStatus(HttpServletResponse.SC_OK); // 200
            response.setContentType("text/html");
            
            //MetadataRetrieval Log
            MetadataRetrievalLogTO log = ControlUtils.createMetadataLogTO(UserUtils.getCurrentContextControlUser(),
                    start, new Date(), "PROTOCOLS");
            vospaceService.doInsertMetadataLog(log, false);
            
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, response);
        }
        
	    Log.debug("End of ProtocolServlet.processRequest()");
	}

}
