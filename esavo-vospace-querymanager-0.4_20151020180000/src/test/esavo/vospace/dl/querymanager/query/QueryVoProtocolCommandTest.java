package esavo.vospace.dl.querymanager.query;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import esac.archive.vospace.persistence.model.VosType;
import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.dl.querymanager.AbstractQueryManagerTestClass;

public class QueryVoProtocolCommandTest extends AbstractQueryManagerTestClass {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryVoProtocolCommandTest.class);
    
    /**
     * Initialize query manager services before instantiating this class.
     */
    @BeforeClass
    public static final void runBeforeClass() {
        initializeVOspaceQueryManagerServices();
    }
    
    /**
     * Public constructor.
     */
    public QueryVoProtocolCommandTest() {
        super();
    }
    
    /**
     * Method to test QueryHandler#doIngestHstCompositeObservation method.
     */
    @Test
    public final void testDoQueryVoProtocol() {
        try {
            log.info("Into QueryVoProtocol."
                    + "testDoQueryVoProtocol()");

            List<VoProtocolTO> voProtocolTOList = this.getQueryManager().doQueryVoProtocols(VosType.PROVIDE.toString());
            for (VoProtocolTO voProtocolTO : voProtocolTOList) {
                System.out.println("Uri Protocol: " + voProtocolTO.getURI());
            }

            log.debug("End of QueryVoProtocol."
                    + "testDoQueryVoProtocol()");
        } catch (Exception e) {
            log.error("Error occurred", e);
            throw new AssertionFailedError();
        }
    }
}
