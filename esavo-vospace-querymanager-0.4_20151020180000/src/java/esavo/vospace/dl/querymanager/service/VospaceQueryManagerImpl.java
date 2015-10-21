package esavo.vospace.dl.querymanager.service;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.common.model.exceptions.RuntimeArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.interfaces.dl.querymanager.service.AbstractQueryManagerImpl;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.common.model.transferobjects.control.AccessLogTO;
import esavo.vospace.common.model.transferobjects.control.DataMngLogTO;
import esavo.vospace.common.model.transferobjects.control.GroupControlTO;
import esavo.vospace.common.model.transferobjects.control.MetadataRetrievalLogTO;
import esavo.vospace.common.model.transferobjects.control.ShareLogTO;
import esavo.vospace.common.model.transferobjects.control.TransferLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.dl.querymanager.cmd.VospaceQueryManagerCommandFactory;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;

/**
 * Class which implements all IQueryManagerVospace method calls.
 * @author Sara Nieto
 */
public class VospaceQueryManagerImpl extends AbstractQueryManagerImpl implements IQueryManagerVospace {

    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(VospaceQueryManagerImpl.class);

    /**
     * Singleton instance.
     */
    private static final VospaceQueryManagerImpl INSTANCE = new VospaceQueryManagerImpl();

