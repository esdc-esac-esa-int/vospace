package esavo.vospace.json;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import esavo.vospace.common.model.transferobjects.CapabilityParamTO;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.GroupNodePropertyTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.UserNodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceNodeType;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
import esavo.vospace.json.utils.JsonConstants;

public class ObjectToJson {
    
    public static JSONObject parseSharedNodeListToJson (Map<NodeTO,String> childs) {
        JSONObject result = new JSONObject();        
        try {
            List<JSONObject> objList = new LinkedList<JSONObject>();
            Iterator<Entry<NodeTO, String>> it = childs.entrySet().iterator();
            while (it.hasNext()) {                
                Map.Entry<NodeTO, String> pair = (Map.Entry<NodeTO, String>)it.next();
                JSONObject childObj = parseSharedNode(pair.getKey(), pair.getValue());
                objList.add(childObj);
            }
            result.put(JsonConstants.NODE_LIST, objList);
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static JSONObject parseNodeListToJson (List<NodeTO> childs) {
        JSONObject resultObj = new JSONObject();
        
        List<JSONObject> childObj = parseNodeList(childs);
        try{
            resultObj.put(JsonConstants.NODE_LIST, childObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultObj;
    }
    
    public static JSONObject parseSharedNode (NodeTO node, String role) {
        JSONObject nodeObj = new JSONObject();
        try {
            
            nodeObj.put(JsonConstants.NODE_URI, node.getURI());
            nodeObj.put(JsonConstants.NODE_TYPE, node.getType().toString());

            JSONObject owner = parseUserToJson (node.getUserTO(), false);
            nodeObj.put(JsonConstants.NODE_OWNER, owner);
                
            nodeObj.put(JsonConstants.NODE_ROLE, role);
                
            List<NodePropertyTO> properties = node.getNodeProperties();
            List<JSONObject> propertiesObj = new LinkedList<JSONObject>();
            if (properties != null) {
                propertiesObj = parseNodeProperties(properties);
            }
            nodeObj.put(JsonConstants.PROPERTIES_LIST, propertiesObj);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return nodeObj;
    }
    
    /*
     * Parse a list of nodes into JSON
     */
    public static List<JSONObject> parseNodeList (List<NodeTO> childs) {
        List<JSONObject> nodeObjects = new LinkedList<JSONObject>();
        try {

            for (NodeTO node : childs) {
                JSONObject nodeObj = new JSONObject();

                nodeObj.put(JsonConstants.NODE_URI, node.getURI());
                nodeObj.put(JsonConstants.NODE_TYPE, node.getType().toString());

                JSONObject owner = parseUserToJson (node.getUserTO(), true);
                nodeObj.put(JsonConstants.NODE_OWNER, owner);
                
                List<NodePropertyTO> properties = node.getNodeProperties();
                List<JSONObject> propertiesObj = new LinkedList<JSONObject>();
                if (properties != null) {
                    propertiesObj = parseNodeProperties(properties);
                }
                nodeObj.put(JsonConstants.PROPERTIES_LIST, propertiesObj);

                nodeObjects.add(nodeObj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return nodeObjects;
    }

    public static JSONObject parseNodeToJson (NodeTO node) {
        JSONObject nodeObj = new JSONObject();
        try {
            nodeObj.put(JsonConstants.NODE_URI, node.getURI());
            nodeObj.put(JsonConstants.NODE_TYPE, node.getType().toString());

            JSONObject owner = parseUserToJson (node.getUserTO(), true);
            nodeObj.put(JsonConstants.NODE_OWNER, owner);

            List<JSONObject> propertiesObj = parseNodeProperties(node.getNodeProperties());
            nodeObj.put(JsonConstants.PROPERTIES_LIST, propertiesObj);

            if (node.getType().compareTo(VOSpaceNodeType.CONTAINER_NODE) == 0) {
                ContainerNodeTO container = ((ContainerNodeTO) node);
                List<NodeTO> nodeList = container.getNodeList();
                List<JSONObject> childObj = parseNodeList(nodeList);
                nodeObj.put(JsonConstants.NODE_LIST, childObj);
            }            
            //Capabilities
            List<JSONObject> capabilitiesObj = parseNodeCapabilities(node.getCapabilities());
            nodeObj.put(JsonConstants.CAPABILITIES_LIST, capabilitiesObj);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return nodeObj;
    }

    private static List<JSONObject> parseNodeProperties (List<NodePropertyTO> properties) {
        List<JSONObject> propertyObjects = new LinkedList<JSONObject>();
        
        for (NodePropertyTO property : properties) {                
            JSONObject propObj = parsePropertyToJson(property);
            propertyObjects.add(propObj);
        }

        return propertyObjects;
    }
    
    public static JSONObject parsePropertyToJson (NodePropertyTO property) {
        JSONObject propObj = new JSONObject();

        try {
            String uri = property.getVoPropertyTO().getURI();
            //VOSpaceURI propertyURI = new VOSpaceURI(uri);
            propObj.put(JsonConstants.PROPERTY_URI, uri);
            propObj.put(JsonConstants.PROPERTY_READ, property.getVoPropertyTO().getReadOnly());
            
            if (property instanceof GroupNodePropertyTO) {                    
                String value = "";
                List<GroupTO> groupList = ((GroupNodePropertyTO)property).getGroupList();
                for (GroupTO groupTO : groupList) {
                    if (value.isEmpty())
                        value += groupTO.getName();
                    else
                        value += "," + groupTO.getName();
                }
                propObj.put(JsonConstants.PROPERTY_VALUE, value);
            } else if (property instanceof UserNodePropertyTO) {
                String value = "";
                List<UserTO> userList = ((UserNodePropertyTO) property).getUserList();
                for (UserTO userTO : userList) {
                    if (value.isEmpty())
                        value += userTO.getName();
                    else
                        value += "," + userTO.getName();
                }
                propObj.put(JsonConstants.PROPERTY_VALUE, value);
            } else
                propObj.put(JsonConstants.PROPERTY_VALUE, property.getValue());
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return propObj;
    }

    private static List<JSONObject> parseNodeCapabilities (List<VoCapabilityTO> capabilities) {
        List<JSONObject> capabilityObjects = new LinkedList<JSONObject>();
        
        for (VoCapabilityTO capability : capabilities) {                
            JSONObject capObj = parseCapabilityToJson(capability);
            capabilityObjects.add(capObj);
        }

        return capabilityObjects;
    }    
    
    public static JSONObject parseCapabilityToJson (VoCapabilityTO capability) {
        JSONObject capObj = new JSONObject();
        
        try {
            capObj.put(JsonConstants.CAPABILITY_URI, capability.getURI());
            capObj.put(JsonConstants.CAPABILITY_ENDPOINT, capability.getEndPoint());
            
            @SuppressWarnings("unused")
            List<CapabilityParamTO> capabilityList = capability.getParams();
            //TODO parse capability params
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return capObj;
    }
    
    public static JSONObject parseUserToJson (UserTO user, boolean fullDetails) {
        JSONObject userObj = new JSONObject();
        try {
            userObj.put(JsonConstants.USER_NAME, user.getName());
            userObj.put(JsonConstants.USER_FULLNAME, user.getFullName());
            if (fullDetails){
                userObj.put(JsonConstants.USER_MAIL, user.getMail());
                userObj.put(JsonConstants.USER_QUOTA, user.getQuota());
                userObj.put(JsonConstants.USER_QLIMIT, user.getQLimit());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userObj;
    }

    /*public static JSONObject parseUserToJson (UserTO user, double quota) {
        JSONObject userObj = new JSONObject();
        try {
            userObj.put(JsonConstants.USER_NAME, user.getName());
            userObj.put(JsonConstants.USER_FULLNAME, user.getFullName());
            userObj.put(JsonConstants.USER_MAIL, user.getMail());
            userObj.put(JsonConstants.USER_QUOTA, quota);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userObj;
    }*/

    public static JSONObject parseUserListToJson (List<UserTO> userList) {
        JSONObject responseObj = new JSONObject();
        List<JSONObject> userObjects = new LinkedList<JSONObject>();

        for (UserTO user : userList) {
            JSONObject userObj = parseUserToJson(user, true);
            userObjects.add(userObj);         
        }
        try {
            responseObj.put(JsonConstants.USER_LIST, userObjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseObj;
    }

    public static JSONObject parseGroupNameListToJson (List<String> groupList) {
        JSONObject responseObj = new JSONObject();
        List<JSONObject> groupObjects = new LinkedList<JSONObject>();

        for (String groupname : groupList) {
            JSONObject groupObj = new JSONObject();
            try {
                groupObj.put(JsonConstants.GROUP_NAME, groupname);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            groupObjects.add(groupObj);
         }

        try {
            responseObj.put(JsonConstants.GROUP_LIST, groupObjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseObj;
    }

    public static JSONObject parseGroupToJson (GroupTO group) {

        JSONObject groupObj = new JSONObject();
        try {
            groupObj.put(JsonConstants.GROUP_NAME, group.getName());
            groupObj.put(JsonConstants.GROUP_DESC, group.getDescription());

            List<JSONObject> memberObjects = new LinkedList<JSONObject>();
             List<UserTO> members = group.getMembers();
            if (members != null) {
                for (UserTO user : members) {
                    JSONObject userObj = new JSONObject();
                    
                    userObj.put(JsonConstants.USER_NAME, user.getName());
                    userObj.put(JsonConstants.USER_FULLNAME, user.getFullName());
                    userObj.put(JsonConstants.USER_MAIL, user.getMail());
                    memberObjects.add(userObj);
                }
            }
            groupObj.put(JsonConstants.MEMBER_LIST, memberObjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return groupObj;
    }
    
    public static JSONObject parseGroupListToJson (List<GroupTO> groupList) {
        JSONObject responseObj = new JSONObject();
        List<JSONObject> groupObjects = new LinkedList<JSONObject>();

        for (GroupTO group : groupList) {
            groupObjects.add(parseGroupToJson(group));
         }

        try {
            responseObj.put(JsonConstants.GROUP_LIST, groupObjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseObj;
    }
}
