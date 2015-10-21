package esavo.vospace.service.cmd.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.uws.jobs.UwsJob;
import esavo.uws.jobs.parameters.UwsJobParameters;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.DataNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.ProtocolParamTO;
import esavo.vospace.common.model.transferobjects.TransferTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceNodeType;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.common.model.transferobjects.control.TransferLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.database.DuplicateNodeException;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.exceptions.InvalidURIException;
import esavo.vospace.exceptions.QuotaExceededException;
import esavo.vospace.exceptions.database.NodeNotFoundException;
import esavo.vospace.exceptions.PermissionDeniedException;
import esavo.vospace.exceptions.UnsupportedOperationException;
import esavo.vospace.service.utils.ControlUtils;
import esavo.vospace.service.utils.GenericUtils;
import esavo.vospace.service.utils.NameUtils;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.access.AccessUtils;
import esavo.vospace.service.utils.access.UserUtils;
import esavo.vospace.service.utils.compress.CompressionUtils;
import esavo.vospace.service.utils.nodes.NodeUtils;
import esavo.vospace.service.utils.nodes.URIUtils;
import esavo.vospace.service.utils.nodes.VospaceURIUtils;
import esavo.vospace.storage.LocalFileSystemStorageManager;
import esavo.vospace.xml.XMLReader;
import esavo.vospace.xml.XMLWriter;

