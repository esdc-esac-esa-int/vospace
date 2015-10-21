package test.esac.archive.vospace.persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.absi.persistence.transaction.TransactionManager;
import esac.archive.vospace.persistence.VospaceDaoServiceImpl;

/**
 * Convenience class to start and stop Hibernate sessions, to avoid a Lazy Loading Initialization
 * error. This class is based on:
 * http://www.jroller.com/kbaum/entry/orm_lazy_initialization_with_dao
 * @author ileon
 */
public class LazyLoadingTestCase {

    /**
     * Commons-logging var.
     */
    private static Log log = LogFactory.getLog(LazyLoadingTestCase.class);

    /**
     * Transaction manager.
     */
    private TransactionManager transactionManager;

    /**
     * Constructor.
     */
    public LazyLoadingTestCase() {
        log.info("Into LazyLoadingTestCase()");
        this.transactionManager = new TransactionManager();
    }

    /**
     * To do before all test cases: Assign SSA DAO, but only once!!
     */
    @BeforeClass
    public static void runBeforeClass() {
        log.info("Into LazyLoadingTestCase.runBeforeClass()");
        // Initialise DAOFactory...
        DAOServiceProvider.setDaoService(new VospaceDaoServiceImpl());
    }

    /**
     * Something to do after all test cases?.
     */
    @AfterClass
    public static void runAfterClass() {
        log.info("Into LazyLoadingTestCase.runAfterClass()");
    }

    /**
     * Start test. It will initialize the session factory.
     * @throws Exception If any error is found.
     */
    @Before
    public final void setUp() throws Exception {
        log.info("Into LazyLoadingTestCase.setUp()");
        this.transactionManager.beginTransaction();
        // Just in case...
        HstObjectFactory.getInstance().deleteTestObjectsFromDatabase();
        flushSession();
    }

    /**
     * Store all performed actions into database.
     */
    protected final void flushSession() {
        log.info("Into LazyLoadingTestCase.flushSession()");
        this.transactionManager.flushSession();
    }

    /**
     * Stops session factory, storing all performed actions into database.
     * @throws Exception If any error is found.
     */
    @After
    public final void tearDown() throws Exception {
        log.info("Into LazyLoadingTestCase.tearDown()");
        // Commit transaction
        this.transactionManager.commitTransaction();
    }
}
