package esavo.vospace.dl.querymanager.cmd;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.interfaces.dl.querymanager.cmd.QueryManagerCommandFactory;
import esac.archive.vospace.persistence.model.VoCapability;
import esavo.vospace.common.model.transferobjects.CapabilityParamTO;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.common.model.transferobjects.control.AccessLogTO;
import esavo.vospace.common.model.transferobjects.control.DataMngLogTO;
import esavo.vospace.common.model.transferobjects.control.MetadataRetrievalLogTO;
import esavo.vospace.common.model.transferobjects.control.ShareLogTO;
import esavo.vospace.common.model.transferobjects.control.TransferLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.dl.querymanager.cmd.controls.IngestAccessLogCommand;
import esavo.vospace.dl.querymanager.cmd.controls.IngestDataMngLogCommand;
import esavo.vospace.dl.querymanager.cmd.controls.IngestMetadataRetrievalLogCommand;
import esavo.vospace.dl.querymanager.cmd.controls.IngestShareLogCommand;
import esavo.vospace.dl.querymanager.cmd.controls.IngestTransferLogCommand;
import esavo.vospace.dl.querymanager.cmd.controls.IngestUserControlCommand;
import esavo.vospace.dl.querymanager.cmd.controls.QueryUserControlCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.IngestCapabilityCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.IngestContainerNodeCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.IngestGroupCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.IngestLinkNodeCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.IngestPropertyCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.IngestStructuredDataNode;
import esavo.vospace.dl.querymanager.cmd.ingestion.IngestUnstructuredDataNode;
import esavo.vospace.dl.querymanager.cmd.ingestion.IngestUserCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.IngestVoCapabilityParamCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.RemoveGroupCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.RemoveNodeCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.UpdateNodeParentCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.UpdateNodePropertiesCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.UpdateUserQLimitCommand;
import esavo.vospace.dl.querymanager.cmd.ingestion.UpdateUserQuotaCommand;
import esavo.vospace.dl.querymanager.query.QueryGroupsNodePropertyCommand;
import esavo.vospace.dl.querymanager.query.QueryNodeCommand;
import esavo.vospace.dl.querymanager.query.QueryUserCommand;
import esavo.vospace.dl.querymanager.query.QueryUserListCommand;
import esavo.vospace.dl.querymanager.query.QueryUserQuotaCommand;
import esavo.vospace.dl.querymanager.query.QueryUsersNodePropertyCommand;
import esavo.vospace.dl.querymanager.query.QueryVoPropertyCommand;
import esavo.vospace.dl.querymanager.query.QueryVoProtocolCommand;
import esavo.vospace.dl.querymanager.query.QueryVoViewCommand;
import esavo.vospace.dl.querymanager.query.QueryGroupCommand;

/**
 * Factory class that creates the instances of the proper command classes.
 * @author Sara Nieto
 */
public class VospaceQueryManagerCommandFactory extends QueryManagerCommandFactory {
   
    /**
     * Insert a User if does not exists
     * @param userTO
     * @return IQueryManagerCommand:IngestUserCommand
     */
    public static IQueryManagerCommand createUserCommand(final UserTO userTO) {
        return new IngestUserCommand(userTO);
    }
    
    /**
     * Remove a User if exists.
     * @param userTO
     * @return IQueryManagerCommand:RemoveUserCommand
     */
    public static IQueryManagerCommand removeUserCommand(final UserTO userTO) {
        return removeUserCommand(userTO);
    }
    
    /**
     * Insert a Group if does not exists
     * @param groupTO
     * @param members
     * @param overwrite
     * @return IQueryManagerCommand:IngestGroupCommand
     */
    public static IQueryManagerCommand createGroupCommand(final GroupTO groupTO, 
            final Boolean overwrite) {
        return new IngestGroupCommand(groupTO, overwrite);
    }
    
    /**
     * Insert a LinkNode if does not exists
     * @param linkNodeTO
     * @param parentUriPath
     * @param inputCapability
     * @param inputProperties
     * @param overwrite
     * @return IQueryManagerCommand:IngestLinkNodeCommand
     */
    public static IQueryManagerCommand createLinkNodeCommand(
            final LinkNodeTO linkNodeTO, final Boolean overwrite) {
        return new IngestLinkNodeCommand(linkNodeTO, overwrite);
    }
    
    /**
     * Create ContainerNode
     * @param containerNodeTO
     * @param parentUriPath
     * @param voCapabilityTOList
     * @param nodePropertyTOList
     * @param voViewTOList
     * @param overwrite
     * @return IQueryManagerCommand:IngestContainerNodeCommand
     */
    public static IQueryManagerCommand createContainerNodeCommand (
            final ContainerNodeTO containerNodeTO, final Boolean overwrite) {
        return new IngestContainerNodeCommand(containerNodeTO, overwrite);
    }
    
