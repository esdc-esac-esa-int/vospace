package esavo.vospace.dl.querymanager.query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.model.Users;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;

/**
 * Query the user by its name.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class QueryUserCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryUserCommand.class);
    
    /** Vospace User DAO. */
    private IUsersDao userDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /** Transfer object to be ingested. */
    private UserTO userTO;
    
    /** Ingested persistent object. */
    private Users user;
    
    /** User name to retrieve the user. */
    private String userName;
    
    /**
     * Constructor.
     * @param userName
     */
    public QueryUserCommand(final String userName) {
        log.debug("Into QueryUserCommand()");
        this.userName = userName;
    }
    
    @Override
    public void execute() {
        log.debug("Into QueryUserCommand().execute()");
        
        if (this.userName == null) {
            throw new IllegalArgumentException("QueryUserCommand.execute() -> "
                    + "Input User cannot be null.");
        }
        
        // Does this User already exists??
        this.user = this.userDao.queryUserByName(this.userName);
        if (this.user == null) {
            throw new UnsupportedOperationException("User: [" + this.userTO.getId()
                    + "] has already been ingested");
        }
        
        this.userTO = VospaceTransferObjectMapper.populateUserTO(this.user);
        
        log.debug("End of QueryUserCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.userTO;
    }

}
