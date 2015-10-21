package esavo.vospace.service.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is used to manage the configuration properties for the
 * application. All access to the properties should be done through this class.
 */
public class ConfigProperties {

   /**
    * Logging variable.
    */
   private static final Log LOG = LogFactory.getLog(ConfigProperties.class);

   /**
    * Properties object.
    */
   private static final Properties properties = new Properties();

   /**
    * Name of the file contatining the properties. Should be located in the
    * classpath
    */
   private static final String PROPERTIES_FILE = "vospace.properties";

   static {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();

      InputStream in = loader.getResourceAsStream(PROPERTIES_FILE);
      try {
         LOG.info("Loading properties file '" + PROPERTIES_FILE + "'");
         properties.load(in);
      } catch (IOException e) {
         LOG.error("Error loading properties file '" + PROPERTIES_FILE + "'",
                   e);
         System.exit(-1);
      }
      LOG.debug("Properties loaded: " + properties);
   }

   /**
    * Default constructor.
    */
   protected ConfigProperties() {
      // prevents calls from subclass
      throw new UnsupportedOperationException();
   }

   /**
    * Returns value for input property.
    * @param prop Input property.
    * @return Value in properties file.
    */
   public static String getProperty(final String prop) {
      return properties.getProperty(prop);
   }
}
