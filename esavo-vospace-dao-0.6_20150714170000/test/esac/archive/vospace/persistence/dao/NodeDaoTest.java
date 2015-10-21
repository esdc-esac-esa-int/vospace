package test.esac.archive.vospace.persistence.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.junit.Test;

import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.VospaceDaoServiceImpl;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoProperty;
import test.esac.archive.vospace.persistence.HstObjectFactory;
import test.esac.archive.vospace.persistence.LazyLoadingTestCase;

public class NodeDaoTest extends LazyLoadingTestCase {

    /** Logger. */
    private static Logger logger = Logger.getLogger(NodeDaoTest.class);
    
    private INodeDao nodeDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getNodeDAO();
    
    private IUsersDao userDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    private IVoPropertyDao propertyDao = ((VospaceDaoServiceImpl) DAOServiceProvider.getDaoService())
            .getVoPropertyDAO();
    
    public NodeDaoTest() {
        super();
    }
    
    /**
     * Test insert and delete node.
     */
    @Test
    public final void testInsertAndDeleteNode() {
        logger.info("Into NodeDaoTest.testInsertAndDeleteNode()");
        try {
            //Node node = HstObjectFactory.getInstance().insertAndReturnNodeObject();
            @SuppressWarnings("unused")
            Node node = HstObjectFactory.getInstance().insertAndReturnCompleteNodeObject();
            //HstObjectFactory.getInstance().deleteTestNodeFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in NodeDaoTest.testInsertAndDeleteNode()", e);
            throw new AssertionFailedError();
        }

        logger.info("End of NodeDaoTest.testInsertAndDeleteNode()");
    }
    
    /**
     * Test insert, query and delete user.
     */
    @Test
    public final void queryNode() {

        logger.info("Into NodeDaoTest.queryNode()");
        try {
            
            //Node node = HstObjectFactory.getInstance().insertAndReturnNodeObject();
            
            /*Users u = this.userDao.queryUserByName("snieto");
            VoProperty v = this.propertyDao.queryVoPropertyByUri("vos://esavo!vospace/properties#length");
            List<Node> insertedNodes = this.nodeDao.queryNodeByName("snieto");
            long size = this.nodeDao.queryUserQuota(u, v);
            System.out.println("User quota: " + size);*/
            
            Node node = this.nodeDao.queryNodeByUri("snieto/copy/2/3");
            String name = node.getName();
            //Node element = this.nodeDao.queryNodeByParentAndName((ContainerNode)nodes.get(0), "EUCL-OTS-TN-8-002_v0.9_EuclidSGS_Architecture_Dossier.pdf");
            
            //String uri = this.nodeDao.queryNodeURIById(insertedNodes.get(0).getNodeOid());
            
            /*if (node != null) {
                System.out.println("Node uri Path: " + node.getName());
            }*/
            
            
            /*Node nodeInserted = insertedNodes.get(0);
            
            Set<NodeProperty> properties = nodeInserted.getNodePropertySet();
            
            logger.info("Node [" + nodeInserted + "]");

            assertNotNull(insertedNodes.get(0));*/

            // Delete artifact
            //HstObjectFactory.getInstance().deleteTestNodeFromDatabase();
        } catch (Exception e) {
            logger.error("Exception in NodeDaoTest.queryNode()", e);
            throw new AssertionFailedError();
        }
        logger.info("End of NodeDaoTest.queryNode()");
    }
}