/**
 * TransferService in charge of making the movements of nodes.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
@SuppressWarnings("unused")
public class TransferService {

    /* Logging variable. */
    private static Log Log = LogFactory.getLog(TransferService.class);
    
    /* VOSpace Public Services*/
    private VospaceServiceImpl vospaceService;
    
    /* Current User logged */
    private UserTO currentUser;
    
    /* Current ControlUser logged */
    private UserControlTO userControl;
    
    /* Buffer size to upload in streaming */
    private final int BUFFER_SIZE = 67108864; //64MB
    
    /* CONTENT-TYPE */
    private final String GZ_CONTENT_TYPE = "application/x-tar-gz";
    private final String ZIP_CONTENT_TYPE = "application/zip";
    
    /* CONTENT-ENCODING */
    private final String GZ_CONTENT_ENCODING = "x-tar-gz";
    private final String ZIP_CONTENT_ENCODING = "zip";
    
    /* COMPRESS EXTENSION */
    private final String GZ_EXT = ".tar.gz";
    private final String ZIP_EXT = ".zip";
    
    /**
     * Default constructor.
     */
    public TransferService () {
        vospaceService = new VospaceServiceImpl();
        this.currentUser = UserUtils.getCurrentContextUser();
        this.userControl = UserUtils.getCurrentContextControlUser();
    }
    
    /**
     * Constructor used from job threads.
     * @param current
     * @param control
     */
    public TransferService (UserTO current, UserControlTO control) {
        vospaceService = new VospaceServiceImpl();
        this.currentUser = current;
        this.userControl = control;
    }
    
    /**
     * Downloading data from a PullFromVospace request to .../servlet/transfers 
     * @param request
     * @param response
     * @throws IOException
     * @throws InternalFaultException
     */
    public void executeGetRequest (HttpServletRequest request, HttpServletResponse response) 
            throws IOException, InternalFaultException, VOSpaceException {
        
        Date startTampstamp = new Date();
        String type = "PULL_FROM_VOSPACE_POST"; //comes from HTTP POST of a Job representation for the transfer
        long size = 0;
        
        String path = request.getPathInfo();
        if (path == null || path.isEmpty()) {
            return;
        }
        path = path.substring(1);
        
        String[] pathSplitted = path.split(VOSConstants.BAR);
        String resultFileName = pathSplitted[1] + "_result";
        
        //UserTO currentUser = UserUtils.getCurrentContextUser();
        String dataFolderPath = VOSConstants.FILESYSTEM_PATH + VOSConstants.BAR 
                                + currentUser.getName() + VOSConstants.BAR 
                                + VOSConstants.DATA_FOLDER;
        String transferPath = dataFolderPath + VOSConstants.BAR + resultFileName;
        TransferTO transfer = getTransferFromResultFile(transferPath);
        
        if (transfer == null) {
            throw new InternalFaultException("InternalFault, operation fails.");
        }
        VOSpaceURI target = transfer.getTarget(); //already checked by TransferThread.java
                
        NodeTO downloadNode = NodeUtils.getNode(target, false);
        if (downloadNode == null)
            throw new NodeNotFoundException("NodeNotFound for URI [ " + target + " ].");
        
        // ------------
        // Downloading
        // ------------
        try {
            downloadNode(response, target.getPath().substring(1), "documents-export", true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        // remove transfer file
        try {
            URI uri = new URI(transferPath);
            File transferFile = new File(uri);
            transferFile.delete();
        } catch (Exception e) {
            Log.debug("Error removing transfer file...");
            e.printStackTrace();
        }
        
        //transfer log
        //UserControlTO userControl  = UserUtils.getCurrentContextControlUser();
        TransferLogTO transferLog = ControlUtils.createTransferLogTO(userControl, 
                startTampstamp, new Date(), type, transfer.getProtocol(), 
                transfer.getView(), new Long(size));
        vospaceService.doInsertTransferLog(transferLog);
    }
    
    /**
     * 
     * @param response
     * @param path
     * @param name
     * @param spec, whether if comes or not from the specification
     * @throws Exception
     */
    public void downloadNode (HttpServletResponse response, String path, String name, Boolean spec) throws Exception {
        
        LocalFileSystemStorageManager fs = new LocalFileSystemStorageManager();
        URI uri = null;
        try {
            uri = new URI(path);
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        
        InputStream is = null;
        String[] files = new String[1];
        if (!spec) {            
            try {
                is = fs.getBytes(uri);
            } catch (VOSpaceException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is));
            String line = br.readLine();
            if (line != null) {
                files = GenericUtils.getFiles (line);
            }
        } else {
            files[0] = path;
        }
        
        if (files != null && files.length > 0) {

            double r = Math.random()*100;
            long number = Math.round(r);
            String compressedName = name + "-" + number + ZIP_EXT;
            response.setContentType(ZIP_CONTENT_TYPE);
            response.addHeader("Content-Encoding", ZIP_CONTENT_ENCODING);
            response.setHeader("Content-Disposition",
                                    "attachment;filename=\"" + compressedName + "\"");

            response.setBufferSize(BUFFER_SIZE);
            ServletOutputStream sos = response.getOutputStream();
            ZipOutputStream out = new ZipOutputStream(sos);
            //GzipCompressorOutputStream out = new GzipCompressorOutputStream(sos);
            //TarArchiveOutputStream tos = new TarArchiveOutputStream(out);
            
            

            try {

                int count = 0;
                //Zip the list of files
                while (count < files.length) {
                    String filePath = files[count++]; //file path
                    String filetozip = VOSConstants.DOWNLOAD_PATH + VOSConstants.BAR + filePath;
                    File fileItem = new File(filetozip);
                    if (fileItem.isDirectory()) {
                        CompressionUtils.zipFolder(fileItem, "", out);
                        //CompressionUtils.targzFolder(fileItem, "", tos);
                    } else if (fileItem.isFile()){
                        CompressionUtils.zipFile(fileItem, out);
                        //CompressionUtils.targzFile(fileItem, tos);
                    }
                }

            } finally {
                /*tos.finish();
                tos.close();
                tos = null;*/
                out.finish();
                out.close();
                out = null; //explicit memory free
                
                if (is != null)
                    is.close();
                System.gc(); //call garbage collector - not guaranteed
                try {
                    fs.removeBytes(uri); //Delete file in tmp folder
                } catch (VOSpaceException e) {
                    Log.info(e.getMessage());
                }
            }
        }
    }
    
    /**
     * Upload of node to a given address provided by the service in a step before.
     * 
     * @param req
     * @param resp
     * @throws NodeNotFoundException
     */
    public void executePutRequest (HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        
        String path = req.getPathInfo();
        if (path == null || path.isEmpty()) {
            return;
        }
        path = path.substring(1);
        
        String[] pathSplitted = path.split(VOSConstants.BAR);
        String fileName = pathSplitted[1] + "_result";
        
        
        //UserTO currentUser = UserUtils.getCurrentContextUser();
        String dataFolderPath = VOSConstants.FILESYSTEM_PATH + VOSConstants.BAR 
                                + currentUser.getName() + VOSConstants.BAR 
                                + VOSConstants.DATA_FOLDER;
        String transferPath = dataFolderPath + VOSConstants.BAR + fileName;
        TransferTO transfer = getTransferFromResultFile(transferPath);          
        VOSpaceURI target = transfer.getTarget();
        
        // ---------
        // Uploading
        // ---------        
        uploadNode (req, target, "PUSH_TO_VOSPACE");
        
        // remove transfer file
        URI uri = new URI(transferPath);
        File transferFile = new File(uri);
        transferFile.delete();
    }
    
    /**
     * Upload a file represented by nodeTarget into targetPath.
     * @param req
     * @param targetPath
     * @param nodeTarget
     * @return the list of nodes uploaded
     * @throws Exception 
     */
    public List<NodeTO> uploadNode (HttpServletRequest req, VOSpaceURI target, String operType)
            throws Exception {
        
        NodeTO nodeTarget = NodeUtils.getNode(target, false);
        if (nodeTarget == null) {
            throw new NodeNotFoundException("Node not found for URI [ " + target + " ].");
        }        
        boolean isContainer = nodeTarget instanceof ContainerNodeTO;
        
        // Check that we have a multipart/ request
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if( !isMultipart ) return null;
        
        // SIZE pre-check
        // this is not the file length but a similar figure
        long approxFileSize = Long.parseLong(req.getHeader("content-length"));        
        long quota = vospaceService.doQueryUserQuota(nodeTarget.getUserTO());
        long qlimit = nodeTarget.getUserTO().getQLimit();
        //check quota available before going further
        if (qlimit < (quota + approxFileSize)) {
            throw new QuotaExceededException("QuotaExceededException for user -> " + quota);
        }
        // SIZE pre-check
        
        ServletFileUpload upload = new ServletFileUpload();
        upload.setSizeMax(GeneralConstants.MAX_FILESIZE);                 
        List<NodeTO> uploadedNodeList = new ArrayList<NodeTO>();
        FileItemIterator it = upload.getItemIterator(req);
        while ( it.hasNext () ) {
            
            FileItemStream fileItem = it.next();             
            if (!fileItem.isFormField()) {//is an uploaded file
                Date start = new Date();

                String uploadName = fileItem.getName();
                if (GenericUtils.isIEBrowser(req)) { // in case of browser is IE
                    // is IE
                    String path = uploadName;
                    int index = path.lastIndexOf("\\");
                    uploadName = path.substring(index+1);
                }
                
                // check the name of the fileItem
                if (!NameUtils.processTextArea(uploadName)) {
                    String msg = "Invalid file " + uploadName + " name!. "
                            + "The following characters are invalid"
                            + " ` ~ , : ; ' ? ^ \" < > / | } { % # $ & ! = + ";
                    throw new InvalidURIException(msg);
                }
                
                String nodeURI = VOSConstants.FILESYSTEM_PATH + target.getPath();                
                String name = NameUtils.encodeUrl(uploadName);
                URI fileURI = null;
                if (isContainer) { fileURI = new URI(nodeURI + File.separator + name); }
                else { fileURI = new URI(nodeURI); }//upload data directly to target                               
                
                // check if target node exists
                String mtimeValue = "";
                String lengthValue = "";
                NodePropertyTO length = null;
                NodePropertyTO mTime = null;
                NodeTO nodeInserted = null;
                long oldSize = 0;
                if (isContainer) { //if target is Container
                    
                    //check if node exists
                    VOSpaceURI uriTarget = new VOSpaceURI(target + File.separator + name);
                    NodeTO node = NodeUtils.getNode(uriTarget, false);
                    if (node == null) {                    
                        nodeTarget = NodeUtils.createDefaultNode(currentUser, uriTarget, approxFileSize);
                        nodeInserted = vospaceService.doInsertUnstructuredDataNode((UnstructuredDataNodeTO) nodeTarget);
                    } else {
                        // overwrite operation                        
                        if (node instanceof ContainerNodeTO) {                            
                            throw new UnsupportedOperationException("UnsupportedOperationException: ContainerNode with name '" +
                                    name + "' already exists.");                            
                        } else if (node instanceof DataNodeTO) { //unstructured or structured
                            
                            // save old properties for: modification time and length
                            length = NodeUtils.retrieveNodeProperty(node, GeneralConstants.PROPERTY_LENGTH);
                            mTime = NodeUtils.retrieveNodeProperty(node, GeneralConstants.PROPERTY_MTIME);                    
                            if (mTime != null) mtimeValue = mTime.getValue();                                        
                            if (length != null) lengthValue = length.getValue();                    
                            oldSize = Long.parseLong(lengthValue);
                            
                            //Update properties: mDate and length
                            NodeUtils.updateMDateProperty(mTime, false);
                            length.setValue(Long.toString(approxFileSize));
                            nodeInserted = (UnstructuredDataNodeTO) VospaceServiceImpl.doSetNode(node, oldSize);                            
                        }
                    }
                }
                
                //Upload
                Log.debug("Writing file [ " + fileURI + " ].");
                long realFileSize = 0;
                File f = new File(fileURI);
                FileOutputStream fout= new FileOutputStream (f);
                BufferedOutputStream bout= new BufferedOutputStream (fout);
                InputStream stream = fileItem.openStream();
                try {                    
                    int i;
                    byte data[] = new byte[BUFFER_SIZE];
                    while ((i = stream.read(data)) != -1) {
                        bout.write(data, 0, i);
                        bout.flush();
                        realFileSize += i;
                    }
                    
                    //Update file size to the real one
                    NodePropertyTO lengthP = NodeUtils.retrieveNodeProperty(nodeInserted, 
                            GeneralConstants.PROPERTY_LENGTH);
                    lengthP.setValue(Long.toString(realFileSize));
                    nodeInserted = VospaceServiceImpl.doSetNode(nodeInserted, approxFileSize);
                                        
                } catch (IOException e) { 
                    e.printStackTrace();
                    // Roleback database
                     if (isContainer) {
                        //remove node inserted
                        //TODO remove the following comment after the tests
                        vospaceService.doRemoveNode(nodeInserted);
                         
                     }else {
                        //restore properties
                        length.setValue(lengthValue);
                        mTime.setValue(mtimeValue);
                        nodeInserted = (UnstructuredDataNodeTO) VospaceServiceImpl.doSetNode(nodeTarget);
                    }
                } finally {
                    bout.close();
                    bout = null;
                    if (stream != null)
                        stream.close();
                    System.gc(); //call garbage collector - not guaranteed
                }
                
                uploadedNodeList.add(nodeInserted);

                //transfer log
                //UserControlTO userControl  = UserUtils.getCurrentContextControlUser();
                TransferLogTO transferLog = ControlUtils.createTransferLogTO (
                        userControl, start,
                        new Date(), operType, "HTTP", "", approxFileSize);
                vospaceService.doInsertTransferLog(transferLog);
            }
        }
        
        return uploadedNodeList;
    }
    
    
    
    /*
     * Retrieve TransferTO from file path
     */
    private TransferTO getTransferFromResultFile (String filePath) {        
        try {
          URI uri = new URI(filePath);
          //read File
          File transferFile = new File(uri);
          return XMLReader.readTransfer(transferFile);
        } catch (Exception e) {
            e.printStackTrace();
            Log.debug("DataServlet.getTransferFromResultFile() Error with filepat [" +
                    filePath + "].");
        }
        return null;
    }
    
    /**
     * Move  target into direction.
     * @param target
     * @param direction
     * @throws NodeNotFoundException
     * @throws DuplicateNodeException
     * @throws InternalFaultException
     * @throws InvalidURIException
     */
    public void moveNode (final String target, final String direction/*, final UserTO currentUser*/) 
            throws NodeNotFoundException, DuplicateNodeException, 
            InternalFaultException, InvalidURIException, PermissionDeniedException,
            VOSpaceException {
        
        transferNode(target, direction, false, currentUser);
                
    }

    /**
     * Copy target into direction.
     * @param target
     * @param direction
     * @throws NodeNotFoundException
     * @throws DuplicateNodeException
     * @throws InternalFaultException
     * @throws InvalidURIException
     */
    public void copyNode (final String target, final String direction/*, final UserTO currentUser*/)
            throws NodeNotFoundException, DuplicateNodeException, 
            InternalFaultException, InvalidURIException, PermissionDeniedException,
            VOSpaceException {
        
        transferNode(target, direction, true, currentUser);
        
    }
    
    /*
     * Make the transfer: move or copy.
     */
    private void transferNode (final String target, final String direction,
            final Boolean keepBytes, UserTO currentUser) throws VOSpaceException {
        
        if (target == null || target.isEmpty()) {
            throw new InvalidURIException("InvalidURI for Target URI [" + target + "]/");
        }
        
        if (direction == null || direction.isEmpty()) {
            throw new InvalidURIException("InvalidURI for Target URI [" + direction + "]/");
        }
        
        //check if target exists and if it's not Root
        VOSpaceURI targetURI = new VOSpaceURI(target);
        if (URIUtils.isRoot(targetURI))
            throw new PermissionDeniedException("PermissionDenied for Root "
                    + "URI [" + targetURI + "].");
        
        NodeTO targetNodeTO = NodeUtils.getNode(targetURI, true);
        if (targetNodeTO == null)
            throw new NodeNotFoundException("NodeNotFound for URI [" + targetURI + "]");
                
        //Verify operation permission
        boolean authorized = AccessUtils.isAuthorized(currentUser, "write", targetNodeTO);
        if (!authorized) {
            throw new PermissionDeniedException("PermissionDenied for URI [" + targetURI + "]");
        }
        
        //check if destination exists
        VOSpaceURI directionURI = new VOSpaceURI(direction);
        NodeTO destNodeTO = NodeUtils.getNode(directionURI, true);
        if (destNodeTO == null)
            throw new NodeNotFoundException("NodeNotFound for URI [" + direction + "]");
        //Verify operation permission
        authorized = AccessUtils.isAuthorized(currentUser, "write", destNodeTO);
        if (!authorized) {
            throw new PermissionDeniedException("PermissionDenied for URI [" + direction + "]");
        }
        
        // Check of additional rules to move and copy nodes between end points
        String userLogged = currentUser.getName();
        String userAccountDest = VospaceURIUtils.getUserAccount(directionURI.toString());
        if (!keepBytes) { //only move within your VOSpace account is allowed by the system            
            String userAccountTarget = VospaceURIUtils.getUserAccount(targetURI.toString());            
            if (!(userLogged.compareTo(userAccountDest) == 0 && userLogged.compareTo(userAccountTarget) == 0)) {
                throw new UnsupportedOperationException("Move operation is only allowed within your user account.");
            }
        } else { //only copy from another to your space is allowed by the system
            if (userLogged.compareTo(userAccountDest) != 0) {
                throw new UnsupportedOperationException("Copy operation is only allowed to your user account.");
            }
        }
        
        // check if destination node
        // destination should be a ContainerNode
        if (destNodeTO != null && (destNodeTO.getType() != VOSpaceNodeType.CONTAINER_NODE)) {
            throw new DuplicateNodeException("DuplicateNode for URI [" + directionURI + "]");
        }
        
        // move operation
        vospaceService.doTransferNode(targetNodeTO, destNodeTO, keepBytes, currentUser);
    }
    
    /*
     * Creates a TransferFile including the endpoint of the Transfer.
     * The name of the file is: <jobId>_result
     * and will put under .data folder underneath the specific user.
     */
    public void createTransferFile(TransferTO transfer, String jobId, 
            String dataFolder) throws VOSpaceException, 
        IOException, URISyntaxException {
    
        File f = new File(new URI(dataFolder));
        if (!f.exists()) { //create .data folder if not exists
            f.mkdir();
        }
        String fileName = jobId + "_result";
        URI fileURI = new URI(dataFolder + VOSConstants.BAR + fileName); // creates transfer file                
        File resultFile = new File(fileURI);
        resultFile.createNewFile();
        
        // writes transfer into file
        XMLWriter.writeTransfer(transfer, resultFile);
    }
    
    public TransferTO createTransferFromJob(UwsJob job) throws VOSpaceException, IOException {
        
        TransferTO transfer = new TransferTO();
        UwsJobParameters jobParams = job.getParameters();
        VOSpaceURI targetUri = (VOSpaceURI) jobParams.getParameter(GeneralConstants.VOS_TARGET);
        VOSpaceURI protocolUri = (VOSpaceURI) jobParams.getParameter(GeneralConstants.VOS_PROTOCOL);
        VOSpaceURI viewUri = (VOSpaceURI) jobParams.getParameter(GeneralConstants.VOS_VIEW);
        String direction = jobParams.getStringParameter(GeneralConstants.VOS_DIRECTION);
        Object keepBytes = jobParams.getParameter(GeneralConstants.VOS_KEEPBYTES);
        
        VOSpaceURI target = null;
        VOSpaceURI protocolURI = null;
        VOSpaceURI viewURI = null;
        if (targetUri != null) {
            target = targetUri;
            transfer.setTarget(target);
        }
        if (direction != null)
            transfer.setDirection(direction);
        if (protocolUri != null) {
            protocolURI = protocolUri;
            VoProtocolTO protocol = new VoProtocolTO();
            protocol.setURI(protocolURI);
            protocol.setParams(new ArrayList<ProtocolParamTO>());
            transfer.setProtocol(protocol);
        }
        if (viewUri != null) {
            viewURI = viewUri;
            VoViewTO view = new VoViewTO();
            view.setURI(viewURI);
            transfer.setView(view);
        } else {
            VoViewTO viewTO = new VoViewTO();
            viewTO.setURI(new VOSpaceURI(GeneralConstants.VOS_PREFIX)); //dummy value
            transfer.setView(viewTO);
        }
        if (keepBytes != null)
            transfer.setKeepBytes((Boolean)keepBytes);
        
        return transfer;
    }
}
