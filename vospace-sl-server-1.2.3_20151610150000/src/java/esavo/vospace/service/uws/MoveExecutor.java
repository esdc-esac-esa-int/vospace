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
 * Executor that moves a source node to a target destination.
 * 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2015
 *
 */
public class MoveExecutor extends VOSpaceExecutor {

    /** Logging variable. */
    private static Log Log = LogFactory.getLog(MoveExecutor.class);
        
    /* Transfer Service */
    private TransferService transferService;
    
    /* Transfer Object */
    private TransferTO transfer;
    
    private UserTO currentUser;
    private UserControlTO userControl;
    
    private UwsJob job;
    
    @Override
    public Object execute(UwsJob j) throws InterruptedException, UwsException {
        Log.debug("Into MoveExecutor.execute()");
        
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
        
        Log.debug("End of MoveExecutor.execute()");
        
        return result;
    }
    
    private Object jobWork () throws UwsException, InterruptedException {
        
        Log.debug("Into MoveExecutor.jobWork()");
        
        Date startTampstamp = new Date();
        long size = 0;
        
        //Cannot create a new transferService due to current session user is null
        // It's better to create it at TransferThread creation
        
        UwsJobResultMeta result = null;
        VOSpaceURI targetUri = transfer.getTarget();
        
        try {
            
            String target = targetUri.toString();
            String direction = transfer.getDirection();
            transferService.moveNode(target, direction/*, currentUser*/);
                        
            try {
                String resultURL = transfer.getDirection() + File.separator + targetUri.getName();
                VOSpaceURI vosURI = new VOSpaceURI(resultURL);
                
                result = writeResult(GeneralConstants.VOS_RESULT_DESTINATION + ": "+ vosURI, job);                                
                job.addResult(result);
                
            } catch (Exception e) {
                e.printStackTrace();                
                throw new UwsException(e.getMessage());
            }
            
            //dataMng log            
            DataMngLogTO log = ControlUtils.createDataMngLog(this.userControl, 
                    startTampstamp, new Date(), "MOVE", transfer.getTarget().toString(), 
                    transfer.getDirection(), new Long(size));
            VospaceServiceImpl vospaceService = new VospaceServiceImpl();
            vospaceService.doInsertDataMngLog(log);
            
        } catch (Exception e) {            
            Log.debug("UwsException executing Move nodes for Target '" + targetUri.getName() + "'.");
            UwsJobErrorSummaryMeta errorSummary = writeError(e.getMessage());
            job.setErrorSummary(errorSummary);
            throw new UwsException(e.getMessage(),e);
        }
        
        if (job.isPhaseAborted())
            throw new InterruptedException();
                
        Log.debug("End of MoveExecutor.jobWork()");
        
        return null;
    }

}
