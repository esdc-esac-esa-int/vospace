package esac.archive.vospace.persistence.dao;

import java.util.List;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.LinkNode;

/**
 * Interface to define methods used by LinkNode DAO.
 * @author snieto
 *
 */
public interface ILinkNodeDao extends IHibernateDAO {

	/**
	 * Return an empty LinkNode.
	 * @return LinkNode object empty.
	 */
	LinkNode getEmptyLinkNode();
	
	/**
	 * Query LinkNode by input target.
	 * @param target of LinkNode
	 * @return List<LinkNode> if found.
	 */
	List<LinkNode> queryLinkNodeByTarget(String target);
	
	/**
	 * Query LinkNode by input id.	
	 * @param nodeId
	 * @return LinkNode if found.
	 */
	LinkNode queryLinkNodeById(Integer nodeId);
}
