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

import esavo.vospace.common.model.transferobjects.VoViewTO;
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
public class ViewServlet extends HttpServlet {

	/** Generated serival version uid. */
	private static final long serialVersionUID = 1L;

	/** Logging variable. */
	private static Log Log = LogFactory.getLog(ViewServlet.class);

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
	@Override
    public final void doGet(final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException {
	    Log.debug("Into ViewServlet.doGet()");
	    processRequest(request, response);
	}

	/**
	 * GetViews
	 * @param request
	 * @param response
	 */
	protected void processRequest (final HttpServletRequest request, final HttpServletResponse response) {
		
	    Log.debug("Into ViewServlet.processRequest()");
	    
	    VospaceServiceImpl vospaceService = new VospaceServiceImpl();
	    
	    Date start = new Date();
	    
		List<VoViewTO> acceptViewTOList = new ArrayList<VoViewTO>();
		List<VoViewTO> provideViewTOList = new ArrayList<VoViewTO>();
        try {
            acceptViewTOList = vospaceService.doQueryViews("ACCEPT");
            provideViewTOList = vospaceService.doQueryViews("PROVIDE");
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        }
			
		try {
            XMLWriter.writeGetViews(acceptViewTOList, provideViewTOList, response.getOutputStream());
            response.setStatus(HttpServletResponse.SC_OK); // 200
            response.setContentType("text/html");
            
            //MetadataRetrieval Log
            MetadataRetrievalLogTO log = ControlUtils.createMetadataLogTO(UserUtils.getCurrentContextControlUser(),
                    start, new Date(), "VIEWS");
            vospaceService.doInsertMetadataLog(log, false);
            
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, response);
        }
		Log.debug("End of ViewServlet.processRequest()");
	}
}