    /**
     * Create StructuredDataNode
     * @param structuredDataNodeTO
     * @param parentUriPath
     * @param voCapabilityTOList
     * @param nodePropertyTOList
     * @param voViewTOList
     * @param overwrite
     * @return IQueryManagerCommand:IngestStructuredDataNode
     */
    public static IQueryManagerCommand createStructuredDataNodeCommand (
    		final StructuredDataNodeTO structuredDataNodeTO, final Boolean overwrite){
        return new IngestStructuredDataNode(structuredDataNodeTO, overwrite);
    }
    
    /**
     * Create UnstruturedDataNode
     * @param unstructuredDataNodeTO
     * @param vospaceTO
     * @param parentUriPath
     * @param userTO
     * @param voCapabilityTOList
     * @param nodePropertyTOList
     * @param voViewTOList
     * @param overwrite
     * @return IQueryManagerCommand:IngestUnstructuredDataNode
     */
    public static IQueryManagerCommand createUnstructuredDataNodeCommand (
    		final UnstructuredDataNodeTO unstructuredDataNodeTO, 
            final Boolean overwrite){
        return new IngestUnstructuredDataNode(unstructuredDataNodeTO, overwrite);
    }
    
    /**
     * Create VoCapability
     * @param voCapabilityTO
     * @param overwrite
     * @return
     */
    public static IQueryManagerCommand createVoCapabilityCommand (
            final VoCapabilityTO voCapabilityTO, final Boolean overwrite) {
        return new IngestCapabilityCommand(voCapabilityTO, overwrite);
    }
    
    /**
     * Create VoCapabilityParam
     * @param capabilityParamTO
     * @param voCapability
     * @param overwrite
     * @return
     */
    public static IQueryManagerCommand createVoCapabilityParamCommand (
        final CapabilityParamTO capabilityParamTO, final VoCapability voCapability,
        final Boolean overwrite) {
            return new IngestVoCapabilityParamCommand(capabilityParamTO, voCapability, overwrite);
        }
    
    /**
     * 
     * @param voPropertyTO
     * @param overwrite
     * @return
     */
    public static IQueryManagerCommand createVoPropertyCommand (
            final VoPropertyTO voPropertyTO, final Boolean overwrite) {
        return new IngestPropertyCommand(voPropertyTO, overwrite);
    }
    
    /**
     * Retrieve all VoProperty from the service with the specific type.
     * @param propertyType
     * @return queryVoPropertyCommand
     */
    public static IQueryManagerCommand queryVoPropertyCommand(final String propertyType) {
        return new QueryVoPropertyCommand(propertyType);
    }
    
    /**
     * Retrieve all VoProtocols from the service with the specific type.
     * @param protocolType
     * @return IQueryManagerCommand:QueryVoProtocolCommand
     */
    public static IQueryManagerCommand queryVoProtocolCommand(final String protocolType) {
        return new QueryVoProtocolCommand(protocolType);
    }
    
    /**
     * Retrieve all VoViews from the service with the specific type.
     * @param viewType
     * @return IQueryManagerCommand:queryVoViewCommand
     */
    public static IQueryManagerCommand queryVoViewCommand(final String viewType) {
        return new QueryVoViewCommand(viewType);
    }
    
    /**
     * Retrieve the Node at a given uri path
     * @param nodeUriPath
     * @return Node
     */
    public static IQueryManagerCommand queryNodeCommand(final VOSpaceURI nodeURI, final Boolean retrieval) {
        return new QueryNodeCommand(nodeURI, retrieval);
    }
    
    /**
     * Remove node from its uri
     * @param nodeURI
     * @return IQueryManagerCommand:RemoveNodeCommand
     */
    public static IQueryManagerCommand removeNodeCommand(final VOSpaceURI nodeURI) {
        return new RemoveNodeCommand(nodeURI);
    }
    
    /**
     * Remove a group by its name and manager name
     * @param groupName
     * @param managerName
     * @return IQueryManagerCommand:RemoveGroupCommand
     */
    public static IQueryManagerCommand removeGroupCommand (final String groupName,
            final String managerName) {
        return new RemoveGroupCommand(groupName, managerName);
    }
    
    /**
     * Update the properties of a given Node.
     * @param nodeTO
     * @return IQueryManagerCommand:UpdateNodePropertiesCommand
     */
    public static IQueryManagerCommand updateNodePropertiesCommand (final NodeTO nodeTO) {
        return new UpdateNodePropertiesCommand(nodeTO);
    }
    
    /**
     * Query the user given its name.
     * @param userName
     * @return IQueryManagerCommand:QueryUserCommand
     */
    public static IQueryManagerCommand queryUserCommand (final String userName) {
        return new QueryUserCommand(userName);
    }

