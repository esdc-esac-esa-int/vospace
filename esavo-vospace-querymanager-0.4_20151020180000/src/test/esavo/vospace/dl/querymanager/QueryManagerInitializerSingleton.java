package esavo.vospace.dl.querymanager;

import esac.archive.absi.interfaces.dl.querymanager.mapper.TransferObjectMapperProvider;
import esac.archive.absi.interfaces.dl.querymanager.security.SecurityProvider;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.VospaceDaoServiceImpl;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;
import esavo.vospace.dl.querymanager.security.VospaceAuthorizationServiceImpl;

/** Convenience class to initialize QueryManager only once. */
public class QueryManagerInitializerSingleton {

    /** Instance. */
    private static QueryManagerInitializerSingleton instance = null;

    /** Constructor. */
    protected QueryManagerInitializerSingleton() {
        // Initialize query manager services:
        SecurityProvider.setAuthorizationService(new VospaceAuthorizationServiceImpl());
        DAOServiceProvider.setDaoService(new VospaceDaoServiceImpl());
        TransferObjectMapperProvider.setTransferObjectMapper(new VospaceTransferObjectMapper());
    }

    /**
     * Call instance, create if it does not exist (but only once!).
     * @return QueryManagerInitializerSingleton
     */
    public static QueryManagerInitializerSingleton getInstance() {
        if (instance == null) {
            instance = new QueryManagerInitializerSingleton();
        }
        return instance;
    }
}
