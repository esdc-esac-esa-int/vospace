package esac.archive.vospace.persistence;

import esac.archive.absi.persistence.IDAOService;
import esac.archive.vospace.persistence.dao.IAccessLogDao;
import esac.archive.vospace.persistence.dao.ICapabilityParamDao;
import esac.archive.vospace.persistence.dao.IContainerNodeDao;
import esac.archive.vospace.persistence.dao.IDataMngLogDao;
import esac.archive.vospace.persistence.dao.IDataNodeDao;
import esac.archive.vospace.persistence.dao.IGroupControlDao;
import esac.archive.vospace.persistence.dao.IGroupsDao;
import esac.archive.vospace.persistence.dao.ILinkNodeDao;
import esac.archive.vospace.persistence.dao.IMetadataRetrievalLogDao;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.dao.INodePropertyDao;
import esac.archive.vospace.persistence.dao.IProtocolParamDao;
import esac.archive.vospace.persistence.dao.IShareLogDao;
import esac.archive.vospace.persistence.dao.IStructuredDataNodeDao;
import esac.archive.vospace.persistence.dao.ITransferLogDao;
import esac.archive.vospace.persistence.dao.IUnstructuredDataNodeDao;
import esac.archive.vospace.persistence.dao.IUserControlDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.dao.IViewParamDao;
import esac.archive.vospace.persistence.dao.IVoCapabilityDao;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.dao.IVoProtocolDao;
import esac.archive.vospace.persistence.dao.IVoViewDao;

/**
 * Interface for Dao Services.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 */
public interface IVospaceDaoService extends IDAOService {
   
    /**
     * Returns IContainerNodeDao object (VOSpace database).
     * @return ContainerNodeDao
     */
    IContainerNodeDao getContainerNodeDAO();
    
    /**
     * Returns IDataNodeDao object (VOSpace database).
     * @return DataNodeDao
     */
    IDataNodeDao getDataNodeDAO();
    
    /**
     * Returns IGroupsDAO object (VOSpace database).
     * @return groupsDAO
     */
    IGroupsDao getGroupsDAO();
    
    /**
     * Returns ILinkNodeDao object (VOSpace database).
     * @return LinkNodeDao
     */
    ILinkNodeDao getLinkNodeDAO();
    
    /**
     * Returns INodeDao object (VOSpace database).
     * @return NodeDao
     */
    INodeDao getNodeDAO();
    
    /**
     * Returns INodePropertyDao object (VOSpace database)
     * @return NodePropertyDao
     */
    INodePropertyDao getNodePropertyDAO();
    
    /**
     * Returns IStructuredDataNodeDao object (VOSpace database).
     * @return StructuredDataNodeDao
     */
    IStructuredDataNodeDao getStructuredDataNodeDAO();
    
    /**
     * Returns IUnstructuredDataNodeDao object (VOSpace database).
     * @return UnstructuredDataNodeDao
     */
    IUnstructuredDataNodeDao getUnstructuredDataNodeDAO();
    
    /**
     * Returns IUsersDao object (VOSpace database).
     * @return UsersDao
     */
    IUsersDao getUsersDAO();
    
    /**
     * Returns IVoCapabilityDao object (VOSpace database).
     * @return VoCapabilityDao
     */
    IVoCapabilityDao getVoCapabilityDAO();
    
    /**
     * Returns ICapabilityParamDao object (VOSpace database)
     * @return CapabilityParamDao
     */
    ICapabilityParamDao getCapabilityParamDao();
    
    /**
     * Returns IVoPropertyDao object (VOSpace database).
     * @return VoPropertyDao
     */
    IVoPropertyDao getVoPropertyDAO();
    
    /**
     * Returns IVoProtocolDao object (VOSpace database).
     * @return VoProtocolDao
     */
    IVoProtocolDao getVoProtocolDAO();
    
    /**
     * Returns IProtocolParamDao object (VOSpace database)
     * @return ProtocolParamDao
     */
    IProtocolParamDao getProtocolParamDao();
    
    /**
     * Returns IVoViewDao object (VOSpace database).
     * @return VoViewDao
     */
    IVoViewDao getVoViewDAO();
    
    /**
     * Returns IViewParamDao object (VOSpace database).
     * @return ViewParamDao
     */
    IViewParamDao getViewParamDao();
    
    /*********************************************************************
     *                  Vospace Controls Database
     *********************************************************************/
    
    /**
     * Returns IAccessLogDao object (VOSpace Controls database).
     * @return AccessLogDao
     */
    IAccessLogDao getAccessLogDao();
    
    /**
     * Returns IDataMngLogDao object (VOSpace Controls database).
     * @return DataMngLogDao
     */
    IDataMngLogDao getDataMngLogDao();
    
    /**
     * Returns IGroupControlDao object (VOSpace Controls database).
     * @return GroupControlDao
     */
    IGroupControlDao getGroupControlDao();
    
    /**
     * Returns IUserControlDao object (VOSpace Controls database).
     * @return UserControlDao
     */
    IUserControlDao getUserControlDao();
    
    /**
     * Returns IMetadataRetrievalLogDao object (VOSpace Controls database).
     * @return MetadataRetrievalLogDao
     */
    IMetadataRetrievalLogDao getMetadataRetrievalLogDao();
    
    /**
     * Returns IShareLogDao object (VOSpace Controls database).
     * @return ShareLogDao
     */
    IShareLogDao getShareLogDao();
    
    /**
     * Returns ITransferLogDao object (VOSpace Controls database).
     * @return TransferLogDao
     */
    ITransferLogDao getTransferLogDao();
}
