package esavo.vospace.dl.querymanager.cmd.ingestion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.model.Users;
import esavo.vospace.common.model.transferobjects.UserTO;

/**
 * Command which will ingest a User object into the database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class IngestUserCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestUserCommand.class);
    
    /** Vospace User DAO. */
    private IUsersDao userDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /** Transfer object to be ingested. */
    private UserTO userTO;
    
    /** Ingested persistent object. */
    private Users user;
    
    /**
     * Constructor.
     * @param userTO
     */
    public IngestUserCommand(final UserTO userTO) {
        log.debug("Into IngestUserCommand()");
        this.userTO = userTO;
    }
    
    @Override
    public void execute() {
        log.debug("Into IngestUserCommand().execute()");
        
        if (this.userTO == null) {
            throw new IllegalArgumentException("IngestUserCommand.execute() -> "
                    + "Input User cannot be null.");
        }
        
        // Does this User already exists??
        this.user = this.userDao.queryUserByName(this.userTO.getName());
        if (this.user != null) {
            throw new UnsupportedOperationException("User: [" + this.user.getName()
                    + "] has already been ingested");
        } else if (this.user == null) {
            // It does not exist? Insert new data...
            this.user = this.userDao.getEmptyUsers();
        }
        
        this.user.setName(this.userTO.getName());
        this.user.setFullName(this.userTO.getFullName());
        this.user.setMail(this.userTO.getMail());
        this.user.setCreationDate(this.userTO.getCreationDate());
        this.user.setQuota(this.userTO.getQuota());
        this.user.setQLimit(this.userTO.getQLimit());
        
        this.userDao.insertOrUpdate(this.user);
        
        log.debug("End of IngestUserCommand.execute()");
        
    }

    @Override
    public Object getResult() {
        return this.user;
    }

}
