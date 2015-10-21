package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.StructuredDataNode;

/**
 * Interface to define methods used by StructuredDataNode DAO.
 * @author snieto
 *
 */
public interface IStructuredDataNodeDao extends IHibernateDAO {

	/**
	 * Returns an empty StructuredDataNode object.
	 * @return StructuredDataNode
	 */
	StructuredDataNode getEmptyStructuredDataNode();
	
	/**
	 * Query StructuredDataNode by input id.
	 * @param id of StructuredDataNode
	 * @return StructuredDataNode if found.
	 */
	StructuredDataNode queryStructuredDataNodeById(Integer id);
}
