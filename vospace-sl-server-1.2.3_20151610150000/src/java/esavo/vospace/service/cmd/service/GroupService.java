package esavo.vospace.service.cmd.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.utils.access.UserUtils;
import esavo.vospace.service.utils.nodes.NodeUtils;
import esavo.vospace.json.JsonToObject;
import esavo.vospace.json.JsonWriter;

public class GroupService {
    
    /** VOSpace Public Services*/
    protected VospaceServiceImpl vospaceService;
    
    public GroupService () {
        vospaceService = new VospaceServiceImpl();
    }
    
    public void executeGetRequest (final HttpServletRequest request, 
            final HttpServletResponse response) throws VOSpaceException, Exception {
        
        String value = request.getPathInfo(); // /group || /group/xxx
        if (value == null) {
            throw new ServletException();
        }
        
        List<GroupTO> groupTOList;
        UserTO manager = UserUtils.getCurrentContextUser();
        
        String[] chunk = value.split("/");        
        if (chunk.length > 1) {
            // .../vospace-dev/servlet/group/namexxx
            groupTOList = vospaceService.doQueryGroups(manager.getName(), chunk[1]);
        } else {
            // .../vospace-dev/servlet/group
            groupTOList = vospaceService.doQueryGroups(manager.getName(), null);
        }
        
        // write response
        JsonWriter.writeGroupList(groupTOList, response.getOutputStream());
    }
    
    public void executePutRequest (final HttpServletRequest request, 
            final HttpServletResponse response) throws VOSpaceException, Exception {
        
        InputStream is = request.getInputStream();
        if (is == null) {
            throw new ServletException("Failed, empty insertion request.");
        }
                
        BufferedReader br = new BufferedReader(
                new InputStreamReader(is));

        String line = br.readLine();
        if (line != null) {
            
            GroupTO group = JsonToObject.parseJsonToGroup(line, 
                    UserUtils.getCurrentContextUser());
            group.setCreationDate(new Date());
            // check members and insert the users still not registered
            List<UserTO> validatedMembers = NodeUtils.checkList(group.getMembers());
            group.getMembers().clear();
            group.getMembers().addAll(validatedMembers);
            vospaceService.doInsertGroup(group);
        }
    }
    
    public void executeDeleteRequest (final HttpServletRequest request, 
            final HttpServletResponse response) throws VOSpaceException, Exception {
            InputStream is = request.getInputStream();
            if (is == null) {
                throw new ServletException("Failed, empty removal request.");
            }
            
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is));
    
            String line = br.readLine();
            if (line != null) {
    
                GroupTO group = JsonToObject.parseJsonToGroup(line, 
                        UserUtils.getCurrentContextUser());
                vospaceService.doRemoveGroup(group);
            }
    }
}
