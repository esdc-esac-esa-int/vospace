package esavo.vospace.dl.querymanager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.AbstractQueryManager;
import esac.archive.absi.interfaces.dl.querymanager.mapper.ITransferObjectMapper;
import esac.archive.absi.interfaces.dl.querymanager.security.IAuthorizationService;
import esac.archive.absi.persistence.IDAOService;
import esac.archive.vospace.persistence.VospaceDaoServiceImpl;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;
import esavo.vospace.dl.querymanager.security.VospaceAuthorizationServiceImpl;
import esavo.vospace.dl.querymanager.util.ConfigProperties;

/**
 * Query Manager server class. It will initialize main Spring xml file. The ApplicationContext found
 * here will be referenced by many classes
 * @author ileon
 */
public final class QueryManager extends AbstractQueryManager {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryManager.class);

    /**
     * RMI config file.
     */
    /*private static final String RMI_SERVER_CONGIG_XML_FILE = "esavo/vospace/dl/querymanager/rmi/rmi-server-config.xml";*/
    
    /**
     * Service file.
     */
    private static final String RMI_SERVER_CONGIG_XML_FILE = ConfigProperties
          .getProperty("service.file");

    /**
     * Constructor, it will initialize the spring context.
     */
    private QueryManager() {
        super(RMI_SERVER_CONGIG_XML_FILE);
    }

    /**
     * Main entry point to Query Manager.
     * @param args Input arguments
     */
    public static void main(final String[] args) {
        log.info("Starting Query Manager Application...");
        try {
            new QueryManager();
            log.info("QueryManager started.");
        } catch (Exception e) {
            log.info("Problem starting QueryManager", e);
        }
    }

    @Override
    public IAuthorizationService initializeAuthorizationService() {
        return new VospaceAuthorizationServiceImpl();
    }

    @Override
    public IDAOService initializeDaoService() {
        return new VospaceDaoServiceImpl();
    }

    @Override
    public ITransferObjectMapper initializeTransferObjectMapper() {
        return new VospaceTransferObjectMapper();
    }

}
