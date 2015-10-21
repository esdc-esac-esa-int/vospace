package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.UnstructuredDataNode;

/**
 * Interface to define methods used by UnstructuredDataNode DAO.
 * @author snieto
 *
 */
public interface IUnstructuredDataNodeDao extends IHibernateDAO {

	/**
	 * Returns an empty UnstructuredDataNode object.
	 * @return UnstructuredDataNode
	 */
	UnstructuredDataNode getEmptyUnstructuredDataNode();
	
	/**
	 * Query StructuredDataNode by input id.
	 * @param id of UnstructuredDataNode
	 * @return UnstructuredDataNode if found.
	 */
	UnstructuredDataNode queryUnstructuredDataNodeById(Integer id);
}
