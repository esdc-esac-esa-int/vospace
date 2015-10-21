package esavo.vospace.service.utils.access;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.service.cmd.service.ControlManager;
import esavo.vospace.service.cmd.service.UserManager;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.security.restful.UserContextServiceImpl;

public class UserUtils {
    
    private static VospaceServiceImpl vospaceService = new VospaceServiceImpl();
    
    private static UserContextServiceImpl context = new UserContextServiceImpl();
            
    /**
     * Retrieves the current context user
     * @return UserTO
     */
    public static UserTO getCurrentContextUser () {
        String name = context.getCurrentUser();
        UserTO user = UserManager.getInstance().getOrCreateUser(name);
        return user;
    }
    
    /**
     * Retrieves the current control user
     * @return
     */
    public static UserControlTO getCurrentContextControlUser () {
        String name = context.getCurrentUser();
        UserControlTO control = ControlManager.getInstance().getOrCreateControlUser(name);
        return control;
    }
    
    public static UserTO getUser (final String name) {
        UserTO user = null;
        try {
            user = vospaceService.doQueryUser(name);
        } catch (Exception e) {}
        return user;
    }
    
    public static UserControlTO getUserControl (final String name) {
        UserControlTO userControl = null;
        try {
            userControl = vospaceService.doQueryUserControl(name);
        } catch (Exception e) {}
        return userControl;
    }
}
