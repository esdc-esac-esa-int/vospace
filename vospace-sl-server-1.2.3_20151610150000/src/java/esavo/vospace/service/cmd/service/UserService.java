package esavo.vospace.service.cmd.service;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.json.JsonWriter;
import esavo.vospace.service.users.ldap.UsersDirectory;

/**
 * Describes the list of LDAP users (suggestions) that fulfils the criteria.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class UserService {

    /** VOSpace Public Services*/
    protected VospaceServiceImpl vospaceService;
    public List<UserTO> userList;
    
    public UserService (URL location) {
        vospaceService = new VospaceServiceImpl();
        userList = new ArrayList<UserTO>();
    }
    
    public UserService () {
        vospaceService = new VospaceServiceImpl();
        userList = new ArrayList<UserTO>();
    }
    
    public void executeGetRequest (final HttpServletRequest request, 
            final HttpServletResponse response) throws VOSpaceException, Exception {
                
        //Retrieve LDAP users        
        UsersDirectory directory = new UsersDirectory();
        String searchCriteria = request.getQueryString();
        String decoded = URLDecoder.decode(searchCriteria, "UTF-8");
        this.userList = directory.getSuggestion(decoded);
        
        JsonWriter.writeUserList(this.userList, response.getOutputStream());
    }
}
