package esavo.vospace.service.uws;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.uws.UwsException;
import esavo.uws.jobs.UwsJob;
import esavo.uws.jobs.UwsJobErrorSummaryMeta;
import esavo.uws.jobs.UwsJobResultMeta;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.TransferTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.exceptions.PermissionDeniedException;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.database.NodeNotFoundException;
import esavo.vospace.service.cmd.service.TransferService;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.access.AccessUtils;
import esavo.vospace.service.utils.access.UserUtils;
import esavo.vospace.service.utils.nodes.NodeUtils;
import esavo.vospace.service.utils.nodes.VospaceURIUtils;

/**
 * PullFromVOSpace executor.
 * 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2015
 *
 */
public class PullFromVospaceExecutor extends VOSpaceExecutor {

    /** Logging variable. */
    private static Log Log = LogFactory.getLog(PullFromVospaceExecutor.class);
    
    /* Result Name */
    private final String RESULT_NAME = "transferDetails";
    
    private final String LIST_NAME = "async";
    
    /* Transfer TransferObject */
    private TransferTO transfer;
    
    /* Service transfer */
    private TransferService transferService;
    
    private UserTO currentUser;
    private UserControlTO userControl;
    
    private UwsJob job;
    
    @Override
    public Object execute(UwsJob j) throws InterruptedException, UwsException {
        
        Log.debug("End of PullFromVospaceExecutor.execute()");
        
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
        }
        
        UwsJobResultMeta result = (UwsJobResultMeta) jobWork();
        
        Log.debug("End of PullFromVospaceExecutor.execute()");
        
        return result;
    }
    
    private Object jobWork() throws UwsException, InterruptedException {
        Log.debug("Into PullFromVospaceThread.jobWork()");
        
        UwsJobResultMeta result = null;
        VOSpaceURI targetURI = null;
        
        try {
            targetURI = this.transfer.getTarget();
            NodeTO node = NodeUtils.getNode(targetURI, false);
            if (node == null)
                throw new NodeNotFoundException("NodeNotFound for URI [ " + targetURI + " ].");
            // check authorization            
            boolean authorized = AccessUtils.isAuthorized(this.currentUser, "read", targetURI);
            if (!authorized) {
                throw new PermissionDeniedException("PermissionDenied for URI [" 
                        + targetURI + "].");
            }
        
            String userName = VospaceURIUtils.getUserAccount(targetURI.toString());
            // .../data/<user-name>/job-id
            String endpoint =   VOSConstants.SERVER_URL + "servlet" + VOSConstants.BAR 
                                + "data" + VOSConstants.BAR + userName + VOSConstants.BAR 
                                + job.getJobId();
            
            VoProtocolTO protocol = this.transfer.getProtocol();
            protocol.setEndPoint(new VOSpaceURI(endpoint));
            
            String dataFolder = VOSConstants.FILESYSTEM_PATH + VOSConstants.BAR 
                                + userName + VOSConstants.BAR + VOSConstants.DATA_FOLDER;
            transferService.createTransferFile(transfer, job.getJobId(), dataFolder);
                        
            String resultRef = createResultRef(LIST_NAME, RESULT_NAME);
            result = writeResult(resultRef, job);                                
            job.addResult(result);
            
            
        } catch (Exception e) {            
            Log.debug("UwsException executing PullFromVOSpace nodes for Target '" + targetURI.getName() + "'.");
            UwsJobErrorSummaryMeta errorSummary = writeError(e.getMessage());
            job.setErrorSummary(errorSummary);
            throw new UwsException(e.getMessage(),e);
        }
        
        if (job.isPhaseAborted())
            throw new InterruptedException();
        
        Log.debug("End of PullFromVospaceThread.jobWork()");
        
        return null;
    }

    /*
     * Create the result link to the Result.
     */
    private String createResultRef (String jobList, String resultName) {
        String resultRef =  VOSConstants.SERVER_URL + "servlet" + File.separator 
                            + "transfers" + File.separator + jobList + File.separator 
                            + job.getJobId() + File.separator + "results" 
                            + File.separator + resultName;
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
