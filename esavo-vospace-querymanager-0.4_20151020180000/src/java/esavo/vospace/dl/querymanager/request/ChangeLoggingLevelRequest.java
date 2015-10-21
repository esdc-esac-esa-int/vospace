package esavo.vospace.dl.querymanager.request;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** Entry point for external change logging request. */
public final class ChangeLoggingLevelRequest {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(ChangeLoggingLevelRequest.class);

    /**
     * Main entry point to Query Manager.
     * @param args Input arguments
     */
    public static void main(final String[] args) {
        log.info("Starting ChangeLoggingLevelRequest Request...");
        if ((args == null) || (args.length < 1)) {
            log.error("No arguments were provided. Exiting request.");
        } else {
            String loggingLevel = args[0];
            new ChangeLoggingLevelRequest(loggingLevel);
            log.info("ChangeLoggingLevelRequest Request finished.");
        }
    }

    /**
     * Change request.
     * @param loggingLevel New logging level.
     */
    private ChangeLoggingLevelRequest(final String loggingLevel) {
        log.info("Into ChangeLoggingLevelRequest(" + loggingLevel + ")");
        if (loggingLevel == null) {
            log.error("ChangeLoggingLevelRequest does not accept null input values.");
        } else {
            try {
                ServiceClientHelper.getQueryManagerService().doChangeLoggingLevelCommand(
                        loggingLevel);
            } catch (Exception e) {
                log.error("Exception while trying to change the Loggin level to [" + loggingLevel
                        + "]", e);
            }
        }
    }
}
