package esavo.vospace.service.rmi;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.utils.ConfigProperties;

public class RMIVospaceClient {

    static Logger logger = Logger.getLogger(RMIVospaceClient.class);
    
    /**
     * Application context.
     */
    private static ApplicationContext appContext;
    
    /**
     * Csa query manager.
     */
    private static IQueryManagerVospace queryManagerRMI;
    
    /**
     * Client config file.
     */
    private static final String CLIENT_CONFIG_FILE = ConfigProperties
          .getProperty("service.client.file");
    
    /**
     * Default constructor.
     */
    protected RMIVospaceClient() {
       // prevents calls from subclass
       throw new UnsupportedOperationException();
    }
    
    /**
     * Retrieve QueryManager Service. For that purpose, it uses Spring 
     * Application Context.
     * @return Query Manager Service
     */
    public static IQueryManagerVospace getQueryManagerService() {
       logger.debug("Into RMIVospaceClient.getQueryManagerService()");
       
       if (appContext == null) {
          appContext = new ClassPathXmlApplicationContext(
                new String[] {CLIENT_CONFIG_FILE});
       }
       
       if (queryManagerRMI == null) {
          queryManagerRMI = (IQueryManagerVospace) appContext.getBean("queryManagerService");
       }
       
       return queryManagerRMI;
    }
}
