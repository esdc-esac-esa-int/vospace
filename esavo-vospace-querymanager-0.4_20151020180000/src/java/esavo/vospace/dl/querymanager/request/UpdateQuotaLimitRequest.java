package esavo.vospace.dl.querymanager.request;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Command to be used from command line (scripting).
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2015
 *
 */
public final class UpdateQuotaLimitRequest {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UpdateQuotaLimitRequest.class);
    
    public static void main (String[] args) {
        log.info("Starting UpdateQuotaLimitRequest Request...");
        if ((args == null) || (args.length < 1)) {
            log.error("No arguments were provided. Exiting request.");
        } else {
            String userName = args[0];
            long qlimit = Long.parseLong(args[1]);
            new UpdateQuotaLimitRequest(userName, qlimit);
            log.info("UpdateQuotaLimitRequest Request finished.");
        }
    }
    
    private UpdateQuotaLimitRequest (final String userName, final long qlimit) {
        log.info("Into UpdateQuotaLimitRequest( '" + userName + "', '" + qlimit + "')");
        if ((userName == null || userName.isEmpty()) || qlimit == 0) {
            log.error("UpdateQuotaLimitRequest does not accept null input values.");
        } else {
            try {
                ServiceClientHelper.getQueryManagerService().doUpdateUserQLimit(userName, qlimit);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Exception while trying to change the qlimit to [ " + userName + " to " + qlimit
                        + " ]", e);
            }
        }
    }
}
