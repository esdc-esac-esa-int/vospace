package esavo.vospace.ifsd.model.actions;

import java.rmi.RemoteException;
import java.util.List;

import esac.archive.absi.interfaces.ifsd.model.IQueryManagerIfsd;
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

/**
 * Interface for Query Manager methods. It implements Remoting so that this module can implement RMI
 * access.
 * @author Sara Nieto
 */
public interface IQueryManagerVospace extends IQueryManagerIfsd {

    
    /**
     * Insert a user
     * @param userTO
     * @throws RemoteException
     */
    void doInsertUser(final UserTO userTO) throws RemoteException;
    
    /**
     * Insert a group
     * @param groupTO
     * @param overwrite
     * @throws RemoteException
     */
    void doInsertGroup(final GroupTO groupTO, final Boolean overwrite) throws RemoteException;
    
    /**
     * Remove a group by its name and user manager
     * @param groupName
     * @param manager
     * @throws RemoteException
     */
    void doRemoveGroup(final String groupName, final String manager) throws RemoteException;
    
    /**
     * Remove User from Vospace service
     * @param user
     * @throws RemoteException
     */
    void doRemoveUser(final UserTO user) throws RemoteException;
    
    /**
     * Insert a LinkNode
     * @param linkNodeTO
     * @param parentPath
     * @param inputCapabilityIds
     * @param inputProperties
     * @param overwriteData
     * @throws RemoteException
     */
    LinkNodeTO doInsertLinkNode(final LinkNodeTO linkNodeTO, final Boolean overwriteData)
                    throws RemoteException;
    
    /**
     * Insert a ContainerNode
     * @param containerNodeTO
     * @param parentUriPath
     * @param inputCapabilityIds
     * @param inputProperties
     * @param inputViewId
     * @param overwrite
     * @throws RemoteException
     */
    ContainerNodeTO doInsertContainerNode(final ContainerNodeTO containerNodeTO, final Boolean overwrite) 
                    throws RemoteException;
    
    /**
     * Insert a StructuredDataNode
     * @param structuredDataNodeTO
     * @param parentPath
     * @param inputProperties
     * @param overwriteData
     * @throws RemoteException
     */
    StructuredDataNodeTO doInsertStructuredDataNode(final StructuredDataNodeTO structuredDataNodeTO,
            final Boolean overwriteData) throws RemoteException;
    
    /**
     * Insert an UnstructureDataNode
     * @param unstructuredDataNodeTO
     * @param parentPath
     * @param inputCapabilities
     * @param inputProperties
     * @param inputViews
     * @param overwriteData
     * @throws RemoteException
     */
    UnstructuredDataNodeTO doInsertUnstructuredDataNode(final UnstructuredDataNodeTO unstructuredDataNodeTO,
            final Boolean overwriteData) throws RemoteException;

    /**
     * Remove a Node from the service
     * @param uriPath
     * @throws RemoteException
     */
    void doRemoveNode(final VOSpaceURI uri) throws RemoteException;
    
    /**
     * Retrieve the list of VoPropertyTO with the specific type
     * @param propertiesType
     * @return List<VoPropertyTO>
     * @throws RemoteException
     */
    List<VoPropertyTO> doQueryVoProperties(final String propertiesType) throws RemoteException;
    
    /**
     * Retrieve the list of VoProtocolTO with the specific type
     * @param protocolType
     * @return List<VoProtocolTO>
     * @throws RemoteException
     */
    List<VoProtocolTO> doQueryVoProtocols(final String protocolType) throws RemoteException;
    
    /**
     * Retrieve the list of VoViews with the specific type
     * @param viewType
     * @throws RemoteException
     */
    List<VoViewTO> doQueryVoViews(final String viewType) throws RemoteException;
    
    /**
     * Retrieve the NodeTO by its URI
     * @param nodeUri
     * @return NodeTO
     * @throws RemoteException
     */
    NodeTO doQueryNode(final VOSpaceURI nodeURI, final Boolean retrieval) throws RemoteException;
    
