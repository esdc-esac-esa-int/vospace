/**
 *
 */
package esavo.vospace.service.utils;


/**
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 *
 */
public class VOSConstants {
        
    public static final String VOS_PREFIX = 
            ConfigProperties.getProperty("vospace.prefix");    
    public static final String FILESYSTEM_PATH = 
            ConfigProperties.getProperty("vospace.file.system");    
    public static final String SERVER_URL = 
            ConfigProperties.getProperty("server.url");
    public static final String BAR = System.getProperty("file.separator");
    public static final String DOWNLOAD_PATH =
            ConfigProperties.getProperty("vospace.download");
    public static final String DATA_FOLDER = ".data";   
    public static final String DATA_SERVICE = "data";
    
    //public static final String PROVIDER_URL = "";
    
}
