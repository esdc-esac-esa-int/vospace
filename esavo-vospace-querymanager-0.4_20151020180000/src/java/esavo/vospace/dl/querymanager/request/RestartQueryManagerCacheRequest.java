package esavo.vospace.dl.querymanager.request;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Stand-alone java class which will contact the main QueryManager application and call its
 * "restartQueryManagerCache" method.
 * @author ileon
 */
public final class RestartQueryManagerCacheRequest {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(RestartQueryManagerCacheRequest.class);

    /**
     * Main entry point to Request.
     * @param args Input arguments
     */
    public static void main(final String[] args) {
        log.info("Starting RestartQueryManagerCacheRequest Request...");
        new RestartQueryManagerCacheRequest();
        log.info("RestartQueryManagerCacheRequest Request finished.");
    }

    /**
     * Constructor.
     */
    private RestartQueryManagerCacheRequest() {
        log.debug("Into RestartQueryManagerCacheRequest()");
        try {
            ServiceClientHelper.getQueryManagerService().doRestartQueryManagerCache();
        } catch (Exception e) {
            log.error("Exception while restarting QueryManager cache", e);
        }
    }
}
