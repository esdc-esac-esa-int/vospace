package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.ContainerNode;

/**
 * Interface to define methods used by ContainerNode DAO.
 * @author snieto
 *
 */
public interface IContainerNodeDao extends IHibernateDAO {

	/**
     * Returns an ContainerNode Class object, used by Session and Criteria object.
     * @return ContainerNode class
     */
    ContainerNode getEmptyContainerNode();
    
    /**
     * Query ContainerNode by nodeId.
     * @param nodeId
     * @return ContainerNode if exists.
     */
    ContainerNode queryContainerNodeById(Integer nodeId);
    
    /**
     * Query the list of children
     * @param nodeId
     * @return List<Node>
     */
    /*List<Node> queryChildrenByParentId(Integer nodeId);*/
}
