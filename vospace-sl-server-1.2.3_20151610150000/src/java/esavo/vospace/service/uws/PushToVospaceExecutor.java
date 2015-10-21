package esavo.vospace.service.uws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.uws.UwsException;
import esavo.uws.jobs.UwsJob;
import esavo.uws.jobs.UwsJobErrorSummaryMeta;
import esavo.uws.jobs.UwsJobResultMeta;
import esavo.uws.jobs.parameters.UwsJobParameters;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.ProtocolParamTO;
import esavo.vospace.common.model.transferobjects.TransferTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.exceptions.PermissionDeniedException;
import esavo.vospace.exceptions.UnsupportedOperationException;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.database.NodeNotFoundException;
import esavo.vospace.service.action.TransferServlet;
import esavo.vospace.service.cmd.service.TransferService;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.access.AccessUtils;
import esavo.vospace.service.utils.access.UserUtils;
import esavo.vospace.service.utils.nodes.NodeUtils;
import esavo.vospace.service.utils.nodes.URIUtils;
import esavo.vospace.service.utils.nodes.VospaceURIUtils;

/**
 * PushToVospace Executor. 
 * 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2015
 *
 */
public class PushToVospaceExecutor extends VOSpaceExecutor {

    /** Logging variable. */
    private static Log Log = LogFactory.getLog(PushToVospaceExecutor.class);
    
    /* Transfer TransferObject */
    private TransferTO transfer;
    
    /* Result Name */
    private final String RESULT_NAME = "transferDetails";
    
    private final String LIST_NAME = "async";
    
    /* Transfer service */
    private TransferService transferService;
    
    private VospaceServiceImpl vospaceService;
    
    private UserTO currentUser;
    private UserControlTO userControl;
    
    private UwsJob job;
    
