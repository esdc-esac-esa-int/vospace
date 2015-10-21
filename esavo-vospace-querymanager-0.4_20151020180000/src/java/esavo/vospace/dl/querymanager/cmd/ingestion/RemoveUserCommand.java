package esavo.vospace.dl.querymanager.cmd.ingestion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.model.Users;
import esavo.vospace.common.model.transferobjects.UserTO;

/**
 * Remove a user from Vospace DB.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class RemoveUserCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(RemoveUserCommand.class);
    
    /** Vospace User DAO. */
    private IUsersDao userDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /** User to be removed from VOSpace DB */
    private UserTO user;
    
    /**
     * Constructor.
     * @param user
     */
    public RemoveUserCommand (final UserTO user) {
        this.user = user;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("RemoveUserCommand.execute()");
        
        if (this.user == null) {
            throw new IllegalArgumentException("RemoveUserCommand.execute() -> "
                    + "Input User cannot be null.");
        }
        
        // exist?
        Users persistentUser = this.userDao.queryUserByName(this.user.getName());
        if (persistentUser == null) {
            throw new UnsupportedOperationException("User with Name [" + this.user.getName()
                    + "] does not exist.");
        }
        
        // remove UserTO
        this.userDao.delete(persistentUser);
        
        log.debug("End of RemoveUserCommand.execute()");
    }

    @Override
    public Object getResult() {
        return null;
    }

}
