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
 * Update the User quota of a given user.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class UpdateUserQuotaCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UpdateUserQuotaCommand.class);
    
    /** Vospace User DAO. */
    private IUsersDao userDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /** Transfer object to be ingested. */
    private String userName;
        
    /** Result transfer object */
    private UserTO result;
    
    private Boolean add;
    
    private Boolean auto;
    
    private long quota;
    
    private long size;
    
    /**
     * Constructor to increase/decrease quota by size.
     * @param userTO
     */
    public UpdateUserQuotaCommand (final String userName, final long size, Boolean add) {
        log.debug("Into UpdateUserQuota() -> size = " + size + ", Add = " + add );
        this.userName = userName;
        this.size = size;
        this.add = add;
        this.auto = false;
    }
    
    /**
     * Constructor to update the quota..
     * @param userName
     * @param quota
     */
    public UpdateUserQuotaCommand (final String userName, final long quota) {
        log.debug("Into UpdateUserQuota() -> quota = " + quota);
        this.userName = userName;
        this.quota = quota;
        this.auto = true;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        
        
        if (this.userName == null) {
            throw new IllegalArgumentException("IngestUserCommand.execute() -> "
                    + "Input User name cannot be null.");
        }        
        
        Users currentUser = this.userDao.queryUserByName(this.userName);
        if (currentUser == null) {
            throw new UnsupportedOperationException("User: [" + currentUser
                    + "] does not exist.");
        }
        
        if (auto) {
            //updateQuota (quota, userName);
            log.debug("1 - Into UpdateUserQuota() -> Quota=" + this.quota);
            currentUser.setQuota(quota);
            this.userDao.update(currentUser);
            
        } else {
            //updateQuotaBySize (size, userName, add);
            Long quota = currentUser.getQuota();            
            log.debug("2 - Into UpdateUserQuota() -> Size=" + size + ", Quota=" + quota);
            
            long total = 0;
            if (add)
                total = quota + size;
            else
                total = quota - size;
            
            currentUser.setQuota(total);        
            this.userDao.update(currentUser);            
        }
        
        Users userUpdated = this.userDao.queryUserByName(this.userName);
        this.result = VospaceTransferObjectMapper.
                populateUserTO(userUpdated);
    }
    
    /*private Users updateQuota (long quota, String name) {
        // This user should already exists        
        Users currentUser = this.userDao.queryUserByName(name);
        if (currentUser == null) {
            throw new UnsupportedOperationException("User: [" + currentUser
                    + "] does not exist.");
        }
        log.debug("1 - Into UpdateUserQuota() -> Quota=" + quota);
        
        currentUser.setQuota(quota);
        userDao.update(currentUser);
        
        return currentUser;
    }
    
    private Users updateQuotaBySize (long size, String name, boolean add) {
        // This user should already exists
        Users currentUser = this.userDao.queryUserByName(name);
        if (currentUser == null) {
            throw new UnsupportedOperationException("User: [" + currentUser
                    + "] does not exist.");
        }
        Long quota = currentUser.getQuota();
        log.debug("2 - Into UpdateUserQuota() -> Size=" + size + ", Quota=" + quota);
        
        long total = 0;
        if (add) {
            total = quota + size; 
        } else {
            total = quota - size;
        }
        
        currentUser.setQuota(total);        
        userDao.update(currentUser);
        
        return currentUser;
    }*/

    @Override
    public Object getResult() {
        return this.result;
    }

}
