package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IGroupsDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.model.Groups;
import esac.archive.vospace.persistence.model.Users;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.UserTO;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class IngestGroupCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestGroupCommand.class);
    
    /** Vospace Users DAO. */
    private IUsersDao userDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /** Vospace Groups DAO. */
    private IGroupsDao groupDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getGroupsDAO();
    
    /** Transfer object to be ingested. */
    private GroupTO groupTO;
    
    /** List of associated members for input Group */
    private List<UserTO> members;
    
    /** Ingested persistent object. */
    private Groups group;
    
    /** Overwrite information flag */
    private Boolean overwriteData;
    
    /**
     * Constructor
     * @param groupTO
     */
    public IngestGroupCommand(final GroupTO groupTO, final Boolean overwrite) {
        log.debug("Into IngestGroupCommand()");
        this.groupTO = groupTO;
        this.members = groupTO.getMembers();
        this.overwriteData = overwrite;
    }
    
    @Override
    public void execute() {
        log.debug("Into IngestGroupCommand().execute()");
        
        if (this.groupTO == null) {
            throw new IllegalArgumentException("IngestGroupCommand.execute() -> "
                    + "Input Group cannot be null.");
        }
        
        // Get the group manager
        Users manager = this.userDao.queryUserByName(this.groupTO.getManagerTO().getName());
        if (manager == null) {
            throw new IllegalArgumentException("IngestGroupCommand.execute() -> "
                    + "Input Group Manager cannot be null.");
        }
        
        // Does this Group already exists??
        String groupName = this.groupTO.getName();
        this.group = this.groupDao.queryGroupByOwnerAndName(manager, groupName);
        
        if ((this.group != null) && !this.overwriteData) {
            throw new UnsupportedOperationException("Group: [" + this.groupTO.getName()
                    + "] has already been ingested");
        } else if (this.group == null) {
            this.group = this.groupDao.getEmptyGroup();
        }
        
        this.group.setName(this.groupTO.getName());
        this.group.setDescription(this.groupTO.getDescription());
        this.group.setCreationDate(this.groupTO.getCreationDate());
        this.group.setManager(manager);
        
        // Attach members to Group (if any)
        Set<Users> users;
        if (this.overwriteData) {
            users = new HashSet<Users>();
        } else {
            users = this.group.getMembers();
        }
        
        if ((this.members != null) && (this.members.size() > 0)) {
            for (UserTO member : this.members) {
                Users persistentUser = getUserByName(member.getName());
                if (persistentUser == null) {
                    throw new IllegalArgumentException("IngestGroupCommand.execute() -> "
                            + "No User found for User Name [" + member.getName() + "].");
                }
                users.add(persistentUser);
            }
        }
        
        this.group.setMembers(users);
        this.groupDao.insertOrUpdate(this.group);
        
        log.debug("End of IngestGroupCommand().execute()");
    }
    
    private Users getUserByName (final String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("IngestGroupCommand.getUserByName() -> "
                    + "Input User Name cannot be null.");
        }
        
        Users persistentUser = this.userDao.queryUserByName(userName);
        return persistentUser;
    }

    @Override
    public Object getResult() {
        return this.group;
    }

}
