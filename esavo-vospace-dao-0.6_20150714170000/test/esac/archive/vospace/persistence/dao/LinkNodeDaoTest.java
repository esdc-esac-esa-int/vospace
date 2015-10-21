package test.esac.archive.vospace.persistence.dao;

import static org.junit.Assert.assertNotNull;
import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.junit.Test;

import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.VospaceDaoServiceImpl;
import esac.archive.vospace.persistence.dao.ILinkNodeDao;
import esac.archive.vospace.persistence.model.LinkNode;
import esac.archive.vospace.persistence.model.Node;
import test.esac.archive.vospace.persistence.HstObjectFactory;
import test.esac.archive.vospace.persistence.LazyLoadingTestCase;

public class LinkNodeDaoTest extends LazyLoadingTestCase {

    /** Logger. */
    private static Logger logger = Logger.getLogger(LinkNodeDaoTest.class);
    
    private ILinkNodeDao linkNodeDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getLinkNodeDAO();
    
    public LinkNodeDaoTest(){
        super();
    }
    
    /**
     * Test insert and delete link node.
     */
    @Test
    public final void testInsertAndDeleteLinkNode() {
        logger.info("Into LinkNodeDaoTest.testInsertAndDeleteLinkNode()");
        try {
            LinkNode node = HstObjectFactory.getInstance().insertAndReturnLinkNodeObject();
            HstObjectFactory.getInstance().deleteTestLinkNodeFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in LinkNodeDaoTest.testInsertAndDeleteLinkNode()", e);
            throw new AssertionFailedError();
        }

        logger.info("End of LinkNodeDaoTest.testInsertAndDeleteLinkNode()");
    }
    
    /**
     * Test insert, query and delete link node.
     */
    @Test
    public final void queryLinkNode() {

        logger.info("Into LinkNodeDaoTest.queryLinkNode()");
        try {

            Node node = HstObjectFactory.getInstance().insertAndReturnLinkNodeObject();

            // Let's query for it
            LinkNode insertedNode = this.linkNodeDao.queryLinkNodeById(node.getNodeOid());
            
            System.out.println("Oid " + node.getNodeOid());
            
            logger.info("LinkNode [" + insertedNode + "]");

            assertNotNull(insertedNode);

            // Delete artifact
            HstObjectFactory.getInstance().deleteTestLinkNodeFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in LinkNodeDaoTest.queryLinkNode()", e);
            throw new AssertionFailedError();
        }
        logger.info("End of LinkNodeDaoTest.queryLinkNode()");
    }
    
}
