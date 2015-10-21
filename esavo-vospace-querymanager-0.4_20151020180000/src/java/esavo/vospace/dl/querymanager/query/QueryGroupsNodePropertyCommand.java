package esavo.vospace.dl.querymanager.query;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.dao.INodePropertyDao;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.model.GroupNodeProperty;
import esac.archive.vospace.persistence.model.Groups;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoProperty;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;
import esavo.vospace.dl.querymanager.util.Constants;

public class QueryGroupsNodePropertyCommand implements IQueryManagerCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryGroupsNodePropertyCommand.class);
    
    private INodePropertyDao nodePropertyDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getNodePropertyDAO();
    
    private IVoPropertyDao voPropertyDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoPropertyDAO();
    
    private INodeDao nodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getNodeDAO();
    
    /** Group name */
    private String userName;
    
    /** VoProperty name */
    private String propertyUri;
    
    /** List of nodes shared with User user */
    private List<NodeTO> nodeList;
    
    public QueryGroupsNodePropertyCommand(final String user, final String propertyUri) {
        log.debug("Into QueryGroupsNodePropertyCommand()");
        this.userName = user;
        this.propertyUri = propertyUri;
        this.nodeList = new ArrayList<NodeTO>();
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("Into QueryGroupsNodePropertyCommand().execute()");
        
        if (this.userName == null) {
            throw new IllegalArgumentException("QueryGroupsNodePropertyCommand.execute() -> "
                    + "Input user name cannot be null.");
        }
        
        if (this.propertyUri == null) {
            throw new IllegalArgumentException("QueryGroupsNodePropertyCommand.execute() -> "
                    + "Input Property Uri owner cannot be null.");
        }
        
        VoProperty voProperty = this.voPropertyDao.queryVoPropertyByUri(this.propertyUri);
        
        if (voProperty == null) {
            throw new IllegalArgumentException("QueryGroupsNodePropertyCommand.execute() -> "
                    + "The VoProperty with Uri [" + this.propertyUri + "] does not exists.");
        }
        
        List<NodeProperty> properties = this.nodePropertyDao.queryNodePropertyByVoProperty(voProperty);
        if (properties == null) {
            return;
        }
        
        for (NodeProperty property : properties) {
            if (property instanceof GroupNodeProperty) {
                Set<Groups> groupList = ((GroupNodeProperty) property).getShareGroups();
                if (isMemberOfGroup(groupList)) {
                    Node node = property.getNode();
                    String nodeUri = Constants.VOS_PREFIX + File.separator + 
                            this.nodeDao.queryNodeURIById(node.getNodeOid());                
                    NodeTO nodeTO = VospaceTransferObjectMapper.populateNodeTO(
                            node, new VOSpaceURI(nodeUri), false);                
                    this.nodeList.add(nodeTO);
                }
            }
        }
    }
    
    /*
     * Return true if user is in the list.
     */
    private boolean isMemberOfGroup(Set<Groups> groupList) {
        boolean contains = false;        
        for (Groups group : groupList) {
            
            Set<Users> members = group.getMembers();
            for (Users user : members) {
                String name = user.getName();
                if (name.compareToIgnoreCase(this.userName) == 0) {
                    contains = true;
                    break;
                }
            }
        }        
        return contains;
    }

    @Override
    public Object getResult() {
        return this.nodeList;
    }

}
