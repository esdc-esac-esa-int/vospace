package esavo.vospace.json;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.json.utils.JsonUtils;

public class JsonWriter {

    /**
     * Write the list of JSON objects into the Output Stream.
     * @param childs, list of nodes
     * @param out
     */
    public static void writeNodeList (List<NodeTO> childs, OutputStream out) {
        JsonUtils.writeJsonResponse(ObjectToJson.parseNodeListToJson(childs), out);
    }
    
    /**
     * Write the list of JSON objects and its roles into the Output Stream.
     * @param childs, list of nodes
     * @param out
     */
    public static void writeSharedNodeList (Map<NodeTO, String> childs, OutputStream out) {
        JsonUtils.writeJsonResponse(ObjectToJson.parseSharedNodeListToJson(childs), out);
    }
    
    /**
     * Write the JSON object into the Output Stream.
     * @param node
     * @param out
     */
    public static void writeNode (NodeTO node, OutputStream out) {
        JsonUtils.writeJsonResponse(ObjectToJson.parseNodeToJson(node), out);
    }
    
    /**
     * Write the JSON object into the Output Stream.
     * @param user
     * @param out
     */
    public static void writeUser (UserTO user, OutputStream out) {
        JsonUtils.writeJsonResponse(ObjectToJson.parseUserToJson(user, true), out);
    }
    
    /**
     * Write the list of JSON objects into the Output Stream.
     * @param userList, list of users
     * @param out
     */
    public static void writeUserList (List<UserTO> userList, OutputStream out) {
        JsonUtils.writeJsonResponse(ObjectToJson.parseUserListToJson(userList), out);
    }
    
    /**
     * Write the list of JSON objects into the Output Stream.
     * @param groupList, list of group
     * @param out
     */
    public static void writeGroupList (List<GroupTO> groupList, OutputStream out) {
        JsonUtils.writeJsonResponse(ObjectToJson.parseGroupListToJson(groupList), out);
    }

}
