package esavo.vospace.service.cmd.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

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
import esavo.vospace.common.model.transferobjects.control.DataMngLogTO;
import esavo.vospace.common.model.transferobjects.control.MetadataRetrievalLogTO;
import esavo.vospace.common.model.transferobjects.control.ShareLogTO;
import esavo.vospace.common.model.transferobjects.control.TransferLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.common.model.transferobjects.control.AccessLogTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.VospaceServiceCommandFactory;
import esavo.vospace.service.cmd.oper.IVospaceCommand;

/**
 * Implementation of VOSpace Server commands.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class VospaceServiceImpl { 

    /** Logger. */
    private static Logger logger = Logger.getLogger(VospaceServiceImpl.class);
        
    public List<VoViewTO> doQueryViews(final String type) 
            throws VOSpaceException {
        
        logger.info("Into VospaceServiceImpl.doQueryViews()");
        
        IVospaceCommand queryViewsCommand = VospaceServiceCommandFactory
                .queryViewsCommand(type);
        
        queryViewsCommand.execute();
        
        @SuppressWarnings("unchecked")
        List<VoViewTO> viewTOList = (List<VoViewTO>) queryViewsCommand.getResult();
        
        if (viewTOList == null) {
            viewTOList = new ArrayList<VoViewTO>();
        }
        
        logger.info("End of VospaceServiceImpl.doQueryViews()");
        
        return viewTOList;
    }
    
    public List<VoPropertyTO> doQueryProperties(final String type) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryProperties()");
        
        IVospaceCommand queryPropertiesCommand = VospaceServiceCommandFactory
                .queryPropertiesCommand(type);
        
        queryPropertiesCommand.execute();
        
        @SuppressWarnings("unchecked")
        List<VoPropertyTO> propertyTOList = (List<VoPropertyTO>) queryPropertiesCommand.getResult();
        
        if (propertyTOList == null) {
            propertyTOList = new ArrayList<VoPropertyTO>();
        }
        
        logger.info("Size: " + propertyTOList.size());
        logger.info("End of VospaceServiceImpl.doQueryProperties()");
        
        return propertyTOList;
    }
    
    public List<VoProtocolTO> doQueryProtocols(final String type) 
        throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryProtocols()");
        
        IVospaceCommand queryProtocolCommand = VospaceServiceCommandFactory
                .queryProtocolsCommand(type);
        
        queryProtocolCommand.execute();
        
        @SuppressWarnings("unchecked")
        List<VoProtocolTO> protocolTOList = 
                (List<VoProtocolTO>) queryProtocolCommand.getResult();
        
        if (protocolTOList == null) {
            protocolTOList = new ArrayList<VoProtocolTO>();
        }
        
        logger.info("End of VospaceServiceImpl.doQueryProtocols()");
        
        return protocolTOList;
    }
    
    //static synchronized
    public LinkNodeTO doInsertLinkNode(LinkNodeTO nodeTO) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertLinkNode( "+nodeTO.getURI()+" )");
        
        IVospaceCommand insertLinkNodeCommand = VospaceServiceCommandFactory
                .insertLinkNodeCommand(nodeTO);
        
        insertLinkNodeCommand.execute();
        
        LinkNodeTO nodeInserted = (LinkNodeTO) insertLinkNodeCommand.getResult();
        
        logger.info("End of VospaceServiceImpl.doInsertLinkNode()");
        
        return nodeInserted;
    }
    
    //static synchronized
    public ContainerNodeTO doInsertContainerNode(ContainerNodeTO nodeTO) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertContainerNode( "+nodeTO.getURI()+" )");
        
        IVospaceCommand insertContainerNodeCommand = VospaceServiceCommandFactory
                .insertContainerCommand(nodeTO);
        
        insertContainerNodeCommand.execute();
        
        ContainerNodeTO nodeInserted = (ContainerNodeTO) insertContainerNodeCommand.getResult();
        
        logger.info("End of VospaceServiceImpl.doInsertContainerNode()");
        
        return nodeInserted;
    }
    
    //static synchronized
    public UnstructuredDataNodeTO doInsertUnstructuredDataNode(UnstructuredDataNodeTO nodeTO) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertUnstructuredDataNode( "+nodeTO.getURI()+" )");
        
        IVospaceCommand insertUnstructuredDataNodeCommand = VospaceServiceCommandFactory
                .insertUnstructuredDataCommand(nodeTO);
        
        insertUnstructuredDataNodeCommand.execute();
        
        UnstructuredDataNodeTO nodeInserted = 
        		(UnstructuredDataNodeTO) insertUnstructuredDataNodeCommand.getResult();
        
        logger.info("End of VospaceServiceImpl.doInsertUnstructuredDataNode()");
        
        return nodeInserted;
    }
    
    //static synchronized
    public StructuredDataNodeTO doInsertStructuredDataNode(StructuredDataNodeTO nodeTO) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertStructuredDataNode( "+nodeTO.getURI()+" )");
        
        IVospaceCommand insertStructuredDataNodeCommand = VospaceServiceCommandFactory
                .insertStructuredDataCommand(nodeTO);
        
        insertStructuredDataNodeCommand.execute();
        
        StructuredDataNodeTO nodeInserted = 
        		(StructuredDataNodeTO) insertStructuredDataNodeCommand.getResult();
        
        logger.info("End of VospaceServiceImpl.doInsertStructuredDataNode()");
        
        return nodeInserted;
    }
    
    //static synchronized
    public void doRemoveNode(NodeTO nodeTO) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doRemoveNode( "+nodeTO.getURI()+" )");
        
        IVospaceCommand removeNodeCommand = VospaceServiceCommandFactory
                .removeNodeCommand(nodeTO);
        
        removeNodeCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doRemoveNode()");
    }
    
    //static synchronized
    public static synchronized NodeTO doSetNode(final NodeTO nodeTO) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doSetNode( "+nodeTO.getURI()+" )");
        
        /* Just for logging
        UserNodePropertyTO p = (UserNodePropertyTO) NodeUtils.retrieveNodeProperty(nodeTO, GeneralConstants.PROPERTY_USERWRITE);
        
        List<UserTO> list = p.getUserList();
        for (UserTO user : list) {
            String name = user.getName();
            logger.info("Nombre del usuario: " + name);
        }*/
        
        IVospaceCommand setNodeCommand = VospaceServiceCommandFactory
                .setNodeCommand(nodeTO);
        
        setNodeCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doSetNode()");
        
        return (NodeTO) setNodeCommand.getResult();
    }
    
    //static synchronized
    public static synchronized NodeTO doSetNode(final NodeTO nodeTO, final Long oldLength) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doSetNode( "+nodeTO.getURI()+" )");
        
        IVospaceCommand setNodeCommand = VospaceServiceCommandFactory
                .setNodeCommand(nodeTO, oldLength);
        
        setNodeCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doSetNode()");
        
        return (NodeTO) setNodeCommand.getResult();
    }
    
    public NodeTO doQueryNode(final VOSpaceURI uri, final String detailParam, 
            final VOSpaceURI uriParam, final String limitParam) 
                    throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryNode( "+uri+" )");
        
        IVospaceCommand queryNodeCommand = VospaceServiceCommandFactory
                .queryNodeCommand(uri, detailParam, uriParam, limitParam);      
        
        queryNodeCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doQueryNode()");
        
        return (NodeTO) queryNodeCommand.getResult();
    }
    
    public NodeTO doQueryNode(final VOSpaceURI uri, final Boolean fullDetails) 
                    throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryNode( "+uri+" )");
        
        IVospaceCommand queryNodeCommand = VospaceServiceCommandFactory
                .queryNodeCommand(uri, fullDetails);
        
        queryNodeCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doQueryNode()");
        
        return (NodeTO) queryNodeCommand.getResult();
    }
    
    public void doMoveNode(final NodeTO source, final NodeTO target, UserTO user)
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doMoveNode( "+source.getURI()+" to  "+target.getURI()+" )");
        
        IVospaceCommand moveNodeCommand = VospaceServiceCommandFactory
                .moveNodeCommand(source, target, user);
        
        moveNodeCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doMoveNode()");
    }
    
    public void doCopyNode(final NodeTO source, final NodeTO target, UserTO user)
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doCopyNode( "+source.getURI()+" to  "+target.getURI()+" )");
        
        IVospaceCommand moveNodeCommand = VospaceServiceCommandFactory
                .copyNodeCommand(source, target, user);
        
        moveNodeCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doCopyNode()");
    }
    
    public void doInsertUser(final UserTO user) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertUser()");
        
        IVospaceCommand createUserCommand = VospaceServiceCommandFactory
                .insertUserCommand(user);
        
        createUserCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doInsertUser()");
    }
    
    public void doInsertControlUser (final UserControlTO user) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertUserControl( "+user.getName()+" )");
        
        IVospaceCommand createUserCommand = VospaceServiceCommandFactory
                .insertUserControlCommand(user);
        
        createUserCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doInsertUser()");
    }
    
    public void doInsertControlUser (final UserTO user) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertUserControl( "+user.getName()+" )");
        
        IVospaceCommand createUserCommand = VospaceServiceCommandFactory
                .insertUserControlCommand(user);
        
        createUserCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doInsertUser()");
    }
    
    public void doRemoveUser(final UserTO user) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doRemoveUser( "+user.getName()+" )");
        
        IVospaceCommand removeUserCommand = VospaceServiceCommandFactory
                .removeUserCommand(user);
        
        removeUserCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doRemoveUser()");
    }
    
    public void doInsertGroup(final GroupTO group) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertGroup( "+group.getName()+" )");
        
        IVospaceCommand createGroupCommand = VospaceServiceCommandFactory
                .insertGroupCommand(group);
        
        createGroupCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doInsertGroup()");
    }
    
    public void doRemoveGroup(final GroupTO group) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doRemoveGroup( "+group.getName()+" )");
        
        IVospaceCommand removeGroupCommand = VospaceServiceCommandFactory
                .removeGroupCommand(group);
        
        removeGroupCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doRemoveGroup()");
    }
    
    public List<GroupTO> doQueryGroups(final String manager, final String name) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryGroups()");
        
        IVospaceCommand queryGroupsCommand = VospaceServiceCommandFactory
                .queryGroupCommand(manager, name);
        
        queryGroupsCommand.execute();
        
        @SuppressWarnings("unchecked")
        List<GroupTO> groupTOList = (List<GroupTO>) queryGroupsCommand.getResult();
        
        if (groupTOList == null) {
            groupTOList = new ArrayList<GroupTO>();
        }
        
        logger.info("End of VospaceServiceImpl.doQueryGroups()");
        return groupTOList;
    }
    
    public UserTO doQueryUser(final String name) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryUser( "+name+" )");
        
        IVospaceCommand queryUserCommand = VospaceServiceCommandFactory
                .queryUserCommand(name);
        
        queryUserCommand.execute();
        
        UserTO userTO = (UserTO) queryUserCommand.getResult();
                
        logger.info("End of VospaceServiceImpl.doQueryUser()");
        return userTO;
    }
    
    @SuppressWarnings("unchecked")
    public List<UserTO> doQueryUserList() throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryUserList()");
        
        IVospaceCommand queryUserCommand = VospaceServiceCommandFactory
                .queryUserListCommand();
        
        queryUserCommand.execute();
        
        List<UserTO> userTO = (List<UserTO>) queryUserCommand.getResult();
                
        logger.info("End of VospaceServiceImpl.doQueryUserList()");
        return userTO;
    }
    
    @SuppressWarnings("unchecked")
    public List<NodeTO> doQueryUsersNodeProperty (final String name, final String propertyUri)
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryUsersNodeProperty()");
        
        IVospaceCommand queryNodesCommand = VospaceServiceCommandFactory
                .queryNodeSharedByUserCommand(name, propertyUri);
        
        queryNodesCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doQueryUsersNodeProperty()");
        return (List<NodeTO>) queryNodesCommand.getResult();
    }
    
    @SuppressWarnings("unchecked")
    public List<NodeTO> doQueryGroupsNodeProperty (final String name, final String propertyUri)
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryGroupsNodeProperty()");
        
        IVospaceCommand queryNodesCommand = VospaceServiceCommandFactory
                .queryNodeSharedByGroupCommand(name, propertyUri);
        
        queryNodesCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doQueryGroupsNodeProperty()");
        return (List<NodeTO>) queryNodesCommand.getResult();
    }
    
    public long doQueryUserQuota (final UserTO user)
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryUserQuota( "+user.getName()+" )");
        
        IVospaceCommand queryUserQuotaCommand = VospaceServiceCommandFactory
                .queryUserQuotaCommand(user);
        
        queryUserQuotaCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doQueryUserQuota()");
        return (Long) queryUserQuotaCommand.getResult();
    }
    
    public UserControlTO doQueryUserControl (final String userName)
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doQueryUserControl( "+userName+" )");
        
        IVospaceCommand queryUserControlCommand = VospaceServiceCommandFactory
                .queryUserControlCommand(userName);
        
        queryUserControlCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doQueryUserControl()");
        return (UserControlTO) queryUserControlCommand.getResult();
    }
    
    public void doInsertAccessLog (final AccessLogTO log) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertAccessLog()");
        
        IVospaceCommand insertAccessLogCommand = VospaceServiceCommandFactory
                .insertAccessLogCommand(log);
        
        insertAccessLogCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doInsertAccessLog()");
    }
    
    public void doInsertTransferLog (final TransferLogTO log) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertTransferLog()");
        
        IVospaceCommand insertTransferLogCommand = VospaceServiceCommandFactory
                .insertTransferLogCommand(log);
        
        insertTransferLogCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doInsertTransferLog()");
    }
    
    public void doInsertDataMngLog (final DataMngLogTO log) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertDataMngLog()");
        
        IVospaceCommand insertTransferLogCommand = VospaceServiceCommandFactory
                .insertDataMngLogCommand(log);
        
        insertTransferLogCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doInsertDataMngLog()");
    }
    
    public void doInsertMetadataLog (final MetadataRetrievalLogTO log, final Boolean overwrite) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertMetadataLog()");
        
        IVospaceCommand insertMetadataLogCommand = VospaceServiceCommandFactory
                .insertMetadataLogCommand(log, overwrite);
        
        insertMetadataLogCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doInsertMetadataLog()");
    }
    
    public void doInsertShareLog (final ShareLogTO log) 
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doInsertShareLog()");
        
        IVospaceCommand insertShareLogCommand = VospaceServiceCommandFactory
                .insertShareLogCommand(log);
        
        insertShareLogCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doInsertShareLog()");
    }
    
    public void doTransferNode (final NodeTO source, final NodeTO target, 
            final Boolean typeTransfer, UserTO user) throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doTransferNode( "+source.getURI()+" to "+target.getURI()+" )");
        
        IVospaceCommand transferNodeCommand = VospaceServiceCommandFactory
                .transferNodeCommand(source, target, typeTransfer, user);
        
        transferNodeCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doTransferNode()");
    }
    
    public static synchronized UserTO doUpdateUserQuota (final String userName)
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doUpdateUserQuota( " + userName + " )");
        
        IVospaceCommand updateUserQuotaCommand = VospaceServiceCommandFactory
                .updateUserQuota(userName);
        
        updateUserQuotaCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doUpdateUserQuota()");
        
        return (UserTO) updateUserQuotaCommand.getResult();
    }
    
    public static synchronized UserTO doUpdateUserQuota (final String userName, final Long size, final Boolean add)
            throws VOSpaceException {
        logger.info("Into VospaceServiceImpl.doUpdateUserQuota( "+userName+" with Size = "+size+" )");
        
        IVospaceCommand updateUserQuotaCommand = VospaceServiceCommandFactory
                .updateUserQuota(userName, size, add);
        
        updateUserQuotaCommand.execute();
        
        logger.info("End of VospaceServiceImpl.doUpdateUserQuota()");
        
        return (UserTO) updateUserQuotaCommand.getResult();
    }
}
