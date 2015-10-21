package esavo.vospace.service.uws;

import java.io.File;

import esavo.uws.UwsException;
import esavo.uws.UwsManager;
import esavo.uws.config.UwsConfiguration;
import esavo.uws.config.UwsConfigurationManager;
import esavo.uws.factory.UwsDefaultFactory;
import esavo.uws.factory.UwsFactory;
import esavo.uws.scheduler.UwsDefaultScheduler;

public class VOSpaceUwsFactory extends UwsDefaultFactory implements UwsFactory  {
    
    public VOSpaceUwsFactory(String appid, File storageDir,
            UwsConfiguration configuration) {
        super(appid, storageDir, configuration);
        // TODO Auto-generated constructor stub
                
        executor = new VOSpaceFactoryExecutor();
        securityManager = new VOSpaceSecurityManager(appid);
        scheduler = new UwsDefaultScheduler(appid, executor);
        
        try {
            uwsManager = UwsManager.getManager(this);
        } catch (UwsException e) {
            e.printStackTrace();
        }
        
        getCreator().addJobHttpParametersHandler(new VOSpaceParametersHandler());
    }
    
    public void clear(){
        UwsConfiguration config = UwsConfigurationManager.getConfiguration(getAppId());
        config.clear();
        reset();
    }    
    
    public void reset(){
        
    }   

}
