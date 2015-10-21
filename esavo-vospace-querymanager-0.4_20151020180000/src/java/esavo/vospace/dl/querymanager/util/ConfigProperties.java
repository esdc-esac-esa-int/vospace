package esavo.vospace.dl.querymanager.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is used to manage the configuration properties for the application. All access to the
 * properties should be done through this class.
 * @author Nicolas Fajersztejn
 */
public class ConfigProperties {

    private static Log LOG = LogFactory.getLog(ConfigProperties.class);

    private static final Properties properties = new Properties();

    /**
     * Name of the file containing the properties. Should be located in the classpath.
     */
    private static final String PROPERTIES_FILE = "QueryManager.properties";

    static {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        InputStream in = loader.getResourceAsStream(PROPERTIES_FILE);
        try {
            LOG.info("Loading properties file '" + PROPERTIES_FILE + "'");
            properties.load(in);
        } catch (IOException e) {
            LOG.error("Error loading properties file '" + PROPERTIES_FILE + "'", e);
            System.exit(-1);
        }
        LOG.debug("Properties loaded: " + properties);
    }

    /**
     * Retrieve property value.
     * @param prop Input property.
     * @return Property string value.
     */
    public static String getProperty(final String prop) {
        return properties.getProperty(prop);
    }
}
