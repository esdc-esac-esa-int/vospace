package esavo.vospace.service.security.restful;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;

/**
 * Holder Singleton.
 * 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2015
 *
 */
public class VOSUser {
    
    /**
     * Default constructor.
     */
    private VOSUser () {
        
    }
    
    public static UserTO getInstance () {
        return VOSUserHolder.INSTANCE;
    }
    
    private static class VOSUserHolder{
        
        private static final UserTO INSTANCE = getInstance();
        
        private static final UserTO getInstance () {
            
            UserContextServiceImpl ucsi = new UserContextServiceImpl();
            String uid = ucsi.getCurrentUser();
            
            if (uid == null) {
                return null;
            }
            
            VospaceServiceImpl vospaceService = new VospaceServiceImpl();
            UserTO user = null;
            try {
                user = vospaceService.doQueryUser(uid);
            } catch (Exception e) {
                e.printStackTrace();
            }        
            
            if (user == null) {
                return null;
            }
            
            return user;
            
            
            
            
            /*
             String uid = getCurrentUser();
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
             */
        }
        
    }
}