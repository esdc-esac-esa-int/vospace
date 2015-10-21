package esavo.vospace.service.cmd.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.users.ldap.UsersDirectory;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.access.UserUtils;
import esavo.vospace.service.utils.nodes.NodeUtils;

/**
 * The UserAcountManager creates a user account from scratch in VOSpace.
 * Create the user if does not exists.
 * Create the its root folder in VOSpace DB and Filesystem.
 * 
 * Example:
 * <pre><tt>
 * UserManager userManager = UserManager.getInstance();
 * UserTO user = userManager.getOrCreateUser(userName);
 * </tt></pre>
 *
 * @author Sara Nieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public class UserManager {
    
    private static UserManager _instance;
    
    private Map<String, UserTO> usersMap;
    
    private UserManager(){
        usersMap = new HashMap<String, UserTO>();
    }
    
    public static synchronized UserManager getInstance(){
        if(_instance == null){
            _instance = new UserManager();
        }
        return _instance;
    }
    
    public synchronized UserTO getOrCreateUser(String username){
        if(usersMap.containsKey(username)){
            return usersMap.get(username);
        }
        
        VospaceServiceImpl vospaceService = new VospaceServiceImpl();
                    
        String uid = username;                
        boolean loggedUser = false;
        UserTO vosUser = null;
        try {
        
            vosUser = UserUtils.getUser(uid);
            if (vosUser != null) {
                UserControlTO control = UserUtils.getUserControl(uid);
                if (control != null) 
                    loggedUser = true;
                else 
                    vospaceService.doInsertControlUser(vosUser);
            } else {
                vosUser = createUserTO(uid);
                vospaceService.doInsertUser(vosUser);
                vospaceService.doInsertControlUser(vosUser);
            }
            
            //Create root container node for the new user
            if (!loggedUser) {            
                ContainerNodeTO node = new ContainerNodeTO();
                VOSpaceURI userRepositoryURI = new VOSpaceURI(VOSConstants.VOS_PREFIX 
                        + File.separator + uid);
                node.setUri(userRepositoryURI); //Root
                node.setUserTO(vosUser);
                // properties
                NodePropertyTO lengthProperty = NodeUtils.createLengthProperty(4096, false);
                NodePropertyTO bdateProperty = NodeUtils.createBDateProperty(new Date(), false);
                NodePropertyTO mdateProperty = NodeUtils.createMDateProperty(new Date(), false);        
                List<NodePropertyTO> properties = new ArrayList<NodePropertyTO>();
                properties.add(lengthProperty);
                properties.add(bdateProperty);
                properties.add(mdateProperty);
                node.setNodeProperties(properties);
                
                try {
                    vospaceService.doInsertContainerNode(node);
                } catch (Exception e) {
                    e.printStackTrace();
                    vospaceService.doRemoveUser(vosUser); //rollback
                }
            }
            
        } catch (VOSpaceException e) {
            e.printStackTrace();
        }
        
        usersMap.put(username, vosUser);
        
        return vosUser;
    }
    
    private static UserTO createUserTO (final String uid) {
        
        if (uid == null)
            throw new UsernameNotFoundException("Empty User Name");
        
        UsersDirectory ldap = new UsersDirectory();     
        UserTO user = ldap.searchLdapUser(uid);
        user.setCreationDate(new Date());
        user.setQuota((long) 0);
        user.setQLimit((long) GeneralConstants.USER_QUOTA_LIMIT); //Quota limit by dafeult
        
        // Update Quota
        VospaceServiceImpl vospaceService = new VospaceServiceImpl();
        UserTO userSystem = null;
        try {
            userSystem = vospaceService.doQueryUser(uid);
        } catch (Exception e) { e.printStackTrace(); }        
        if (userSystem != null) {
            user.setQuota(userSystem.getQuota());
            user.setQLimit(userSystem.getQLimit());
            user.setCreationDate(userSystem.getCreationDate());
        }            
        
        return user;
    }
}
