package esavo.vospace.service.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import esavo.uws.UwsException;
import esavo.uws.UwsManager;
import esavo.uws.actions.UwsActionsManager;
import esavo.uws.config.UwsConfiguration;
import esavo.uws.config.UwsConfigurationManager;
import esavo.uws.owner.UwsJobOwner;
import esavo.uws.owner.UwsJobsOwnersManager;
import esavo.uws.security.UwsSecurity;
import esavo.uws.utils.UwsUtils;
import esavo.vospace.common.model.transferobjects.TransferTO;
import esavo.vospace.constants.ClientConstants;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.uws.VOSpaceUwsFactory;
import esavo.vospace.xml.XMLReader;

public class TransferServlet extends HttpServlet {

    /* Transfer direction */
    private static final long serialVersionUID = 1L;
    
    /* Rest Transfer endpoint */
    public static final String REST_TRANSFER_ENDPOINT = VOSConstants.SERVER_URL 
                + ClientConstants.ENTRYPOINT_SERVLET + VOSConstants.BAR + "transfers";
    
    private String appid;
    
    private VOSpaceUwsFactory factory;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        ServletContext context = getServletContext();
        appid = UwsUtils.getAppIdFromContext(context, config);
        if(appid == null){
            throw new IllegalArgumentException("Application identifier must be defined."
                    + " Use configuration variable: "
                    + "'"+UwsConfiguration.CONFIG_APP_ID+"'");
        }
        
        UwsConfiguration configuration = UwsConfigurationManager.getConfiguration(appid);
        UwsUtils.updateConfiguration(configuration, context);
        UwsUtils.updateConfiguration(configuration, config);
        
        // Initialize
        File storageDir = new File(configuration.getProperty(UwsConfiguration.CONFIG_PROPERTY_STORAGE));
        factory = new VOSpaceUwsFactory(appid, storageDir, configuration);
        
    }
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UwsSecurity security = factory.getSecurityManager();
        UwsJobOwner user;
        try {
            user = security.getUser();
        } catch (UwsException e1) {
            throw new ServletException("Cannot obtain current user");
        }
        if(user == null){
            //user = UwsJobOwner.ANONYMOUS_OWNER;
            UwsJobsOwnersManager jobsOwnersManager = factory.getJobsOwnersManager(); 
            try {
                user = jobsOwnersManager.loadOrCreateOwner(UwsUtils.ANONYMOUS_USER);
            } catch (UwsException e) {
                throw new ServletException("Cannot obtain user: " + e.getMessage(), e);
            }
        }
        
        /**/
        /*System.out.println("------------------------------------------------------------Service");
        TransferTO transfer = null;
        try {
            InputStream is = request.getInputStream();
            
            try {
                transfer = XMLReader.readTransferTO(is);
                System.out.println("**********************************************************" + transfer.getTarget().getURI().toString());
            } catch (VOSpaceException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /**/
        
        UwsManager manager = UwsManager.getInstance();
        UwsActionsManager actionsManager = UwsActionsManager.getInstance();
        List<String> toIgnoreParameters = null;
        actionsManager.executeRequest(manager, user, request, response, toIgnoreParameters);
    }
    
    /*
     * GET request are allowed for transfer operations.
     */
    /*@SuppressWarnings("unused")
    private TransferTO getParametersFromGetRequest (HttpServletRequest req) {
        
        String target = req.getParameter("TARGET");
        String direction = req.getParameter("DIRECTION");
        String protocol = req.getParameter("PROTOCOL");
        
        TransferTO transfer = new TransferTO();
        transfer.setTarget(new VOSpaceURI(target));
        transfer.setDirection(direction);
        VoProtocolTO protocolTO = new VoProtocolTO();
        protocolTO.setURI(new VOSpaceURI(protocol));
        transfer.setProtocol(protocolTO);
        
        return transfer;
    }*/
    
    /*
     * POST request are mandatory for transfers
     */
    /*@SuppressWarnings("unused")
    private TransferTO getParametersFromPostRequest (HttpServletRequest req) {
        
        TransferTO transfer = null;
        try {
            InputStream is = req.getInputStream();
            
            try {
                transfer = XMLReader.readTransferTO(is);
            } catch (VOSpaceException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return transfer;
    }*/

}
