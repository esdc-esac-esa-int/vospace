package esavo.vospace.service.uws;

import esavo.uws.UwsException;
import esavo.uws.executor.UwsExecutor;
import esavo.uws.executor.UwsExecutorJobHandler;
import esavo.uws.jobs.UwsJob;
import esavo.vospace.constants.GeneralConstants;

public class VOSpaceFactoryExecutor implements UwsExecutor {

    @SuppressWarnings("unused")
    private boolean canceled;
    
    /* Transfer direction */
    private static final String PUSH_TO_VOSPACE = "pushtovospace";
    private static final String PUSH_FROM_VOSPACE = "pushfromvospace";
    private static final String PULL_FROM_VOSPACE = "pullfromvospace";
    private static final String PULL_TO_VOSPACE = "pulltovospace";
    
    public VOSpaceFactoryExecutor() {
        
    }
    
    /*
     * Factory Method.
     * Creates the proper executor according to the job parameters.
     */
    private VOSpaceExecutor createExecutor (UwsJob job) {
        
        String param = job.getParameters()
                .getStringParameter(GeneralConstants.VOS_DIRECTION);
        Boolean keepBytes = (Boolean) job.getParameters()
                .getParameter(GeneralConstants.VOS_KEEPBYTES);
        
        if (param != null) param = param.toLowerCase();
        
        VOSpaceExecutor executor = null;
        
        if (param.compareToIgnoreCase(PUSH_TO_VOSPACE) == 0) {            
            executor = new PushToVospaceExecutor();            
        } else if (param.compareToIgnoreCase(PULL_FROM_VOSPACE) == 0) {            
            executor = new PullFromVospaceExecutor();            
        } else if (param.compareToIgnoreCase(PULL_TO_VOSPACE) == 0) {            
            // Operation not supported            
        } else if (param.compareToIgnoreCase(PUSH_FROM_VOSPACE) == 0) {          
            // Operation not supported                     
        } else if (keepBytes) {            
            executor = new CopyExecutor();            
        } else if (!keepBytes) {            
            executor = new MoveExecutor();            
        } else {
            return null;
        }
        
        return executor;
    }
    
    @Override
    public void cancel(UwsJob job) throws UwsException {
        canceled = true;
        //if necessary:
        //Object executor = job.getExecutorJobHandler();
        //...
    }

    @Override
    public Object execute(UwsJob job) throws InterruptedException,
            UwsException {        
        
        VOSpaceExecutor executor = createExecutor(job);
        UwsExecutorJobHandler ejh = new UwsExecutorJobHandler(executor);
        job.setExecutorJobHandler(ejh);
        executor.execute(job);
        
        return null;
    }

}