    /**
     * Retrieve a User by its name.
     * @param userName
     * @return UserTO
     * @throws RemoteException
     */
    UserTO doQueryUser(final String userName) throws RemoteException;
    
    /**
     * Retrieve the list of all User of the system.
     * @return List<UserTO>
     * @throws RemoteException
     */
    List<UserTO> doQueryUserList() throws RemoteException;
    
    /**
     * Clean QueryManager cache.
     * @throws RemoteException RMI exception.
     */
    void doRestartQueryManagerCache() throws RemoteException;
    
    /**
     * Set the NodeProperty list of the given NodeTO
     * @param nodeTO
     * @return the updated node
     * @throws RemoteException
     */
    NodeTO doUpdateNodeProperties(NodeTO nodeTO) throws RemoteException;
    
    /**
     * Query a group by its name and owner.
     * @param owner
     * @param groupName
     * @return
     * @throws RemoteException
     */
    List<GroupTO> doQueryGroup(String owner, String groupName) throws RemoteException;
    
    /**
     * Query the list of nodes shared with a given user directly.
     * @param userName
     * @param propertyUri, read or write.
     * @return List of NodeTO
     * @throws RemoteException
     */
    List<NodeTO> doQueryGroupsNodePropertyCommand(String userName, String propertyUri) throws RemoteException;
    
    /**
     * Query the list of nodes shared with a given user as member of a group.
     * @param userName
     * @param propertyUri, read or write.
     * @return List of NodeTO
     * @throws RemoteException
     */
    List<NodeTO> doQueryUsersNodePropertyCommand(String userName, String propertyUri) throws RemoteException;
    
    /**
     * Query the user current quota occupation.
     * @param user name
     * @return long with the used quota.
     * @throws RemoteException
     */
    Long doQueryUserQuota (String userName) throws RemoteException;
    
    /******************************
     *  Vospace Controls Database *
     ******************************/
    
    /**
     * Insert an AccessLog
     * @param logTO
     * @throws RemoteException
     */
    void doInsertAccessLog (AccessLogTO logTO) throws RemoteException;
    
    /**
     * Insert a DataMngLog
     * @param logTO
     * @throws RemoteException
     */
    void doInsertDataMngLog (DataMngLogTO logTO) throws RemoteException;
    
    /**
     * Insert a GroupControl
     * @param logTO
     * @throws RemoteException
     */
    void doInsertGroupControlTO (GroupControlTO logTO) throws RemoteException;
    
    /**
     * Insert a MetadataRetrievalLog
     * @param logTO
     * @throws RemoteException
     */
    void doInsertMetadataRetrievalLogTO (MetadataRetrievalLogTO logTO) throws RemoteException;
    
    /**
     * Insert a ShareLog
     * @param logTO
     * @throws RemoteException
     */
    void doInsertShareLogTO (ShareLogTO logTO) throws RemoteException;
    
    /**
     * Insert a TransferLog
     * @param logTO
     * @throws RemoteException
     */
    void doInsertTransferLogTO (TransferLogTO logTO) throws RemoteException;
    
    /**
     * Insert UserControl
     * @param logTO
     * @throws RemoteException
     */
    void doInsertUserControlTO (UserControlTO logTO) throws RemoteException;
    
    /**
     * Insert UserControl
     * @param mail
     * @throws RemoteException
     */
    UserControlTO doQueryUserControlTO (String mail) throws RemoteException;
    
    /**
     * Update the parent of a given node
     * @param node
     * @return NodeTO
     * @throws RemoteException
     */
    NodeTO doUpdateNodeParent (NodeTO node) throws RemoteException;
    
    /**
     * Update user quota with the given size.
     * @param user name
     * @param size
     * @param add or subtract
     * @return UserTO
     * @throws RemoteException
     */
    UserTO doUpdateUserQuota(String userName, long size, Boolean add) throws RemoteException;
    
    /**
     * Update user quota with the given value.
     * @param user name
     * @param quota value
     * @return UserTO
     * @throws RemoteException
     */
    UserTO doUpdateUserQuota(String userName, long quota) throws RemoteException;

}
