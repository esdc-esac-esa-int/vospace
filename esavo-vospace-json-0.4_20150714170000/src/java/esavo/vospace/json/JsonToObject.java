package esavo.vospace.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.GroupNodePropertyTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserNodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.json.utils.JsonConstants;
import esavo.vospace.json.utils.JsonUtils;

public class JsonToObject {

    public static NodeTO parseJsonToNode (String jsonString) {
        NodeTO node = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            String uri = jsonObject.getString(JsonConstants.NODE_URI);
            String type = jsonObject.getString(JsonConstants.NODE_TYPE);            

            if (type.compareToIgnoreCase(JsonConstants.CONTAINER) == 0) {
                node = new ContainerNodeTO();
            } else if (type.compareToIgnoreCase(JsonConstants.STRUCTURED_DATA_NODE) == 0) {
                node = new StructuredDataNodeTO();
            } else if (type.compareToIgnoreCase(JsonConstants.UNSTRUCTURED_DATA_NODE) == 0) {
                node = new UnstructuredDataNodeTO();
            } else if (type.compareToIgnoreCase(JsonConstants.LINK) == 0) {
                node = new LinkNodeTO();
            }            
            node.setUri(new VOSpaceURI(uri));
            UserTO owner = parseJsonToUser(jsonString);
            node.setUserTO(owner);
            
            //properties
            List<NodePropertyTO> propertyList = parseJsonToPropertyList(jsonString);
            node.setNodeProperties(propertyList);
            
            //TODO capabilities
            

        } catch(JSONException e) {
            e.printStackTrace();
        }
        return node;
    }
    
    public static List<NodeTO> parseJsonToNodeList (String jsonString) {
                
        List<NodeTO> nodeList = new ArrayList<NodeTO>();
        
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray nodeArray = jsonObject.getJSONArray(JsonConstants.NODE_LIST);
            if (nodeArray != null) {
                
                for (int i=0; i<=nodeArray.length()-1; i++) {
                    JSONObject nodeObj = nodeArray.getJSONObject(i);
                    
                    NodeTO node = parseJsonToNode(nodeObj.toString());
                    nodeList.add(node);
                }
            }
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nodeList;
    }
    
    
    
    public static List<NodePropertyTO> parseJsonToPropertyList (String jsonString) {
        
        List<NodePropertyTO> propertyList = new ArrayList<NodePropertyTO>();
        
        try {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray propertyArray = jsonObject.getJSONArray(JsonConstants.PROPERTIES_LIST);

        if (propertyArray != null) {
            
            for (int i=0; i<=propertyArray.length()-1; i++) {

                JSONObject propObj = propertyArray.getJSONObject(i);
                
                String uri = JsonUtils.getStringValue(propObj, JsonConstants.PROPERTY_URI);
                String value = JsonUtils.getStringValue(propObj, JsonConstants.PROPERTY_VALUE);
                boolean readonly = JsonUtils.getBooleanValue(propObj, JsonConstants.PROPERTY_READ);
                String desc = JsonUtils.getStringValue(propObj, JsonConstants.PROPERTY_DESC);

                VoPropertyTO voProperty = new VoPropertyTO();
                voProperty.setURI(uri);
                voProperty.setReadOnly(readonly);
                voProperty.setDescription(desc);
                
                //parse Value
                VOSpaceURI vosuri = new VOSpaceURI(uri);
                String name = vosuri.getFragment();
                
                NodePropertyTO propertyTO;
                if (name.compareTo(JsonConstants.GROUP_READ) == 0 || name.compareTo(JsonConstants.GROUP_WRITE) == 0) {
                    propertyTO = new GroupNodePropertyTO();
                    if (value.isEmpty()) {
                        propertyTO.setNill(true);
                    } else {
                        List<GroupTO> shareGroupList = new ArrayList<GroupTO>();
                        String[] groupList = value.split(",");
                        for (String gname : groupList) {
                            GroupTO group = new GroupTO();
                            group.setName(gname.trim());
                            shareGroupList.add(group);
                        }
                        ((GroupNodePropertyTO) propertyTO).setGroupList(shareGroupList);
                    }
                } else if (name.compareTo(JsonConstants.USER_READ) == 0 || name.compareTo(JsonConstants.USER_WRITE) == 0) {    
                    propertyTO = new UserNodePropertyTO();
                    if (value.isEmpty()) {
                        propertyTO.setNill(true);
                    } else {
                        List<UserTO> shareUserList = new ArrayList<UserTO>();                    
                        String[] userList = value.split(",");
                        for (String uname : userList) {
                            UserTO user = new UserTO();
                            user.setName(uname.trim());
                            shareUserList.add(user);
                        }
                        ((UserNodePropertyTO) propertyTO).setUserList(shareUserList);
                    }   
                } else {
                    propertyTO = new NodePropertyTO();
                    propertyTO.setValue(value);
                }
                
                propertyTO.setVoPropertyTO(voProperty);
                propertyList.add(propertyTO);
            }
        }

        } catch(JSONException e) {
            e.printStackTrace();
        }
        return propertyList;
    }

    public static List<UserTO> parseJsonToUserList (String jsonString) {

        List<UserTO> userList = new ArrayList<UserTO>();
        
        try {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray userArray = jsonObject.getJSONArray(JsonConstants.USER_LIST);

        if (userArray != null) {
            
            for (int i=0; i<=userArray.length()-1; i++) {

                JSONObject userObj = userArray.getJSONObject(i);

                String name = JsonUtils.getStringValue(userObj, JsonConstants.USER_NAME);
                String fullname = JsonUtils.getStringValue(userObj, JsonConstants.USER_FULLNAME);
                String mail = JsonUtils.getStringValue(userObj, JsonConstants.USER_MAIL);
                Long quota = JsonUtils.getLongValue(userObj, JsonConstants.USER_QUOTA);

                UserTO user = new UserTO();
                user.setName(name);
                user.setFullName(fullname);
                user.setMail(mail);
                user.setQuota(quota);
                
                userList.add(user);
            }
        }

        } catch(JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }
    
    public static UserTO parseJsonToUser (String jsonString) {
        UserTO user = null;
        try {
            JSONObject userObj = new JSONObject(jsonString);
            
            String name = JsonUtils.getStringValue(userObj, JsonConstants.USER_NAME);
            String fullname = JsonUtils.getStringValue(userObj, JsonConstants.USER_FULLNAME);
            String mail = JsonUtils.getStringValue(userObj, JsonConstants.USER_MAIL);
            Long quota = JsonUtils.getLongValue(userObj, JsonConstants.USER_QUOTA);
    
            user = new UserTO();
            user.setName(name);
            user.setFullName(fullname);
            user.setMail(mail);
            user.setQuota(quota);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Parse json string to GroupTO and assign user manager.
     * @param jsonString
     * @param manager
     * @return GroupTO
     */
    public static GroupTO parseJsonToGroup (String jsonString, UserTO manager) {
        GroupTO group = parseJsonToGroup(jsonString);
        group.setManagerTO(manager);
        return group;
    }
    
    /**
     * Parse json string to GroupTO.
     * @param jsonString
     * @return GroupTO
     */
    public static GroupTO parseJsonToGroup (String jsonString) {

        GroupTO group = new GroupTO();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            
            String name = JsonUtils.getStringValue(jsonObject, JsonConstants.GROUP_NAME);
            String description = JsonUtils.getStringValue(jsonObject, JsonConstants.GROUP_DESC);            
            group.setName(name);
            group.setDescription(description);

            JSONArray membersArray = jsonObject.getJSONArray(JsonConstants.MEMBER_LIST);
            if (membersArray != null) {

                List<UserTO> userList = new ArrayList<UserTO>();
                for (int j=0; j<=membersArray.length()-1; j++) {
                    JSONObject memberObj = membersArray.getJSONObject(j);

                    String memname = JsonUtils.getStringValue(memberObj, JsonConstants.USER_NAME);
                    String fullname = JsonUtils.getStringValue(memberObj, JsonConstants.USER_FULLNAME);
                    String mail = JsonUtils.getStringValue(memberObj, JsonConstants.USER_MAIL);

                    UserTO user = new UserTO();
                    user.setName(memname);
                    user.setFullName(fullname);
                    user.setMail(mail);

                    userList.add(user);
                }
                group.setMembers(userList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return group;
    }

    /*public static GroupTO parseShareGroupToObject (String jsonString) {

        GroupTO group = new GroupTO();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            String name = jsonObject.getString(GROUP_NAME);
            String desc = jsonObject.getString(GROUP_DESC);
            group.setName(name);
            group.setDescription(desc);

            JSONArray entitiesArray = jsonObject.getJSONArray(ENTITY_LIST);

            if (entitiesArray != null) {

                for (int j=0; j<=entitiesArray.length()-1; j++) {
                    JSONObject entityObj = entitiesArray.getJSONObject(j);

                    String entity = entityObj.getString(ENTITY);

                    //share.getNodes().add(new VOSpaceURI(entity)); ??????
                    
                    
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return group;
    }

    public static ShareUser parseShareUserToObject (String jsonString) {
        ShareUser share = new ShareUser();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            String name = jsonObject.getString(USER_NAME);
            String fullname = jsonObject.getString(USER_FULLNAME);
            String mail = jsonObject.getString(USER_MAIL);
            String role = jsonObject.getString(ROLE);
            share.getUser().setName(name);
            share.getUser().setFullname(fullname);
            share.getUser().setMail(mail);
            share.setRole(role);

            JSONArray entitiesArray = jsonObject.getJSONArray(ENTITY_LIST);

            if (entitiesArray != null) {

                for (int j=0; j<=entitiesArray.length()-1; j++) {
                    JSONObject entityObj = entitiesArray.getJSONObject(j);

                    String entity = entityObj.getString(ENTITY);

                    share.getNodes().add(new VOSpaceURI(entity));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.error(e.getMessage());
        }

        return share;
    }*/
}
