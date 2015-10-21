package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.DataNode;

/**
 * Interface to define methods used by DataNode DAO.
 * @author snieto
 *
 */
public interface IDataNodeDao extends IHibernateDAO {

	/**
	 * Return an empty DataNode 
	 * @return DataNode empty
	 */
	DataNode getEmptyDataNode();
	
	/**
	 * Query DataNode by nodeId.
	 * @param nodeId
	 * @return DataNode if exists
	 */
	DataNode queryDataNodeById(Integer nodeId);
}
