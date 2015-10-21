package esavo.vospace.service.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.json.JsonWriter;
import esavo.vospace.service.cmd.service.TransferService;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.exceptions.VOSpaceExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet to upload files into the server repository.
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 * 
 */
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 7915008438298589644L;

    /** Logging variable. */
    private static Log Log = LogFactory.getLog(UploadServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        Log.debug("Into UploadServlet.doPost()");
        
        String path = req.getPathInfo();

        if (path.compareTo(File.separator) == 0)
            path = "";
        
        String nodeURI = VOSConstants.VOS_PREFIX + path;
        
        TransferService transferService = new TransferService();
        List<NodeTO> uploadedNodeList = new ArrayList<NodeTO>();
        try {
            uploadedNodeList = transferService.uploadNode(req, new VOSpaceURI(nodeURI), "UPLOAD");
            // write response
            JsonWriter.writeNodeList(uploadedNodeList, resp.getOutputStream());
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, resp);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, resp);
        }
        
        Log.debug("End of UploadServlet.doPost()");
    }
}
