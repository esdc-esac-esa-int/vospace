package esavo.vospace.dl.querymanager;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.dl.querymanager.service.VospaceQueryManagerImpl;

/**
 * Abstract class to have common methods for all query handler junit tests.
 * @author ileon
 */
public abstract class AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(AbstractQueryManagerTestClass.class);

    /**
     * Query handler.
     */
    private VospaceQueryManagerImpl queryManager;

    /** Dummy Integer value. */
    public static final Integer DUMMY_INTEGER_VALUE = new Integer(666);

    /** Dummy Double value. */
    public static final Double DUMMY_DOUBLE_VALUE = new Double(666.666016);

    /** Dummy Long value. */
    public static final Long DUMMY_LONG_VALUE = new Long(666666016);

    /** Dummy Big decimal value. */
    public static final BigDecimal DUMMY_BIG_DECIMAL_VALUE = new BigDecimal(666.666016);

    /** Dummy Date value. */
    public static final Date DUMMY_DATE_VALUE = new Date(100, 10, 10, 10, 10, 10);

    /** Dummy String value. */
    public static final String DUMMY_STRING_VALUE = "dummyStringValue";

    /** Dummy composite observation id. */
    public static final String DUMMY_COMPOSITE_OBSERVATION_VALUE = "dummyComp";

    /** Dummy simple observation id. */
    public static final String DUMMY_SIMPLE_OBSERVATION_VALUE = "dummySimp";

    /** Dummy Boolean value. */
    public static final Boolean DUMMY_BOOLEAN_VALUE = Boolean.FALSE;

    /**
     * Constructor.
     */
    public AbstractQueryManagerTestClass() {
        this.queryManager = VospaceQueryManagerImpl.getInstance();
    }

    /**
     * Initialize query manager services before instantiating this class.
     */
    protected static void initializeVOspaceQueryManagerServices() {
        log.info("Into AbstractQueryManagerTestClass.initializeVOspaceQueryManagerServices()");
        QueryManagerInitializerSingleton.getInstance(); // Initialize query manager services:
        log.info("End of AbstractQueryManagerTestClass.initializeVOspaceQueryManagerServices()");
    }

    /**
     * @return the queryHandler
     */
    public final VospaceQueryManagerImpl getQueryManager() {
        return this.queryManager;
    }
}
