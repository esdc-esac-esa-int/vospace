package esavo.vospace.service.cmd.service;

import java.util.HashMap;
import java.util.Map;

import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.exceptions.VOSpaceException;

public class ControlManager {
    
    
    private static ControlManager _instance;
    
    private Map<String, UserControlTO> usersMap;
    
    private ControlManager(){
        usersMap = new HashMap<String, UserControlTO>();
    }
    
    public static synchronized ControlManager getInstance(){
        if(_instance == null){
            _instance = new ControlManager();
        }
        return _instance;
    }
    
    public synchronized UserControlTO getOrCreateControlUser(String username){
        
        if(usersMap.containsKey(username)){
            return usersMap.get(username);
        }        
        
        VospaceServiceImpl vospaceService = new VospaceServiceImpl();
        
        String uid = username;
        UserControlTO userControl = null;
        try {
            userControl = vospaceService.doQueryUserControl(uid);
        } catch (VOSpaceException e) {
            e.printStackTrace(); 
        }
        
        usersMap.put(username, userControl);
        
        return userControl;        
    }
}
