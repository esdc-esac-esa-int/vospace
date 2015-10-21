package esavo.vospace.service.cmd.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.control.DataMngLogTO;
import esavo.vospace.common.model.transferobjects.control.TransferLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.database.ContainerNotFoundException;
import esavo.vospace.exceptions.database.DuplicateNodeException;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.exceptions.InvalidURIException;
import esavo.vospace.exceptions.database.LinkFoundException;
import esavo.vospace.exceptions.database.NodeNotFoundException;
import esavo.vospace.exceptions.PermissionDeniedException;
import esavo.vospace.exceptions.TypeNotSupportedException;
import esavo.vospace.service.utils.ControlUtils;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.access.AccessUtils;
import esavo.vospace.service.utils.access.UserUtils;
import esavo.vospace.service.utils.nodes.NodeUtils;
import esavo.vospace.service.utils.nodes.URIUtils;
import esavo.vospace.service.utils.nodes.URLUtils;
import esavo.vospace.json.JsonReader;
import esavo.vospace.json.JsonWriter;
import esavo.vospace.storage.LocalFileSystemStorageManager;
import esavo.vospace.xml.XMLReader;
import esavo.vospace.xml.XMLWriter;

/**
 * Service to manage node requests: get, create, set and delete.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class NodeService {

    /* Logging variable. */
    private static Log Log = LogFactory.getLog(NodeService.class);
    
    /* Buffer size to upload in streaming */
    private final int BUFFER_SIZE = 67108864; //64MB
    
    /* VOSpace Public Services*/
    private VospaceServiceImpl vospaceService;
    
    /* Storage system */
    private LocalFileSystemStorageManager storage;
    
    /* Current User logged */
    //private UserTO currentUser;
    
    /* Current ControlUser logged */
    //private UserControlTO userControl;
    
    /* Parameters used to verify URLs*/
    private boolean allowRoot;    
    private boolean checkTarget;
    
    /**
     * Constructor.
     */
    public NodeService() {
        vospaceService = new VospaceServiceImpl();
        storage = new LocalFileSystemStorageManager();
        //currentUser = UserUtils.getCurrentContextUser();
        //userControl = UserUtils.getCurrentContextControlUser();
    }  
    
    /**
     * Execute Pull from Vospace requests.
     * @param request
     * @param response
     * @throws InvalidURIException
     * @throws InternalFaultException
     * @throws NodeNotFoundException
     * @throws Exception
     */
    public void executePullFromVospaceRequest(final HttpServletRequest request, 
            final HttpServletResponse response) throws InvalidURIException, 
            InternalFaultException, NodeNotFoundException, Exception {
        
        Date start = new Date();
        String type = "PULL_FROM_VOSPACE_GET";
        long size = 0;
        
        String path = URIUtils.checkPathInfo(request.getPathInfo());
        VOSpaceURI targetURI = new VOSpaceURI(VOSConstants.VOS_PREFIX + path);
        
        // check if node exists
        NodeTO node = NodeUtils.getNode(targetURI, false);        
        if (node == null) {
            throw new NodeNotFoundException("Node not found with URI [" 
                    + targetURI + "].");
        }
        
        // Retrieve node content from FileSystem
        URI uri;
        try {
            uri = new URI(VOSConstants.FILESYSTEM_PATH + path);
        } catch (URISyntaxException e1) {
            throw new InvalidURIException("");
        }
        File f = new File(uri);
        if (f.isFile()) {

            size = f.length();
            
            InputStream is;
            try {
                is = this.storage.getBytes(uri);
            } catch (VOSpaceException e1) {
                throw new InternalFaultException("");
            }
            
            String name = "";
            try {
                name = URLDecoder.decode(targetURI.getName(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new InvalidURIException("");
            }

            response.setContentType("text/plain");
            response.setHeader("Content-Disposition",
                                "attachment;filename=\"" + name + "\"");
            
            ServletOutputStream out = response.getOutputStream();
            try {                

                byte[] buf = new byte[ BUFFER_SIZE ];
                int count = is.read( buf );
                while ( count > 0 ) {
                    out.write( buf, 0, count );
                    count = is.read( buf );
                    out.flush();
                }                
            } catch (IOException io) {
                Log.info("Downloading interrupted, details below... ");
                io.printStackTrace();
            }  finally {
                out.close();
                out = null;
                is.close();
                System.gc(); //call garbage collector - not guaranteed
            }
        }
        
        //transfer log          
        TransferLogTO transferLog = ControlUtils.createTransferLogTO (
                UserUtils.getCurrentContextControlUser(), start,
                new Date(), type, "HTTP", "", new Long(size));
        vospaceService.doInsertTransferLog(transferLog);
    	
    }
    
    /**
     * Execute Get requests.
     * @param request
     * @param response
     * @throws IllegalArgumentException
     * @throws InvalidURIException
     * @throws ContainerNotFoundException
     * @throws LinkFoundException
     * @throws NodeNotFoundException
     * @throws InternalFaultException
     * @throws Exception
     */
    public void executeGetRequest (final HttpServletRequest request, 
            final HttpServletResponse response) throws IllegalArgumentException, 
            InvalidURIException, ContainerNotFoundException, LinkFoundException, 
            NodeNotFoundException, InternalFaultException, Exception {
        
        Date start = new Date();
        String type = "GET";
        int size = 0;
        
        String path = URIUtils.checkPathInfo(request.getPathInfo()); // '/useraccount/node1/...'        
        VOSpaceURI uri = new VOSpaceURI(VOSConstants.VOS_PREFIX + path);
        
        this.checkTarget = true;
        this.allowRoot = true;
        
        // Check additional parameters
        String detailParam = request.getParameter("detail");
        String uriRequest = request.getParameter("uri");
        
        if (uriRequest == null) {
            uriRequest = "";
        }
        
        VOSpaceURI uriParam = new VOSpaceURI(uriRequest);
        String limitParam = request.getParameter("limit");
        
        
        NodeTO nodeResult = null;
        try {
            nodeResult = vospaceService.doQueryNode(uri, detailParam, uriParam, limitParam);
        } catch (VOSpaceException e) {
            e.printStackTrace();
            URIUtils.verifyNodeURL(uri, checkTarget, allowRoot);
            
            if (uriParam != null && !uriParam.toString().isEmpty()) {
                URIUtils.verifyNodeURL(uriParam, checkTarget, allowRoot);
            }
        }           
        
        //Verify operation permission
        UserTO currentUser = UserUtils.getCurrentContextUser();
        boolean authorized = AccessUtils.isAuthorized(currentUser, "read", nodeResult);
        /*boolean authorized = AccessUtils.isAuthorized("read", nodeResult);*/
        if (!authorized) {
            throw new PermissionDeniedException("PermissionDenied for URI [" + uri + "].");
        }
        
        //Log
        UserControlTO userControl = UserUtils.getCurrentContextControlUser();
        DataMngLogTO log = ControlUtils.createDataMngLog(userControl, 
                start, new Date(), type, uri.toString(), "", new Long(size));
        vospaceService.doInsertDataMngLog(log);
        
        // write response
        writeResponse(nodeResult, request.getParameter(GeneralConstants.JSON), 
                response.getOutputStream());
        response.setStatus(HttpServletResponse.SC_OK); //200
    }
    
    /**
     * Execute Post requests.
     * @param request
     * @param response
     * @throws InvalidURIException
     * @throws ContainerNotFoundException
     * @throws LinkFoundException
     * @throws NodeNotFoundException
     * @throws Exception
     */
    public void executePostRequest(final HttpServletRequest request, final HttpServletResponse response) 
            throws InvalidURIException, ContainerNotFoundException, 
            LinkFoundException, NodeNotFoundException, Exception {
        
        Date start = new Date();
        String type = "SET";
        int size = 0;
        
        this.checkTarget = true;
        this.allowRoot = false;
        
        //checkURL
        String url = request.getRequestURL().toString();
        URLUtils.verifyNodesRequest(url);
                
        //Read XML input        
        NodeTO nodeTO = XMLReader.readNode(request.getInputStream());
        if (nodeTO == null) {
            throw new Exception("Error reading node.");
        }
        nodeTO = NodeUtils.updateGroupManager(nodeTO, 
                UserUtils.getCurrentContextUser());
        nodeTO = NodeUtils.updateNodeOwner(nodeTO, 
                UserUtils.getCurrentContextUser());
        
        //Verify operation permission
        UserTO currentUser = UserUtils.getCurrentContextUser();
        boolean authorized = AccessUtils.isAuthorized(currentUser, "write", nodeTO);
        /*boolean authorized = AccessUtils.isAuthorized("write", nodeTO);*/
        if (!authorized) {
            throw new PermissionDeniedException("PermissionDenied for URI [" + nodeTO.getURI() + "].");
        }
        
        //Verify the node URL
        URIUtils.verifyNodeURL(nodeTO.getURI(), checkTarget, allowRoot);
        
        //Update node
        NodeTO nodeResult = VospaceServiceImpl.doSetNode(nodeTO);
        
        //Log
        UserControlTO userControl = UserUtils.getCurrentContextControlUser();
        DataMngLogTO log = ControlUtils.createDataMngLog(userControl/*UserUtils.getCurrentContextControlUser()*/, 
                start, new Date(), type, nodeTO.getURI().toString(), "", new Long(size));
        vospaceService.doInsertDataMngLog(log);
        
        // write node response
        writeResponse(nodeResult, request.getParameter(GeneralConstants.JSON),
                response.getOutputStream());
        response.setStatus(HttpServletResponse.SC_OK); //200
    }
    
    /**
     * Execute Put requests.
     * @param request
     * @param response
     * @throws InvalidURIException
     * @throws ContainerNotFoundException
     * @throws LinkFoundException
     * @throws NodeNotFoundException
     * @throws TypeNotSupportedException
     * @throws DuplicateNodeException
     * @throws PermissionDeniedException
     * @throws Exception
     */
    public void executePutRequest(final HttpServletRequest request, final HttpServletResponse response) 
            throws InvalidURIException, ContainerNotFoundException, LinkFoundException, 
            NodeNotFoundException, TypeNotSupportedException, DuplicateNodeException, 
            PermissionDeniedException,Exception {
        
        Date start = new Date();
        String type = "CREATE";
        
        this.checkTarget = false;
        
        UserTO currentUser = UserUtils.getCurrentContextUser();
        
        //checkURL
        String url = request.getRequestURL().toString();
        URLUtils.verifyNodesRequest(url);
                
        //Read input
        String json = request.getParameter(GeneralConstants.JSON);
        NodeTO nodeTO = null;
        //UserTO currentUser = UserUtils.getCurrentContextUser();
        if (json != null) {
            nodeTO = JsonReader.readNode(request.getInputStream());
            nodeTO = NodeUtils.updateNodeOwner(nodeTO, currentUser);
        } else {
            String xml = readXml(request.getInputStream());
            Log.debug(xml);
            nodeTO = XMLReader.readNode(xml);
            nodeTO = NodeUtils.updateNodeOwner(nodeTO, currentUser);
        }
        
        //Verify the node URL
        URIUtils.verifyNodeURL(nodeTO.getURI(), this.checkTarget, false);
        
        //Verify operation permission
        VOSpaceURI parentURI = nodeTO.getURI().getParentURI();
        boolean authorized = AccessUtils.isAuthorized(currentUser, "write", parentURI);
        if (!authorized) {
            throw new PermissionDeniedException("PermissionDenied for URI [" + nodeTO.getURI() + "].");
        }
        
        //check if node already exists
        NodeTO nodeFound = NodeUtils.getNode(nodeTO.getURI(), false);
        if (nodeFound != null) {
            throw new DuplicateNodeException("DuplicateNode for URI [" 
                    + nodeTO.getURI() + "].");
        }
        
        //check and fulfil the missing properties
        NodeUtils.checkBasicNodeProperties(nodeTO);
        
        //Insert node
        NodeTO nodeInserted;
        switch (nodeTO.getType()) {        
        	case CONTAINER_NODE:
        		nodeInserted = vospaceService.doInsertContainerNode((ContainerNodeTO) nodeTO);
        		break;
        	case LINK_NODE:
        		nodeInserted = vospaceService.doInsertLinkNode((LinkNodeTO) nodeTO);
        		break;
        	case STRUCTURED_DATA_NODE:
        		nodeInserted = vospaceService.doInsertStructuredDataNode((StructuredDataNodeTO) nodeTO);
        		break;
        	case UNSTRUCTURED_DATA_NODE:
        		nodeInserted = vospaceService.doInsertUnstructuredDataNode((UnstructuredDataNodeTO) nodeTO);
        		break;
        	default:
        		throw new TypeNotSupportedException("TypeNotSupported for URI [" + nodeTO.getURI() + "].");
        }
        //UserControlTO userControl = UserUtils.getCurrentContextControlUser();
        UserControlTO userControl = UserUtils.getCurrentContextControlUser();
        DataMngLogTO log = ControlUtils.createDataMngLog(userControl, 
                start, new Date(), type, nodeTO.getURI().toString(), "", 
                new Long(NodeUtils.retrieveNodeSize(nodeInserted)));
        vospaceService.doInsertDataMngLog(log);
        
        // write node response
        writeResponse(nodeInserted, json, response.getOutputStream());
        response.setStatus(HttpServletResponse.SC_CREATED); //201
    }
    
    public String readXml(InputStream is) throws IOException {
        byte[] buffer = new byte[4096];
        int read;
        StringBuilder sb = new StringBuilder();
        while ((read = is.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, read));
        }
        return sb.toString();
    }
    
    /**
     * Execute Delete requests.
     * @param request
     * @param response
     * @throws InvalidURIException
     * @throws ContainerNotFoundException
     * @throws LinkFoundException
     * @throws NodeNotFoundException
     * @throws PermissionDeniedException
     * @throws Exception
     */
    public void executeDeleteRequest(final HttpServletRequest request, 
            final HttpServletResponse response) throws InvalidURIException, 
            ContainerNotFoundException, LinkFoundException, 
            NodeNotFoundException, PermissionDeniedException, Exception {
        
        Date start = new Date();
        String type = "DELETE";
        
        this.checkTarget = false;
        this.allowRoot = false;
        
        UserTO currentUser = UserUtils.getCurrentContextUser();
        
        //checkURL
        String url = request.getRequestURL().toString();
        URLUtils.verifyNodesRequest(url);
                     
        String path = URIUtils.checkPathInfo(request.getPathInfo()); // '/useraccount/node1/...'
        VOSpaceURI uri = new VOSpaceURI(VOSConstants.VOS_PREFIX + path);                
        if (URIUtils.isRoot(uri))
            throw new PermissionDeniedException("PermissionDenied for Root"
                    + " URI [" + uri + "].");
                
        //check if node exists
        NodeTO nodeFound = NodeUtils.getNode(uri, true);
        if (nodeFound == null) {
            URIUtils.verifyNodeURL(uri, this.checkTarget, this.allowRoot);            
            throw new NodeNotFoundException("NodeNotFound for URI [" + uri + "].");
        }
        
        //Verify operation permission
        //UserTO currentUser = UserUtils.getCurrentContextUser();
        boolean authorized = AccessUtils.isAuthorized(currentUser, "write", nodeFound);
        if (!authorized) {
            throw new PermissionDeniedException("PermissionDenied for URI [" + uri + "].");
        }
        
        //Remove node from Vospace
        vospaceService.doRemoveNode(nodeFound);
        Long size = NodeUtils.retrieveNodeSize(nodeFound);
        currentUser = UserUtils.getCurrentContextUser(); //retrieve user with quota updated.
        
        //Log
        UserControlTO userControl = UserUtils.getCurrentContextControlUser();
        DataMngLogTO log = ControlUtils.createDataMngLog(userControl, start, new Date(),
                type, nodeFound.getURI().toString(), "", new Long(size));
        vospaceService.doInsertDataMngLog(log);
        
        String json = request.getParameter(GeneralConstants.JSON);
        if (json != null) {            
            writeResponse(currentUser, response.getOutputStream());
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT); //204
    }
    
    /*
     * Write node response
     */
    private void writeResponse(NodeTO nodeTO, String json, OutputStream out) 
            throws VOSpaceException {
        if (json != null) {
            JsonWriter.writeNode(nodeTO, out); // write Json response
        } else {
            XMLWriter.writeNode(nodeTO, out);
        }
    }
    
    /*
     * Write user response
     */
    private void writeResponse(UserTO userTO, OutputStream out) 
            throws VOSpaceException {
        JsonWriter.writeUser(userTO, out); // write Json response
    }
}
