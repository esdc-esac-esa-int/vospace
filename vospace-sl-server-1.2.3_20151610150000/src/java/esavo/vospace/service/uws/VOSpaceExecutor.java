package esavo.vospace.service.uws;

import java.io.IOException;

import esavo.uws.UwsException;
import esavo.uws.UwsManager;
import esavo.uws.executor.UwsExecutor;
import esavo.uws.jobs.UwsJob;
import esavo.uws.jobs.UwsJobErrorSummaryMeta;
import esavo.uws.jobs.UwsJobResultMeta;
import esavo.uws.output.UwsOutputStream;
import esavo.uws.output.UwsOutputStreamListener;
import esavo.uws.storage.QuotaException;
import esavo.uws.storage.UwsStorage;
import esavo.uws.utils.UwsErrorType;

public class VOSpaceExecutor implements UwsExecutor {

    
    /**
     * Write the job result.
     * @param resultData
     * @param job
     * @return
     * @throws UwsException
     */
    public UwsJobResultMeta writeResult (final String resultData, final UwsJob job) throws UwsException {
        
        UwsManager manager = UwsManager.getInstance();
        UwsStorage storage = manager.getFactory().getStorageManager();
        UwsJobResultMeta result = new UwsJobResultMeta(UwsJobResultMeta.DEFAULT_IDENTIFIER);
        result.setMimeType("text/plain");
        //no sync job: dump result to storage
        
        UwsOutputStream output = null;
            
        try{
            output = new UwsOutputStream(job, storage.getJobResultsDataOutputStream(job, result.getId()));
            output.addListener(new UwsOutputStreamListener() {
                @Override
                public void notifyWrite(long bytes) throws QuotaException,UwsException {
                    if(job.isPhaseAborted()){
                        return;
                    }
                    // In case we use Uws quota
                    //UwsQuota quota = UwsQuotaSingleton.getInstance().createOrLoadQuota(job.getOwner());
                    //quota.addFileSize(bytes);
                }
            });
            output.write(resultData.getBytes());
        } catch (IOException ioe) {
            throw new UwsException("Cannot save data results for job '" + job.getJobId() + "'", ioe);
        } finally {
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    // ignore
                    e.printStackTrace();
                }
            }
        }
        long size = storage.getJobResultDataSize(job, result.getId());
        result.setSize(size);
        return result;
    }
    
    public UwsJobErrorSummaryMeta writeError (String errorMessage) {
        
        UwsJobErrorSummaryMeta error = new UwsJobErrorSummaryMeta(errorMessage, UwsErrorType.FATAL);
                
        return error;
    }

    @Override
    public void cancel(UwsJob arg0) throws UwsException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Object execute(UwsJob arg0) throws InterruptedException,
            UwsException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
