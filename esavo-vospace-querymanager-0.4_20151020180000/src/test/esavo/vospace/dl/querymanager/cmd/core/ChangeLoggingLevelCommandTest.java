package esavo.vospace.dl.querymanager.cmd.core;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

/**
 * JUnit test class for the ChangeLoggingLevelCommand class.
 * @author ileon
 */
public class ChangeLoggingLevelCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(ChangeLoggingLevelCommandTest.class);

    /**
     * Public constructor.
     */
    public ChangeLoggingLevelCommandTest() {
        super();
    }

    /**
     * Initialize query manager services before instantiating this class.
     */
    @BeforeClass
    public static final void runBeforeClass() {
        initializeVOspaceQueryManagerServices();
    }

    /**
     * Execute before EVERY test.....
     */
    @Before
    public final void runBeforeTests() {
        // log.info("runBeforeCallingQuerymanager....");
    }

    /**
     * After tests.........
     */
    @After
    public final void runAfterTests() {
        // log.info("runAfterCallingQueryManager....");
    }

    /**
     * Test insertDataRetrievalLog command.
     * @throws Exception If any error is found.
     */
    @Test
    public final void testChangeLoggingLevel() throws Exception {
        try {
            log.info("Into ChangeLoggingLevelCommandTest.testChangeLoggingLevel()");
            String logLevel = "debug";
            log.info("New log level:    [" + logLevel + "]");
            getQueryManager().doChangeLoggingLevelCommand(logLevel);

            // Check success...
            Assert.assertEquals(Level.DEBUG, Logger.getRootLogger().getLevel());

            logLevel = "info";
            log.info("New log level:    [" + logLevel + "]");
            getQueryManager().doChangeLoggingLevelCommand(logLevel);

            // Check success...
            Assert.assertEquals(Level.INFO, Logger.getRootLogger().getLevel());

            log.info("End of ChangeLoggingLevelCommandTest.testChangeLoggingLevel()");
        } catch (Exception e) {
            log.info("Exception in ChangeLoggingLevelCommandTest.testChangeLoggingLevel", e);
            throw e;
        }
    }

}