    /**
     * Making it singleton.
     * @return Instance of QueryHandler
     */
    public static VospaceQueryManagerImpl getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void doInsertUser(final UserTO userTO) throws RemoteException {
        logger.info("Into QueryManagerImpl.doInsertUser()");
        
        IQueryManagerCommand insertUserCommand = VospaceQueryManagerCommandFactory
                .createUserCommand(userTO);
        
        try {
            executeTransactionalCommand(insertUserCommand);
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.insertUserCommand()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "insertUserCommand()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
        
        logger.info("End of QueryManagerImpl.doInsertUser()");
    }

    @Override
    public void doInsertGroup(final GroupTO groupTO, final Boolean overwrite)
            throws RemoteException {
        logger.info("Into QueryManagerImpl.doInsertGroup()");
        
        IQueryManagerCommand insertGroupCommand = VospaceQueryManagerCommandFactory
                .createGroupCommand(groupTO, overwrite);
        
        try {
            executeTransactionalCommand(insertGroupCommand);
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertGroup()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertGroup()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
        
        logger.info("End of QueryManagerImpl.doInsertGroup()");
    }
    
    @Override
    public LinkNodeTO doInsertLinkNode(final LinkNodeTO linkNodeTO,  
            final Boolean overwriteData) throws RemoteException {
        
        logger.info("Into QueryManagerImpl.doInsertLinkNode()");
        
        IQueryManagerCommand createLinkNodeCommand = VospaceQueryManagerCommandFactory
                .createLinkNodeCommand(linkNodeTO, overwriteData);
        
        try {
            executeTransactionalCommand(createLinkNodeCommand);
            
            logger.info("End of QueryManagerImpl.doInsertLinkNode()");
            
            return (LinkNodeTO) createLinkNodeCommand.getResult();
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertLinkNode()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertLinkNode()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }        
    }

    @Override
    public ContainerNodeTO doInsertContainerNode(final ContainerNodeTO containerNodeTO, 
            final Boolean overwrite) throws RemoteException {
        
        logger.info("Into QueryManagerImpl.doInsertContainerNode()");
        
        IQueryManagerCommand createContainerNodeCommand = VospaceQueryManagerCommandFactory
                .createContainerNodeCommand(containerNodeTO, overwrite);
        
        try {
            executeTransactionalCommand(createContainerNodeCommand);
            
            logger.info("End of QueryManagerImpl.doInsertContainerNode()");
            
            return (ContainerNodeTO) createContainerNodeCommand.getResult();
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertContainerNode()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertContainerNode()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }
    
    @Override
    public StructuredDataNodeTO doInsertStructuredDataNode(final StructuredDataNodeTO structuredDataNodeTO, 
    		final Boolean overwriteData) throws RemoteException {
        
        logger.info("Into QueryManagerImpl.doInsertStructuredDataNode()");
        
        IQueryManagerCommand createStructuredDataNodeCommand = VospaceQueryManagerCommandFactory
        		.createStructuredDataNodeCommand(structuredDataNodeTO, overwriteData);
        
        try {
            executeTransactionalCommand(createStructuredDataNodeCommand);
            logger.info("End of QueryManagerImpl.doInsertStructuredDataNode()");
            return (StructuredDataNodeTO) createStructuredDataNodeCommand.getResult();
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertStructuredDataNode()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertStructuredDataNode()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }        
    }
    
    @Override
    public UnstructuredDataNodeTO doInsertUnstructuredDataNode(final UnstructuredDataNodeTO unstructuredDataNodeTO, 
    		final Boolean overwriteData) throws RemoteException {
        
        logger.info("Into QueryManagerImpl.doInsertUnstructuredDataNode()");
        
        IQueryManagerCommand createUnstructuredDataNodeCommand = VospaceQueryManagerCommandFactory
        		.createUnstructuredDataNodeCommand(unstructuredDataNodeTO, overwriteData);
        
        try {
            executeTransactionalCommand(createUnstructuredDataNodeCommand);
            logger.info("End of QueryManagerImpl.doInsertUnstructuredDataNode()");
            return (UnstructuredDataNodeTO) createUnstructuredDataNodeCommand.getResult();
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertUnstructuredDataNode()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertUnstructuredDataNode()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public NodeTO doQueryNode(final VOSpaceURI nodeURI, final Boolean retrieval) 
            throws RemoteException {
        logger.info("Into QueryManagerImpl.doQueryNode() -> " + nodeURI.toString());
        
        IQueryManagerCommand queryNodeCommand = VospaceQueryManagerCommandFactory
                .queryNodeCommand(nodeURI, retrieval);
        
        try {
            executeTransactionalCommand(queryNodeCommand);            
            NodeTO node = (NodeTO) queryNodeCommand.getResult();            
            logger.info("End of QueryManagerImpl.doQueryNode() -> " + nodeURI.toString());            
            return node;
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doQueryNode()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doQueryNode()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public List<VoPropertyTO> doQueryVoProperties(String propertyType)
            throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doQueryVoProperties()");
        
        IQueryManagerCommand queryVoPropertyCommand = VospaceQueryManagerCommandFactory
                .queryVoPropertyCommand(propertyType);
        
        try {
            executeTransactionalCommand(queryVoPropertyCommand);
            
            @SuppressWarnings("unchecked")
            List<VoPropertyTO> properties = 
                        (List<VoPropertyTO>) queryVoPropertyCommand.getResult();
            
            logger.info("End of VospaceQueryManagerImpl.doQueryVoProperties()");
            return properties;
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in VospaceQueryManagerImpl.doQueryVoProperties()", e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in VospaceQueryManagerImpl."
                    + "doQueryVoProperties()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }
    
    @Override
    public List<VoProtocolTO> doQueryVoProtocols(String protocolType)
            throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doQueryVoProtocols()");
        
        IQueryManagerCommand queryVoProtocolCommand = VospaceQueryManagerCommandFactory
                .queryVoProtocolCommand(protocolType);
        
        try {
            executeTransactionalCommand(queryVoProtocolCommand);
            
            @SuppressWarnings("unchecked")
            List<VoProtocolTO> voProtocolTOList = 
                    (List<VoProtocolTO>) queryVoProtocolCommand.getResult();
            
            logger.info("End of VospaceQueryManagerImpl.doQueryVoProtocols()");
            return voProtocolTOList;
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in VospaceQueryManagerImpl.doQueryVoProtocols()", e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in VospaceQueryManagerImpl."
                    + "doQueryVoProtocols()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }
    
    public List<VoViewTO> doQueryVoViews(String viewType)
            throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doQueryVoViews()");
        
        IQueryManagerCommand queryVoViewCommand = VospaceQueryManagerCommandFactory
                .queryVoViewCommand(viewType);
        
        try {
            executeTransactionalCommand(queryVoViewCommand);
            
            @SuppressWarnings("unchecked")
            List<VoViewTO> voViewTOList = 
                        (List<VoViewTO>) queryVoViewCommand.getResult();
            
            logger.info("End of VospaceQueryManagerImpl.doQueryVoViews()");
            return voViewTOList;
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in VospaceQueryManagerImpl.doQueryVoViews()", e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in VospaceQueryManagerImpl."
                    + "doQueryVoViews()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }
    
    @Override
    public UserTO doQueryUser(final String userName) throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doQueryUser()");
        
        IQueryManagerCommand queryUserCommand = VospaceQueryManagerCommandFactory
                .queryUserCommand(userName);
        
        try {
            executeTransactionalCommand(queryUserCommand);
            
            logger.info("End of VospaceQueryManagerImpl.doQueryUser()");
            return (UserTO) queryUserCommand.getResult();
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doQueryUser()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doQueryUser()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }
    
    
    
    @Override
    public void doRemoveNode(final VOSpaceURI uri) throws RemoteException {
        logger.info("Into QueryManagerImpl.doRemoveNode()");
        
        IQueryManagerCommand removeNodeCommand = VospaceQueryManagerCommandFactory
                .removeNodeCommand(uri);
        
        try {
            executeTransactionalCommand(removeNodeCommand);
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doRemoveNode()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doRemoveNode()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
        
        logger.info("End of QueryManagerImpl.doRemoveNode()");
        
    }
    
    @Override
    public void doRemoveGroup(final String groupName, final String managerName) throws RemoteException {
        logger.info("Into QueryManagerImpl.doRemoveGroup()");
        
        IQueryManagerCommand removeGroupCommand = VospaceQueryManagerCommandFactory
                .removeGroupCommand(groupName, managerName);
        
        try {
            executeTransactionalCommand(removeGroupCommand);
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doRemoveGroup()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doRemoveGroup()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
        
        logger.info("End of QueryManagerImpl.doRemoveGroup()");
    }
    
    @Override
    public NodeTO doUpdateNodeProperties(NodeTO nodeTO) throws RemoteException {
        logger.info("Into QueryManagerImpl.doUpdateNodeProperties()");
        
        IQueryManagerCommand updateNodePropertiesCommand = VospaceQueryManagerCommandFactory
                .updateNodePropertiesCommand(nodeTO);
        
        try {
            executeTransactionalCommand(updateNodePropertiesCommand);
            
            NodeTO nodeResult = (NodeTO) updateNodePropertiesCommand.getResult();
            
            logger.info("End of QueryManagerImpl.doUpdateNodeProperties()");
            return nodeResult;
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doUpdateNodeProperties()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doUpdateNodeProperties()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GroupTO> doQueryGroup(final String owner, final String name) throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doQueryGroup()");
        
        IQueryManagerCommand queryGroupCommand = VospaceQueryManagerCommandFactory
                .queryGroupCommand(owner, name);
        
        try {
            executeTransactionalCommand(queryGroupCommand);
            
            logger.info("End of VospaceQueryManagerImpl.doQueryGroup()");
            return (List<GroupTO>) queryGroupCommand.getResult();
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doQueryGroup()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doQueryGroup()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public void doRemoveUser(UserTO user) throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doRemoveUser()");
        
        IQueryManagerCommand removeUserCommand = VospaceQueryManagerCommandFactory
                .removeUserCommand(user);
        
        try {
            executeTransactionalCommand(removeUserCommand);
            
            removeUserCommand.getResult();            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doRemoveUser()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doRemoveUser()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
        
        logger.info("End of VospaceQueryManagerImpl.doRemoveUser()");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NodeTO> doQueryGroupsNodePropertyCommand(String userName,
            String propertyUri) throws RemoteException {
        
        logger.info("Into VospaceQueryManagerImpl.doQueryGroupsNodePropertyCommand()");
        
        IQueryManagerCommand query = VospaceQueryManagerCommandFactory
                .queryGroupsNodePropertyCommand(userName, propertyUri);
        
        try {
            executeTransactionalCommand(query);
            logger.info("End of VospaceQueryManagerImpl.doQueryGroupsNodePropertyCommand()");
            return (List<NodeTO>) query.getResult();            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doQueryGroupsNodePropertyCommand()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doQueryGroupsNodePropertyCommand()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NodeTO> doQueryUsersNodePropertyCommand(String userName, String propertyUri)
            throws RemoteException {
        
        logger.info("Into VospaceQueryManagerImpl.doQueryUsersNodePropertyCommand()");
        
        IQueryManagerCommand query = VospaceQueryManagerCommandFactory
                .queryUsersNodePropertyCommand(userName, propertyUri);
        
        try {
            executeTransactionalCommand(query);
            logger.info("End of VospaceQueryManagerImpl.doQueryUsersNodePropertyCommand()");
            return (List<NodeTO>) query.getResult();            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doQueryUsersNodePropertyCommand()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doQueryUsersNodePropertyCommand()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserTO> doQueryUserList() throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doQueryUserList()");
        
        IQueryManagerCommand queryUserCommand = VospaceQueryManagerCommandFactory
                .queryUserListCommand();
        
        try {
            executeTransactionalCommand(queryUserCommand);
            
            logger.info("End of VospaceQueryManagerImpl.doQueryUserList()");
            return (List<UserTO>) queryUserCommand.getResult();
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doQueryUserList()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doQueryUserList()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public Long doQueryUserQuota(String userName)
            throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doQueryUserQuota()");
        
        IQueryManagerCommand queryUserQuotaCommand = VospaceQueryManagerCommandFactory
                .queryUserQuotaCommand(userName);
        
        try {
            executeTransactionalCommand(queryUserQuotaCommand);
            
            logger.info("End of VospaceQueryManagerImpl.doQueryUserQuota()");
            return (Long) queryUserQuotaCommand.getResult();
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doQueryUserQuota()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doQueryUserQuota()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public void doInsertAccessLog(AccessLogTO logTO) throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doInsertAccessLog()");
        
        IQueryManagerCommand insertAccessLog = VospaceQueryManagerCommandFactory
                .insertAccessLogCommand(logTO);
        
        try {
            executeTransactionalCommand(insertAccessLog);
            
            logger.info("End of VospaceQueryManagerImpl.doInsertAccessLog()");
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertAccessLog()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertAccessLog()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public void doInsertDataMngLog(DataMngLogTO logTO) throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doInsertDataMngLog()");
        
        IQueryManagerCommand insertDataMngLog = VospaceQueryManagerCommandFactory
                .insertDataMngLogCommand(logTO);
        
        try {
            executeTransactionalCommand(insertDataMngLog);
            
            logger.info("End of VospaceQueryManagerImpl.doInsertDataMngLog()");
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertDataMngLog()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertDataMngLog()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }        
    }

    @Override
    public void doInsertGroupControlTO(GroupControlTO arg0)
            throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doInsertMetadataRetrievalLogTO(MetadataRetrievalLogTO logTO)
            throws RemoteException {        
        logger.info("Into VospaceQueryManagerImpl.doInsertMetadataRetrievalLogTO()");
        
        IQueryManagerCommand insertMetadataRetrievalLog = VospaceQueryManagerCommandFactory
                .insertMetadataRetrievalLogCommand(logTO);
        
        try {
            executeTransactionalCommand(insertMetadataRetrievalLog);
            
            logger.info("End of VospaceQueryManagerImpl.doInsertMetadataRetrievalLogTO()");
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertMetadataRetrievalLogTO()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertMetadataRetrievalLogTO()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
        
    }

    @Override
    public void doInsertShareLogTO(ShareLogTO logTO) throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doInsertShareLogTO()");
        
        IQueryManagerCommand insertShareLog = VospaceQueryManagerCommandFactory
                .insertShareLogCommand(logTO);
        
        try {
            executeTransactionalCommand(insertShareLog);
            
            logger.info("End of VospaceQueryManagerImpl.doInsertShareLogTO()");
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertShareLogTO()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertShareLogTO()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public void doInsertTransferLogTO(TransferLogTO logTO)
            throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doInsertTransferLogTO()");
        
        IQueryManagerCommand insertTransferLog = VospaceQueryManagerCommandFactory
                .insertTransferLogCommand(logTO);
        
        try {
            executeTransactionalCommand(insertTransferLog);
            
            logger.info("End of VospaceQueryManagerImpl.doInsertTransferLogTO()");
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertTransferLogTO()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertTransferLogTO()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public void doInsertUserControlTO(UserControlTO logTO)
            throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doInsertUserControlTO()");
        
        IQueryManagerCommand insertUserControlLog = VospaceQueryManagerCommandFactory
                .insertUserControlCommand(logTO);
        
        try {
            executeTransactionalCommand(insertUserControlLog);
            
            logger.info("End of VospaceQueryManagerImpl.doInsertUserControlTO()");
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doInsertUserControlTO()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doInsertUserControlTO()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public UserControlTO doQueryUserControlTO(String mail) throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doQueryUserControlTO()");
        
        IQueryManagerCommand queryUserControlCommand = VospaceQueryManagerCommandFactory
                .queryUserControlCommand(mail);
        
        try {
            executeTransactionalCommand(queryUserControlCommand);
            
            logger.info("End of VospaceQueryManagerImpl.doQueryUserControlTO()");
            
            return (UserControlTO) queryUserControlCommand.getResult();
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doQueryUserControlTO()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doQueryUserControlTO()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
        
    }

    @Override
    public NodeTO doUpdateNodeParent(NodeTO node) throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doUpdateNodeParent()");
        
        IQueryManagerCommand updateNodeParentCommand = VospaceQueryManagerCommandFactory
                .updateNodeParentCommand(node);
        
        try {
            executeTransactionalCommand(updateNodeParentCommand);
            
            logger.info("End of VospaceQueryManagerImpl.doUpdateNodeParent()");
            
            return (NodeTO) updateNodeParentCommand.getResult();
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doUpdateNodeParent()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doUpdateNodeParent()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public UserTO doUpdateUserQuota(String userName, long size, Boolean add) throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doUpdateUserQuota()");
        
        IQueryManagerCommand queryUserQuotaCommand = VospaceQueryManagerCommandFactory
                .updateUserQuotaCommand(userName, size, add);
        
        try {
            executeTransactionalCommand(queryUserQuotaCommand);
            
            logger.info("End of VospaceQueryManagerImpl.doUpdateUserQuota()");
            
            return (UserTO) queryUserQuotaCommand.getResult();
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doUpdateUserQuota()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doUpdateUserQuota()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public UserTO doUpdateUserQuota(String userName, long quota)
            throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doUpdateUserQuota()");
        
        IQueryManagerCommand queryUserQuotaCommand = VospaceQueryManagerCommandFactory
                .updateUserQuotaCommand(userName, quota);
        
        try {
            executeTransactionalCommand(queryUserQuotaCommand);
            
            logger.info("End of VospaceQueryManagerImpl.doUpdateUserQuota()");
            
            return (UserTO) queryUserQuotaCommand.getResult();
            
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doUpdateUserQuota()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doUpdateUserQuota()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    @Override
    public UserTO doUpdateUserQLimit(String userName, long qlimit)
            throws RemoteException {
        logger.info("Into VospaceQueryManagerImpl.doUpdateUserQLimit()");
        
        IQueryManagerCommand updateUserQLimitCommand = VospaceQueryManagerCommandFactory
                .updateUserQLimitCommand(userName, qlimit);
        
        try {
            executeTransactionalCommand(updateUserQLimitCommand);
            
            logger.info("End of VospaceQueryManagerImpl.doUpdateUserQLimit()");
            
            return (UserTO) updateUserQLimitCommand.getResult();
        
        } catch (RuntimeException e) {
            logger.error("Runtime Exception in QueryManagerImpl.doUpdateUserQLimit()",
                    e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in QueryManagerImpl."
                    + "doUpdateUserQLimit()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }
    }

    /**
     * Override to impose a Function Translator.
     * @see QueryHandlerImpl#doExecuteQuery(QueryBean)
     * @param queryBean Input query bean
     * @param userTO User on behalf of whom this query is executed.
     * @return ResultBean
     * @throws RemoteException RMI exception.
     */
    //@Override
    /*public final ResultBean doExecuteQuery(final QueryBean queryBean, final UserTO userTO)
            throws RemoteException {

        logger.info("Into QueryManagerImpl.doExecuteQuery(" + queryBean + ")");
        try {
            IQueryManagerCommand executeQueryCommand = QueryManagerCommandFactory
                    .createExecuteQueryCommand(queryBean, userTO, new QLFunctionTranslatorImpl());

            long t1 = System.currentTimeMillis();
            executeTransactionalCommand(executeQueryCommand);
            long t2 = System.currentTimeMillis();

            final double timeInSec = ((double) (t2 - t1)) / 1000;
            final DecimalFormat decimalFormatter = new DecimalFormat("##.###");
            logger.info("Query executed in " + decimalFormatter.format(timeInSec) + "s");
            logger.info("End of VospaceQueryManagerImpl.doExecuteQuery()");

            ResultBean resultBean = (ResultBean) executeQueryCommand.getResult();

            // QueryManagerUtils.printResultBean(resultBean);

            return resultBean;

        } catch (RuntimeException e) {
            logger.error("Runtime Exception in EhstQueryManagerImpl.doExecuteQuery()", e);
            throw new RuntimeArchiveNestedException(e);
        } catch (ArchiveNestedException e) {
            logger.error("Unexpected Exception in EhstQueryManagerImpl.doExecuteQuery()", e);
            throw new RuntimeArchiveNestedException("Unexpected exception", e);
        }

    }*/
}
