package esavo.vospace.dl.querymanager.request;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import esavo.vospace.dl.querymanager.util.ConfigProperties;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;

/**
 * This class retrieves spring bean that holds RMI context for the client. Once the management
 * service is retrieved, client can call RMI methods from the server.
 * @author ileon
 */
public class ServiceClientHelper {

    /**
     * Application context.
     */
    private static ApplicationContext appContext;

    /**
     * Ssa query manager.
     */
    private static IQueryManagerVospace queryManagerRMI;

    /**
     * Client config file.
     */
    //private static final String CLIENT_CONFIG_FILE = "esavo/vospace/dl/querymanager/rmi/rmi-client-config.xml";
    
    private static final String CLIENT_CONFIG_FILE = ConfigProperties
            .getProperty("service.client.file");

    /**
     * Retrieve QueryManagerService. For that purpose, it uses Spring Application Context.
     * @return Query Manager Service
     * @throws ServiceException If any error is found.
     */
    /**
     * Retrieve QueryManagerService. For that purpose, it uses Spring Application Context.
     * @return Query Manager Service
     */
    public static IQueryManagerVospace getQueryManagerService() {

        if (appContext == null) {
            appContext = new ClassPathXmlApplicationContext(new String[] { CLIENT_CONFIG_FILE });
        }
        if (queryManagerRMI == null) {
            queryManagerRMI = (IQueryManagerVospace) appContext.getBean("queryManagerService");
        }
        return queryManagerRMI;

    }

}
