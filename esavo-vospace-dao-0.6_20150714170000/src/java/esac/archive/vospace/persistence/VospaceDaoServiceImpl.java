/**
 * 
 */
package esac.archive.vospace.persistence;

import esac.archive.absi.persistence.AbstractDAOService;
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
 * ServiceManager for providing Dao objects.
 * 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 */
public class VospaceDaoServiceImpl extends AbstractDAOService implements IVospaceDaoService {

    /**
     * Path to the file with the configuration of the Dao's.
     */
    private static final String SPRING_DAO_CONFIG_XML_FILE = "esac/archive/vospace/persistence/spring-dao-config.xml";

    /**
     * Constructor, it will initialize the spring context.
     */
    public VospaceDaoServiceImpl() {
        super(SPRING_DAO_CONFIG_XML_FILE);
    }
    
    @Override
    public IGroupsDao getGroupsDAO() {        
        return (IGroupsDao) this.appContext.getBean("groupsDao");
    }

    @Override
    public INodeDao getNodeDAO() {
        return (INodeDao) this.appContext.getBean("nodeDao");
    }
    
    @Override
    public IContainerNodeDao getContainerNodeDAO() {
        return (IContainerNodeDao) this.appContext.getBean("containerNodeDao");
    }

    @Override
    public IDataNodeDao getDataNodeDAO() {
        return (IDataNodeDao) this.appContext.getBean("dataNodeDao");
    }

    @Override
    public ILinkNodeDao getLinkNodeDAO() {
        return (ILinkNodeDao) this.appContext.getBean("linkNodeDao");
    }

    @Override
    public IStructuredDataNodeDao getStructuredDataNodeDAO() {
        return (IStructuredDataNodeDao) this.appContext.getBean("structuredDataNodeDao");
    }

    @Override
    public IUnstructuredDataNodeDao getUnstructuredDataNodeDAO() {
        return (IUnstructuredDataNodeDao) this.appContext.getBean("unstructuredDataNodeDao");
    }

    @Override
    public IUsersDao getUsersDAO() {
        return (IUsersDao) this.appContext.getBean("usersDao");
    }

    @Override
    public IVoCapabilityDao getVoCapabilityDAO() {
        return (IVoCapabilityDao) this.appContext.getBean("voCapabilityDao");
    }

    @Override
    public IVoPropertyDao getVoPropertyDAO() {
        return (IVoPropertyDao) this.appContext.getBean("voPropertyDao");
    }

    @Override
    public IVoProtocolDao getVoProtocolDAO() {
        return (IVoProtocolDao) this.appContext.getBean("voProtocolDao");
    }

    @Override
    public IVoViewDao getVoViewDAO() {
        return (IVoViewDao) this.appContext.getBean("voViewDao");
    }

    @Override
    public ICapabilityParamDao getCapabilityParamDao() {
        return (ICapabilityParamDao) this.appContext.getBean("capabilityParamDao");
    }

    @Override
    public IProtocolParamDao getProtocolParamDao() {
        return (IProtocolParamDao) this.appContext.getBean("protocolParamDao");
    }

    @Override
    public IViewParamDao getViewParamDao() {
        return (IViewParamDao) this.appContext.getBean("viewParamDao");
    }

    @Override
    public INodePropertyDao getNodePropertyDAO() {
        return (INodePropertyDao) this.appContext.getBean("nodePropertyDao");
    }

    @Override
    public IAccessLogDao getAccessLogDao() {
        return (IAccessLogDao) this.appContext.getBean("accessLogDao");
    }

    @Override
    public IDataMngLogDao getDataMngLogDao() {
        return (IDataMngLogDao) this.appContext.getBean("dataMngLogDao");
    }

    @Override
    public IGroupControlDao getGroupControlDao() {
        return (IGroupControlDao) this.appContext.getBean("groupControlDao");
    }

    @Override
    public IUserControlDao getUserControlDao() {
        return (IUserControlDao) this.appContext.getBean("userControlDao");
    }

    @Override
    public IMetadataRetrievalLogDao getMetadataRetrievalLogDao() {
        return (IMetadataRetrievalLogDao) this.appContext.getBean("metadataRetrievalLogDao");
    }

    @Override
    public IShareLogDao getShareLogDao() {
        return (IShareLogDao) this.appContext.getBean("shareLogDao");
    }

    @Override
    public ITransferLogDao getTransferLogDao() {
        return (ITransferLogDao) this.appContext.getBean("transferLogDao");
    }

}
