package esavo.vospace.json.utils;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {    

    public static Long getLongValue (JSONObject obj, String name) {
        Long value = (long) 0;
        try {
            value = obj.getLong(name);            
        } catch (JSONException e) {}
        return value;
    }
    
    public static String getStringValue (JSONObject obj, String name) {
        String value = "";
        try {
            value = obj.getString(name);            
        } catch (JSONException e) {}
        return value;
    }
    
    public static boolean getBooleanValue (JSONObject obj, String name) {
        boolean value = false;
        try {
            return obj.getBoolean(name);            
        } catch (JSONException e) {}
        return value;
    }
    
    public static JSONObject getJSONObject (JSONObject obj, String name) {
        JSONObject manager = null;
        try {
            manager = obj.getJSONObject(JsonConstants.GROUP_MANAGER);
        } catch (JSONException e) {}
        return manager;
    }
    
    /**
     * Write JsonObject List to OutputStream
     * @param json object
     * @param out OutputStream of response
     */
    public static void writeJsonResponse (List<JSONObject> jsonList, OutputStream out) {        
        PrintWriter pw = new PrintWriter(out);        
        try {
            for (JSONObject jsonObject : jsonList) {
                pw.write(jsonObject.toString());
                pw.flush();
            }
        } finally {
            pw.flush();
            pw.close();
        }
    }
    
    /**
     * Write JsonObject to OutputStream
     * @param json object
     * @param out OutputStream of response
     */
    public static void writeJsonResponse (JSONObject json, OutputStream out) {        
        PrintWriter pw = new PrintWriter(out);
        pw.write(json.toString());
        pw.flush();
        pw.close();
    }
}
