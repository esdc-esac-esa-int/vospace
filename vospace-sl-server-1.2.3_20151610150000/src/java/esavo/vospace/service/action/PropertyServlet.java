/**
 * 
 */
package esavo.vospace.service.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import esavo.vospace.common.model.transferobjects.VoPropertyTO;
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
public class PropertyServlet extends HttpServlet {
	
	/** Generated serival version uid. */
	private static final long serialVersionUID = 1L;    
		
	/** Logging variable. */
	private static Log Log = LogFactory.getLog(PropertyServlet.class);
	
	@Override
	public final void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public final void doPost(final HttpServletRequest request, final HttpServletResponse response)
	        throws ServletException, IOException {
	        Log.debug("Into PropertyServlet.doGet()");
	        processRequest(request, response);
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
	    Log.debug("Into PropertyServlet.doGet()");
	    processRequest(request, response);
	}
	
	/**
	 * GetProperties
	 * @param request
	 * @param response
	 */
	protected void processRequest (final HttpServletRequest request, final HttpServletResponse response) {
		
	    Log.debug("Into PropertyServlet.processRequest()");
	    
	    /*try{
	    BufferedReader br = new BufferedReader(
                new InputStreamReader(request.getInputStream()));
	    
	    BufferedReader br= request.getReader();

        String[] files = null;
        String line = br.readLine();
        if (line != null) {
            line += line;
        }
	    }catch(IOException e) {
	        e.printStackTrace();
	    }*/
	    
	    VospaceServiceImpl vospaceService = new VospaceServiceImpl();
	    
	    Date start = new Date();
	    
	    List<VoPropertyTO> acceptList = null;
	    List<VoPropertyTO> provideList = null;
	    List<VoPropertyTO> containList = null;
        try {
            acceptList = vospaceService.doQueryProperties("ACCEPT");
            provideList = vospaceService.doQueryProperties("PROVIDE");
            containList = vospaceService.doQueryProperties("CONTAIN");
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        }
	    
	    try{
	        XMLWriter.writeGetProperties(acceptList, provideList, containList, response.getOutputStream());
	        response.setStatus(HttpServletResponse.SC_OK); // 200
            response.setContentType("text/html");
            
            //MetadataRetrieval Log
            MetadataRetrievalLogTO log = ControlUtils.createMetadataLogTO(UserUtils.getCurrentContextControlUser(),
                    start, new Date(), "PROPERTIES");
            vospaceService.doInsertMetadataLog(log, false);
            
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, response);
        }
	    
        Log.debug("End of PropertyServlet.processRequest()");
	}
}
