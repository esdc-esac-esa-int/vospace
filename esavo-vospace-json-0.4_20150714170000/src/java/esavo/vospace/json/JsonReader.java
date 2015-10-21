package esavo.vospace.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.exceptions.VOSpaceException;

public class JsonReader {

    private static Logger log = Logger.getLogger(JsonReader.class);
    
    public static NodeTO readNode(InputStream in) throws VOSpaceException {
        log.debug("Read node");
        
        BufferedReader br = null;
        String jsonString = "";
        try {
 
            br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) {
                jsonString += line;
            }
            return JsonToObject.parseJsonToNode(jsonString);
        } catch (IOException e) {
            throw new VOSpaceException(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new VOSpaceException(e.getMessage());
                }
            }
        }
    }
    
    public static List<NodeTO> readNodeList(InputStream in) throws VOSpaceException {
        log.debug("Read node list");
        
        BufferedReader br = null;
        String jsonString = "";
        try {
 
            br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) {
                jsonString += line;
            }
            return JsonToObject.parseJsonToNodeList(jsonString);
        } catch (IOException e) {
            throw new VOSpaceException(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new VOSpaceException(e.getMessage());
                }
            }
        }
    }
}
