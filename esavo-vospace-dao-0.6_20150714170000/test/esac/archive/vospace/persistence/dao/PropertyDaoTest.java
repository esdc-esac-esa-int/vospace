package test.esac.archive.vospace.persistence.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.junit.Test;

import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.INodePropertyDao;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.UserNodeProperty;
import esac.archive.vospace.persistence.model.VoProperty;
import test.esac.archive.vospace.persistence.LazyLoadingTestCase;

public class PropertyDaoTest extends LazyLoadingTestCase {

    /** Logger. */
    private static Logger logger = Logger.getLogger(PropertyDaoTest.class);
    
    private IVoPropertyDao voPropertyDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoPropertyDAO();
    
    private INodePropertyDao nodePropertyDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getNodePropertyDAO();
    
    public PropertyDaoTest() {
        super();
    }
    
    /**
     * Test insert and delete voProperty.
     */
    @Test
    public final void testInsertAndDeleteVoProperty() {
        logger.info("Into PropertyDaoTest.testInsertAndDeleteVoProperty()");
        try {
            //VoProperty property = HstObjectFactory.getInstance().insertAndReturnPropertyObject();
            //HstObjectFactory.getInstance().deleteTestVoCapabilityFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in PropertyDaoTest.testInsertAndDeleteVoProperty()", e);
            throw new AssertionFailedError();
        }

        logger.info("End of PropertyDaoTest.testInsertAndDeleteVoProperty()");
    }
    
    /**
     * Test insert, query and delete voProperty.
     */
    @Test
    public final void queryVoProperty() {

        logger.info("Into CapabilityDaoTest.queryVoProperty()");
        try {

            //VoProperty property = HstObjectFactory.getInstance().insertAndReturnPropertyObject();
            // Let's query for it
            //List<VoProperty> voPropertyList = this.voPropertyDao.queryVoPropertyByType(VosType.provide);            
            //List<VoProperty> voPropertyList = this.voPropertyDao.queryPropertiesUsed();
            
            VoProperty voProperty = this.voPropertyDao.queryVoPropertyByUri("vos://esavo!vospace/properties#userwrite");            
            List<NodeProperty> voPropertyList  = this.nodePropertyDao.queryNodePropertyByVoProperty(voProperty);
            for (NodeProperty nodeProperty : voPropertyList) {
                if (nodeProperty instanceof UserNodeProperty) {
                    System.out.println("Node " + ((UserNodeProperty)nodeProperty).getShareUsers().size());
                }
            }
            
            NodeProperty nodeProperty  = this.nodePropertyDao.queryNodePropertyById(5000);
            if (nodeProperty instanceof UserNodeProperty) {
                System.out.println("Node " + ((UserNodeProperty)nodeProperty).getShareUsers().size());
            }
            
            
            //VoProperty voPropertyList = this.voPropertyDao.queryVoPropertyByUri("vos://esavo!vospace/properties#description");            
            /*if (voPropertyList != null) {
                for (Node p : voPropertyList) {                    
                    System.out.println("Node " + p.getName());
                }
            }*/

            assertNotNull(nodeProperty);
        } catch (Exception e) {
            logger.error("Exception in CapabilityDaoTest.queryVoProperty()", e);
            throw new AssertionFailedError();
        }
        logger.info("End of CapabilityDaoTest.queryVoProperty()");
    }
}
