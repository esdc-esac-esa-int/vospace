package esavo.vospace.service.action;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.GroupService;
import esavo.vospace.service.utils.exceptions.VOSpaceExceptionHandler;

/**
 * Manages the groups of users VOSpace: insert, delete and retrieve information.
 *
 * @author Sara Nieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public class GroupServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    /** Logging variable. */
    private static Log Log = LogFactory.getLog(GroupServlet.class);

    @Override
    public void init(ServletConfig config)
            throws ServletException {
    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException{
        Log.debug("Into GroupServlet.doGet()");
        
        GroupService service = new GroupService();
        try {
            service.executeGetRequest(request, response);
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, response);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, response);
        }
        
        Log.debug("Enf of GroupServlet.doGet()");
    }

    /**
     * Removes a group and its members.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException ,IOException {
        Log.debug("Into GroupServlet.doDelete()");
        
        GroupService service = new GroupService();
        try {
            service.executeDeleteRequest(req, resp);
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, resp);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, resp);
        }
        
        Log.debug("Enf of GroupServlet.doDelete()");
    }

    /**
     * Insert a new group and its members in VOSpace.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException ,IOException {
        Log.debug("Into GroupServlet.doPut()");
        
        GroupService service = new GroupService();
        try {
            service.executePutRequest(req, resp);
        } catch (VOSpaceException e) {
            VOSpaceExceptionHandler.handleVOSException(e, resp);
        } catch (Exception e) {
            VOSpaceExceptionHandler.handleGenericException(e, resp);
        }
        
        Log.debug("Enf of GroupServlet.doPut()");
    }
}