    /**
     * Query the list of all users.
     * @return IQueryManagerCommand:QueryUserListCommand
     */
    public static IQueryManagerCommand queryUserListCommand () {
        return new QueryUserListCommand();
    }
    
    /**
     * Query the group given its name and manager.
     * @param manager
     * @param name
     * @return IQueryManagerCommand:QueryGroupCommand
     */
    public static IQueryManagerCommand queryGroupCommand (final String manager, final String name) {
        return new QueryGroupCommand(manager, name);
    }
    
    /**
     * Query the list of nodes shared directly by a given user.
     * @param user
     * @param propertyUri
     * @return IQueryManagerCommand:QueryGroupsNodePropertyCommand
     */
    public static IQueryManagerCommand queryGroupsNodePropertyCommand (final String user, 
            final String propertyUri) {
        return new QueryGroupsNodePropertyCommand(user, propertyUri);
    }
    
    /**
     * Query the list of nodes shared by groups with the given user as memeber.
     * @param user
     * @param propertyUri
     * @return IQueryManagerCommand:QueryUsersNodePropertyCommand
     */
    public static IQueryManagerCommand queryUsersNodePropertyCommand (final String user, 
            final String propertyUri) {
        return new QueryUsersNodePropertyCommand(user, propertyUri);
    }
    
    /**
     * Query the quota used by the given user.  
     * @param userName
     * @param uri, the URI for #length property. 
     * @return IQueryManagerCommand:QueryUserQuotaCommand
     */
    public static IQueryManagerCommand queryUserQuotaCommand (final String userName) {
        return new QueryUserQuotaCommand(userName);
    }
    
    /**
     * Update the user quota.
     * @param userName
     * @param size to increase
     * @return IQueryManagerCommand:UpdateUserQuotaCommand
     */
    public static IQueryManagerCommand updateUserQuotaCommand (final String userName, final long size, final Boolean add) {
        return new UpdateUserQuotaCommand(userName, size, add);
    }
    
    public static IQueryManagerCommand updateUserQuotaCommand (final String userName, final long quota) {
        return new UpdateUserQuotaCommand(userName, quota);
    }
    
    /**
     * Insert access log.
     * @param logTO
     * @return IQueryManagerCommand:IngestAccessLogCommand
     */
    public static IQueryManagerCommand insertAccessLogCommand (final AccessLogTO logTO) {
        return new IngestAccessLogCommand(logTO);
    }
    
    /**
     * Insert data management log.
     * @param logTO
     * @return IQueryManagerCommand:IngestDataMngLogCommand
     */
    public static IQueryManagerCommand insertDataMngLogCommand (final DataMngLogTO logTO) {
        return new IngestDataMngLogCommand(logTO); 
    }
    
    /**
     * Insert metadata retrieval log.
     * @param logTO
     * @return IQueryManagerCommand:IngestMetadataRetrievalLogCommand
     */
    public static IQueryManagerCommand insertMetadataRetrievalLogCommand (final MetadataRetrievalLogTO logTO) {
        return new IngestMetadataRetrievalLogCommand(logTO);
    }
    
    /**
     * Insert share log.
     * @param logTO
     * @return IQueryManagerCommand:IngestShareLogCommand
     */
    public static IQueryManagerCommand insertShareLogCommand (final ShareLogTO logTO) {
        return new IngestShareLogCommand(logTO);
    }
    
    /**
     * Insert transfer log.
     * @param logTO
     * @return IQueryManagerCommand:IngestTransferLogCommand
     */
    public static IQueryManagerCommand insertTransferLogCommand (final TransferLogTO logTO) {
        return new IngestTransferLogCommand(logTO);
    }
    
    /**
     * Insert access log.
     * @param logTO
     * @return IQueryManagerCommand:IngestUserControlCommand
     */
    public static IQueryManagerCommand insertUserControlCommand (final UserControlTO logTO) {
        return new IngestUserControlCommand(logTO);
    }
    
    /**
     * Retrieve UserControl
     * @param mail
     * @return IQueryManagerCommand:QueryUserControlCommand
     */
    public static IQueryManagerCommand queryUserControlCommand (final String mail) {
        return new QueryUserControlCommand(mail);
    }
    
    /**
     * Retrieve node updated.
     * @param node
     * @return IqueryManagerCommand:QueryNodeParentCommand
     */
    public static IQueryManagerCommand updateNodeParentCommand (final NodeTO node) {
        return new UpdateNodeParentCommand(node);
    }
    
    /***************************
     * Administration Requests *
     ***************************/
    
    /**
     * Update the query limit of a given user.
     * @param userName
     * @param qlimit
     * @return IqueryManagerCommand:UpdateUserQLimitCommand
     */
    public static IQueryManagerCommand updateUserQLimitCommand (final String userName, final long qlimit) {
        return new UpdateUserQLimitCommand(userName, qlimit);
    }
    
}
