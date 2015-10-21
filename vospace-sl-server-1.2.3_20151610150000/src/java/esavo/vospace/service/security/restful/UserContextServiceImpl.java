package esavo.vospace.service.security.restful;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserContextServiceImpl implements UserContextService {
    
	/* (non-Javadoc)
	 * @see esac.archive.gacs.security.UserContextService#getCurrentUser()
	 */
	@Override
	public String getCurrentUser(){
		if(SecurityContextHolder.getContext().getAuthentication()==null 
			|| SecurityContextHolder.getContext().getAuthentication().getName()==null 
			|| SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) return null;
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	@Override
	public String getCurrentSessionId(){
		ServletRequestAttributes requestAttr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return requestAttr.getSessionId();
	}
	
	//@Override
	/*public UserTO getLoggedUser (){
	    log.debug("Into UserContextServiceImpl.getLoggedUser().");
	    
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
        
        log.debug("End of UserContextServiceImpl.getLoggedUser().");
	    return user;
	}*/
	
    //@Override
    /*public UserControlTO getLoggedUserControl () {
        log.debug("Into UserContextServiceImpl.getLoggedUserControl().");
        String uid = getCurrentUser();
        if (uid == null)
            throw new UsernameNotFoundException("Empty User Name");
        
        VospaceServiceImpl vospaceService = new VospaceServiceImpl();
        UserControlTO userControl = null;
        try {
            userControl = vospaceService.doQueryUserControl(uid);
        } catch (VOSpaceException e) { e.printStackTrace(); }
        
        log.debug("End of UserContextServiceImpl.getLoggedUserControl().");
        return userControl;
    }*/
}
