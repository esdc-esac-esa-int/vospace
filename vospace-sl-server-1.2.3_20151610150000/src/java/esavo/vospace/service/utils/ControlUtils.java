package esavo.vospace.service.utils;

import java.util.Date;

import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.common.model.transferobjects.control.AccessLogTO;
import esavo.vospace.common.model.transferobjects.control.DataMngLogTO;
import esavo.vospace.common.model.transferobjects.control.MetadataRetrievalLogTO;
import esavo.vospace.common.model.transferobjects.control.ShareLogTO;
import esavo.vospace.common.model.transferobjects.control.TransferLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;

/**
 * Creates Control instances.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class ControlUtils {

    public static AccessLogTO createAccessLogTO (UserControlTO user, Date login, Date logout, 
            Long quota, String environment, String internetSite) {
        AccessLogTO log = new AccessLogTO();
        
        log.setUserControlTO(user);
        log.setLoginTimestamp(login);
        if (logout != null)
            log.setLogoutTimestamp(logout);
        log.setQuota(quota);
        log.setEnvironment(environment);
        log.setInternetSite(internetSite);

        return log;
    }
    
    public static TransferLogTO createTransferLogTO (UserControlTO user, Date start, Date stop, 
            String type, String protocol, String view, Long size) {
        TransferLogTO log = new TransferLogTO();
        
        log.setUserControlTO(user);
        log.setStartTimestamp(start);
        log.setStopTimestamp(stop);
        log.setOperType(type);
        log.setProtocolParam(protocol.toString());
        log.setViewParam(view.toString());
        log.setTotalSize(size);
        
        return log;
    }
    
    public static TransferLogTO createTransferLogTO (UserControlTO user, Date start, Date stop, 
            String type, VoProtocolTO protocol, VoViewTO view, Long size) {
        TransferLogTO log = new TransferLogTO();
        
        log.setUserControlTO(user);
        log.setStartTimestamp(start);
        log.setStopTimestamp(stop);
        log.setOperType(type);
        if (protocol != null)
            log.setProtocolParam(protocol.getURI().toString());
        if (view != null)
            log.setViewParam(view.getURI().toString());
        log.setTotalSize(size);
        
        return log;
    }
    
    public static DataMngLogTO createDataMngLog (UserControlTO user, Date start, Date stop,
            String type, String source, String target, Long size) {
        
        DataMngLogTO log = new DataMngLogTO();
        
        log.setUserTO(user);
        log.setStartTimestamp(start);
        log.setStopTimestamp(stop);
        log.setNodeSource(source);
        log.setNodeTarget(target);
        log.setOperType(type);
        log.setSize(size);
        
        return log;
    }
        
    public static MetadataRetrievalLogTO createMetadataLogTO (UserControlTO user, Date start,
            Date stop, String type) {
        
        MetadataRetrievalLogTO log = new MetadataRetrievalLogTO();
        
        log.setUserControlTO(user);
        log.setStartTimestamp(start);
        log.setStopTimestamp(stop);
        log.setType(type);
        
        return log;
    }
    
    public static ShareLogTO createShareLogTO (UserControlTO user, Date start,
            Date stop, String node) {
        
        ShareLogTO log = new ShareLogTO();
        
        log.setUserControlTO(user);
        log.setOperationTimestamp(start);
        if (stop != null)
            log.setEndTimestamp(stop);
        log.setNode(node);
        
        return log;        
    }
}
