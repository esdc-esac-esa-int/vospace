package esavo.vospace.service.uws;

import esavo.uws.UwsException;
import esavo.uws.UwsManager;
import esavo.uws.owner.UwsJobOwner;
import esavo.uws.owner.UwsJobsOwnersManager;
import esavo.uws.security.UwsSecurity;
import esavo.uws.utils.UwsUtils;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.service.utils.access.UserUtils;

public class VOSpaceSecurityManager implements UwsSecurity {

    private String appid;
    
    public VOSpaceSecurityManager(String appid) {
        this.appid = appid;
    }

    @Override
    public UwsJobOwner getUser() throws UwsException {
        
        UserTO user = UserUtils.getCurrentContextUser();
        String authUsername = user.getName();
        
        String ownerid = getOwnerId(authUsername);
        String pseudo = getPseudo(authUsername);
        
        UwsManager uwsManager = UwsManager.getInstance();
        UwsJobsOwnersManager jobsOwnersManager = uwsManager.getFactory().getJobsOwnersManager(); 
        UwsJobOwner owner = jobsOwnersManager.loadOrCreateOwner(ownerid);
        
        uwsManager.getFactory().getStorageManager();
        
        owner.setAuthUsername(authUsername);
        owner.setPseudo(pseudo);
        
        return owner;
    }

    /**
     * If authUserName == null   : ownerId = "anonymous";
     * If authUserName == ""     : ownerId = "anonymous";
     * If authUserName == [value]: ownerId = [value];
     * @param authUsername
     * @return
     */
    private String getOwnerId(String authUsername){
        if(authUsername != null && !"".equals(authUsername)){
            return authUsername;
        } else {
            return UwsUtils.ANONYMOUS_USER;
        }
    }
    
    /**
     * If authUserName == null   : pseudo = "anonymous"
     * If authUserName == ""     : pseudo = ""
     * If authUserName == [value]: pseudo = [value]
     * @param authUserName
     * @return
     */
    private String getPseudo(String authUserName){
        if(authUserName != null){
            return authUserName;
        } else {
            return UwsUtils.ANONYMOUS_USER;
        }
    }

    @Override
    public void setUser(UwsJobOwner arg0) {
        //Nothing to do, setUser is done by spring security
    }
    
    @Override
    public String toString(){
        return "VOSpace Security Manager for application '"+appid+"'";
    }


}
