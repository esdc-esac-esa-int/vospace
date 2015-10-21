package esavo.vospace.dl.querymanager.query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoProperty;
import esavo.vospace.constants.GeneralConstants;

public class QueryUserQuotaCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryUserQuotaCommand.class);
    
    /** Vospace User DAO. */
    private IUsersDao userDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    /** Vospace Node DAO. */
    private INodeDao nodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getNodeDAO();
    /** Vospace VoProperty DAO. */
    private IVoPropertyDao propertyDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoPropertyDAO();
    
    /** User name */
    private String userName;
    
    /** Value to retrieve in bytes */
    private long quota;
    
    public QueryUserQuotaCommand (String user) {
        log.debug("Into QueryUserQuota()");
        this.userName = user;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        
        if (this.userName == null) {
            throw new IllegalArgumentException("QueryUserQuota.execute() -> "
                    + "Input User name cannot be null.");
        }
        
        Users user = this.userDao.queryUserByName(this.userName);
        if (user == null) {
            throw new UnsupportedOperationException("User: [" + this.userName
                    + "] does not exists");
        }
        
        VoProperty property = this.propertyDao.queryVoPropertyByUri(GeneralConstants.PROPERTY_LENGTH);
        if (property == null){
            throw new UnsupportedOperationException("Property: [" + GeneralConstants.PROPERTY_LENGTH
                    + "] does not exists");
        }
        
        this.quota = this.nodeDao.queryUserQuota(user, property);
    }

    @Override
    public Object getResult() {
        return this.quota;
    }

}
