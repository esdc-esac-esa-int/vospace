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
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;

/**
 * Administration command to update the Quota limit for a given user.
 * By default this command allows to increase the quota size, but in case of 
 * a decreasing size the operations is permitted only in case of the current 
 * occupation is lower than the new limit.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2015
 */
public class UpdateUserQLimitCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UpdateUserQLimitCommand.class);
    
    /** Vospace User DAO. */
    private IUsersDao userDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    private String userName;
    
    private long qlimit;
    
    /** Result transfer object */
    private UserTO result;
    
    /**
     * Constructor to update quota size for a user.
     * @param userName
     * @param qlimit
     */
    public UpdateUserQLimitCommand (final String userName, final long qlimit) {
        log.debug("Into UpdateUserQLimitCommand()");
        this.userName = userName;
        this.qlimit = qlimit;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        
        if (this.userName == null) {
            throw new IllegalArgumentException("UpdateUserQLimitCommand.execute() -> "
                    + "Input User name cannot be null.");
        }
        
        Users currentUser = this.userDao.queryUserByName(this.userName);
        if (currentUser == null) {
            throw new UnsupportedOperationException("User: [" + currentUser
                    + "] does not exist.");
        }
        
        boolean success = false; 
        //check if the current qlimit is bigger than the new value -> decreasing size
        //then, check if the current quota occupation allows the decrement
        if (currentUser.getQLimit() > this.qlimit) { //if decreasing
            if (currentUser.getQuota() < this.qlimit) {
                success = updateQLimit(currentUser); //Update QLimit size
            }
        } else { //if increasing
            success = updateQLimit(currentUser); //Update QLimit size
        }
        
        //Retrieve the user updated.
        if (success) {
            Users userUpdated = this.userDao.queryUserByName(this.userName);
            this.result = VospaceTransferObjectMapper.
                    populateUserTO(userUpdated);
        } else {
            this.result = null;
        }

    }
    
    /*
     * Update QLimit for a given user.
     */
    private boolean updateQLimit (Users currentUser) {
        currentUser.setQLimit(this.qlimit);
        this.userDao.update(currentUser);
        return true;
    }

    @Override
    public Object getResult() {
        return this.result;
    }

}
