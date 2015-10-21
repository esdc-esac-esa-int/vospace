package esavo.vospace.service.uws;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import esavo.uws.UwsException;
import esavo.uws.actions.UwsJobDefaultHttpParametersHandler;
import esavo.uws.actions.UwsJobParametersItems;
import esavo.uws.actions.UwsUploadResource;
import esavo.uws.jobs.parameters.UwsJobParameters;
import esavo.vospace.common.model.transferobjects.TransferTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.utils.access.UserUtils;
import esavo.vospace.xml.XMLReader;

public class VOSpaceParametersHandler extends
        UwsJobDefaultHttpParametersHandler {
    
    @Override
    public UwsJobParametersItems parse(HttpServletRequest request, File uploadDir,
            int maxFileSize) throws UwsException {
        UwsJobParametersItems items = super.parse(request, uploadDir, maxFileSize);
        
        UwsUploadResource[] upload = items.getUploadResourceLoaders();
        
        UwsJobParameters jobParameters = items.getJobParameters();
        if(jobParameters == null) {
            jobParameters = new UwsJobParameters();
            items.setJobParameters(jobParameters);
        }
        
        if(upload != null && upload.length > 0) {
            TransferTO transfer = null;
            try {
                InputStream is = upload[0].openStream();            
                try {
                    transfer = XMLReader.readTransferTO(is);
                } catch (VOSpaceException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
                        
            if (transfer != null) {
                VOSpaceURI targetUri = transfer.getTarget();
                String dir = transfer.getDirection();
                // TODO More than one protocol can be retrieved
                // All protocols are ignored with the exception of HTTP GET
                VoProtocolTO protocol = transfer.getProtocol();
                VoViewTO view = transfer.getView();
                // TODO Update to User owner in each case
                UserTO userTO = UserUtils.getCurrentContextUser();
    
                jobParameters.setParameter(GeneralConstants.VOS_TARGET, targetUri);
                jobParameters.setParameter(GeneralConstants.VOS_DIRECTION, dir);
                if (protocol != null)
                    jobParameters.setParameter(GeneralConstants.VOS_PROTOCOL, protocol.getURI());
                jobParameters.setParameter(GeneralConstants.VOS_KEEPBYTES, transfer.isKeepBytes());
                if (view != null)
                    jobParameters.setParameter(GeneralConstants.VOS_VIEW, view.getURI());
                jobParameters.setParameter("user", userTO.getName());
    
            } else
                throw new UwsException("Error: POST request with no parameters.");
        } else {
            String param_target = jobParameters.getStringParameter(GeneralConstants.VOS_TARGET);
            String param_dir = jobParameters.getStringParameter(GeneralConstants.VOS_DIRECTION);
            String param_proto = jobParameters.getStringParameter(GeneralConstants.VOS_PROTOCOL);
            String param_keepbytes = jobParameters.getStringParameter(GeneralConstants.VOS_KEEPBYTES);
            String param_view = jobParameters.getStringParameter(GeneralConstants.VOS_VIEW);
            
            if (param_target != null)
                jobParameters.setParameter(GeneralConstants.VOS_TARGET, new VOSpaceURI(param_target));
            if (param_dir != null)
                jobParameters.setParameter(GeneralConstants.VOS_DIRECTION, param_dir);
            if (param_proto != null)
                jobParameters.setParameter(GeneralConstants.VOS_PROTOCOL, new VOSpaceURI(param_proto));
            if (param_keepbytes != null)
                jobParameters.setParameter(GeneralConstants.VOS_KEEPBYTES, Boolean.parseBoolean(param_keepbytes));
            if (param_view != null)
                jobParameters.setParameter(GeneralConstants.VOS_VIEW, new VOSpaceURI(param_view));
            
        }
        
        return items;
    }

}
