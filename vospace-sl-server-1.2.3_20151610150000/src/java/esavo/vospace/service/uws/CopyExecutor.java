package esavo.vospace.service.uws;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.uws.UwsException;
import esavo.uws.jobs.UwsJob;
import esavo.uws.jobs.UwsJobErrorSummaryMeta;
import esavo.uws.jobs.UwsJobResultMeta;
import esavo.vospace.common.model.transferobjects.TransferTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.control.DataMngLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.TransferService;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.utils.ControlUtils;
import esavo.vospace.service.utils.access.UserUtils;

/**
 * Executor that copies a source node to a target destination.
 * 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2015
 *
 */
public class CopyExecutor extends VOSpaceExecutor {

    /** Logging variable. */
    private static Log Log = LogFactory.getLog(CopyExecutor.class);
        
    /* Transfer Service */
    private TransferService transferService;
    
    /* Transfer Object */
    private TransferTO transfer;
    
    private UserTO currentUser;
    private UserControlTO userControl;
    
    private UwsJob job;
    
    @Override
    public Object execute(UwsJob j) throws InterruptedException, UwsException {        
        Log.debug("Into CopyExecutor.execute()");
        
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
        
        Log.debug("End of CopyExecutor.execute()");
        
        return result;
    }
    
    private Object jobWork () throws UwsException, InterruptedException {
        Log.debug("Into CopyExecutor.jobWork()");
        
        //Cannot create a new transferService due to current session user is null
        // It's better to create it at TransferThread creation
        
        UwsJobResultMeta result = null;
        
        VOSpaceURI target = transfer.getTarget();        
        try {
            
            transferService.copyNode(target.toString(), transfer.getDirection()/*, currentUser*/);
            
            String resultURL = transfer.getDirection() + File.separator + target.getName();
            VOSpaceURI vosURI = new VOSpaceURI(resultURL);
                
            result = writeResult(GeneralConstants.VOS_RESULT_DESTINATION + ": "+ vosURI, job);                                
            job.addResult(result);
            
            logOperation(new Date(), 0);
                        
        } catch (VOSpaceException v) {
            Log.debug("VOSpaceException executing Copy nodes for Target '" + target.getName() + "'.");
            v.printStackTrace();
            
        } catch (UwsException e) {
            Log.debug("UwsException executing Copy nodes for Target '" + target.getName() + "'.");
            UwsJobErrorSummaryMeta errorSummary = writeError(e.getMessage());
            job.setErrorSummary(errorSummary);
            throw new UwsException(e.getMessage(),e);
        }
        
        if (job.isPhaseAborted()) //if is interrupted?
            throw new InterruptedException();
        
        Log.debug("End of CopyExecutor.jobWork()");
        
        return result;
    }
    
    /*
     * Insert operation in DataMng Log.
     */
    private void logOperation (Date startTampstamp, long size) throws VOSpaceException {
        
        DataMngLogTO log = ControlUtils.createDataMngLog(this.userControl, 
                startTampstamp, new Date(), "COPY", transfer.getTarget().toString(), 
                transfer.getDirection(), new Long(size));
        VospaceServiceImpl vospaceService = new VospaceServiceImpl();
        vospaceService.doInsertDataMngLog(log);
    }
}