    @Override
    public Object execute(UwsJob j) throws InterruptedException, UwsException {
        
        Log.debug("Into PushToVospaceExecutor.execute()");
        
        this.job = j;
        
        this.currentUser = UserUtils.getCurrentContextUser();
        this.userControl  = UserUtils.getCurrentContextControlUser();
        
        transferService = new TransferService(this.currentUser, this.userControl);
                
        try {
            transfer = transferService.createTransferFromJob(j);
        } catch (VOSpaceException e) {
            e.printStackTrace();
            throw new UwsException(e.getMessage(), e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UwsException(e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        UwsJobResultMeta result = (UwsJobResultMeta) jobWork();
        
        Log.debug("End of PushToVospaceExecutor.execute()");
        
        return result;
    }
    
    /*private TransferTO createTransferFromJob(UwsJob job) throws VOSpaceException, IOException {
        
        TransferTO transfer = new TransferTO();
        UwsJobParameters jobParams = job.getParameters();
        String targetUri = null;
        String protocolUri = null;
        String viewUri = null;
        String direction = null;
        Object keepBytes = null;
        try {
            targetUri = jobParams.getStringParameter(GeneralConstants.VOS_TARGET);        
            protocolUri = jobParams.getStringParameter(GeneralConstants.VOS_PROTOCOL);        
            viewUri = jobParams.getStringParameter(GeneralConstants.VOS_VIEW);
            direction = jobParams.getStringParameter(GeneralConstants.VOS_DIRECTION);
            keepBytes = jobParams.getParameter(GeneralConstants.VOS_KEEPBYTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        VOSpaceURI target = null;
        VOSpaceURI protocolURI = null;
        VOSpaceURI viewURI = null;
        if (targetUri != null) {
            target = new VOSpaceURI(targetUri);
            transfer.setTarget(target);
        }
        if (direction != null)
            transfer.setDirection(direction);
        if (protocolUri != null) {
            protocolURI = new VOSpaceURI(protocolUri);
            VoProtocolTO protocol = new VoProtocolTO();
            protocol.setURI(protocolURI);
            protocol.setParams(new ArrayList<ProtocolParamTO>());
            transfer.setProtocol(protocol);
        }
        if (viewUri != null) {
            viewURI = new VOSpaceURI(viewUri);
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
    }*/
    
    public Object jobWork() throws UwsException, InterruptedException {
        Log.debug("Into PushToVospaceThread.jobWork()");
        
        UwsJobResultMeta result = null;
        VOSpaceURI targetURI = null;
        
        try {
            targetURI = this.transfer.getTarget();                
            NodeTO node = NodeUtils.getNode(targetURI, false);
            if (node == null)
                throw new NodeNotFoundException("Node not found for URI [ " + targetURI + " ].");
            else {
                
                if (node instanceof ContainerNodeTO) {
                    boolean authorized = AccessUtils.isAuthorized(this.currentUser, "write", node);
                    if (!authorized) {
                        throw new PermissionDeniedException("PermissionDenied for URI [" 
                                + targetURI + "].");
                    }
                } else {
                    // Types not authorized: LinkNode and DataNode
                    throw new UnsupportedOperationException("PushToVospace is not "
                            + "supported for LinkNode URI [ " + targetURI + " ].");
                }
                
            }
            
            setPushNode();
            
            
            String userName = VospaceURIUtils.getUserAccount(targetURI.toString());
                
            // .../data/<user-name>/job-id
            String dataTransferServlet = "servlet" + VOSConstants.BAR + VOSConstants.DATA_SERVICE;
            String endpoint = VOSConstants.SERVER_URL + dataTransferServlet + VOSConstants.BAR 
                    + userName + VOSConstants.BAR + job.getJobId(); 
                
            VoProtocolTO protocol = this.transfer.getProtocol();
            protocol.setEndPoint(new VOSpaceURI(endpoint));
                
            String dataFolderPath = VOSConstants.FILESYSTEM_PATH + VOSConstants.BAR 
                    + currentUser.getName() + VOSConstants.BAR 
                    + VOSConstants.DATA_FOLDER;
            transferService.createTransferFile(transfer, job.getJobId(), dataFolderPath);
            
            String resultRef = createResultRef(LIST_NAME, RESULT_NAME);
            result = writeResult(resultRef, job);                                
            job.addResult(result);
            
        } catch (Exception e) {            
            Log.debug("UwsException executing PushFromVOSpace nodes for Target '" + targetURI.getName() + "'.");
            UwsJobErrorSummaryMeta errorSummary = writeError(e.getMessage());
            job.setErrorSummary(errorSummary);
            throw new UwsException(e.getMessage(),e);
        }        
        if (job.isPhaseAborted())
            throw new InterruptedException();
        
        Log.debug("End of PushToVospaceThread.jobWork()");
        
        return result;
    }
    
    /*
     * Create the target node to make a PushToVospace operation.
     */
    private void setPushNode() 
            throws InternalFaultException {
        
        //check if target exists
        VOSpaceURI targetURI = transfer.getTarget();
        NodeTO targetNodeTO = NodeUtils.getNode(transfer.getTarget(), false);
                
        if (targetNodeTO == null) {
            try {
                //verify targetURI
                URIUtils.verifyNodeURL(targetURI, false, false);
                
                //create node of default type -> UnstructuredDataNode
                UnstructuredDataNodeTO node = new UnstructuredDataNodeTO();
                node.setUri(targetURI);
                UserTO user = new UserTO();
                String userName = VospaceURIUtils.getUserAccount(targetURI.toString());
                user.setName(userName);
                node.setUserTO(user);
                //Add properties                
                NodePropertyTO lengthProp = NodeUtils.createLengthProperty(0, false);
                Date now = new Date();
                NodePropertyTO bDateProp = NodeUtils.createBDateProperty(now, false);
                NodePropertyTO mDateProp = NodeUtils.createMDateProperty(now, false);
                List<NodePropertyTO> properties = new ArrayList<NodePropertyTO>();
                properties.add(lengthProp);
                properties.add(bDateProp);
                properties.add(mDateProp);                
                node.setNodeProperties(properties);
                // Insert node
                vospaceService.doInsertUnstructuredDataNode(node);
            } catch (VOSpaceException e) {
                Log.debug("TransferService.pushToVospace() Created target for URI ["
                        + targetURI + "].");
                throw new InternalFaultException("Error creating push node for URI [" 
                        + targetURI + "].");
            }            
        }
    }
    
    /*
     * Create the result link to the Result.
     */
    private String createResultRef (String jobList, String resultName) {
        String resultRef = TransferServlet.REST_TRANSFER_ENDPOINT + VOSConstants.BAR 
                + jobList + VOSConstants.BAR + job.getJobId() + VOSConstants.BAR 
                + "results" + VOSConstants.BAR + resultName;
        return resultRef;
    }
    
    /*
     * Create and write transferTO to result
     */
    /*private Result createResult(String resultRef, TransferTO transfer) {
        Result result = null;
        try {
            URL url = new URL(resultRef);
            result = new Result(RESULT_NAME, "simple", url.getRef(), true);
            
            // Write the result
            OutputStream out = getResultOutput(result);
            XMLWriter.writeTransfer(transfer, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.debug("Transfer Error: error creating the result for Target [" 
                    + transfer.getTarget() + "].");
        }
        return result;
    }*/

}
