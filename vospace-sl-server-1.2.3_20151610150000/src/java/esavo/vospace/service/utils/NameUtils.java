package esavo.vospace.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameUtils {

    private static final Pattern COLON = Pattern.compile(":", Pattern.LITERAL);
    private static final Pattern SLASH = Pattern.compile("/", Pattern.LITERAL);
    private static final Pattern QUEST_MARK = Pattern.compile("?", Pattern.LITERAL);
    private static final Pattern EQUAL = Pattern.compile("=", Pattern.LITERAL);
    private static final Pattern AMP = Pattern.compile("&", Pattern.LITERAL);
    private static final Pattern BLANK = Pattern.compile(" ", Pattern.LITERAL);
    private static final Pattern COMMA = Pattern.compile(";", Pattern.LITERAL);
    private static final Pattern DOLLAR = Pattern.compile("$", Pattern.LITERAL);
    private static final Pattern HASH = Pattern.compile("#", Pattern.LITERAL);
    private static final String PATTERN = "^[^`~,:;'?^\"<>/|}{%#$&!=+]+$";
    
    /**
     * Returns true if the areaText does not contain invalid characters.
     * False otherwise.
     * @param areaText
     * @return true if areText is valid, false otherwise.
     */
    public static boolean processTextArea(String areaText) {
        boolean success = false;
        
        //String pattern = "^[^`~,:;'?^\"<>/|}{%#$&!=+]+$";
        
        Pattern p = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE);
        
        Matcher m = p.matcher(areaText);
        
        if (!areaText.isEmpty() && /*areaText.matches(pattern)*/ m.find())
          success = true;
        return success;
    }
    
    /**
     * Encode special characters and retrieve the special characters. 
     * @param url
     * @return string encoded.
     */
    public static String encodeUrl (String url) {
        String value = url;
        value = COLON.matcher(value).replaceAll("%3A");
        value = SLASH.matcher(value).replaceAll("%2F");
        value = QUEST_MARK.matcher(value).replaceAll("%3F");
        value = EQUAL.matcher(value).replaceAll("%3D");
        value = BLANK.matcher(value).replaceAll("+");
        value = AMP.matcher(value).replaceAll("%26");
        value = COMMA.matcher(value).replaceAll("%3B");
        value = DOLLAR.matcher(value).replaceAll("%26");
        value = HASH.matcher(value).replaceAll("%23");
        //logger.log(Level.INFO, "Value after replacement: " + value);
        return value;
    }
}
