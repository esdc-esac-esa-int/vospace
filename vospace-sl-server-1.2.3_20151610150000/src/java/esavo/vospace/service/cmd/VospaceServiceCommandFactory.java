package esavo.vospace.service.cmd;

import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.control.AccessLogTO;
import esavo.vospace.common.model.transferobjects.control.DataMngLogTO;
import esavo.vospace.common.model.transferobjects.control.MetadataRetrievalLogTO;
import esavo.vospace.common.model.transferobjects.control.ShareLogTO;
import esavo.vospace.common.model.transferobjects.control.TransferLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.service.cmd.oper.IVospaceCommand;
import esavo.vospace.service.cmd.oper.InsertContainerCommand;
import esavo.vospace.service.cmd.oper.InsertGroupCommand;
import esavo.vospace.service.cmd.oper.InsertLinkCommand;
import esavo.vospace.service.cmd.oper.InsertStructuredDataCommand;
import esavo.vospace.service.cmd.oper.InsertUnstructuredDataCommand;
import esavo.vospace.service.cmd.oper.InsertUserCommand;
import esavo.vospace.service.cmd.oper.QueryGroupCommand;
import esavo.vospace.service.cmd.oper.QueryNodeCommand;
import esavo.vospace.service.cmd.oper.QueryNodeSharedByGroup;
import esavo.vospace.service.cmd.oper.QueryNodeSharedByUser;
import esavo.vospace.service.cmd.oper.QueryPropertiesCommand;
import esavo.vospace.service.cmd.oper.QueryProtocolsCommand;
import esavo.vospace.service.cmd.oper.QueryUserCommand;
import esavo.vospace.service.cmd.oper.QueryUserControlCommand;
import esavo.vospace.service.cmd.oper.QueryUserListCommand;
import esavo.vospace.service.cmd.oper.QueryUserQuotaCommand;
import esavo.vospace.service.cmd.oper.QueryViewsCommand;
import esavo.vospace.service.cmd.oper.RemoveGroupCommand;
import esavo.vospace.service.cmd.oper.RemoveNodeCommand;
import esavo.vospace.service.cmd.oper.RemoveUserCommand;
import esavo.vospace.service.cmd.oper.SetNodeCommand;
import esavo.vospace.service.cmd.oper.TransferNodeCommand;
import esavo.vospace.service.cmd.oper.UpdateUserQuotaCommand;
import esavo.vospace.service.cmd.oper.control.InsertAccessLogCommand;
import esavo.vospace.service.cmd.oper.control.InsertDataMngLogCommand;
import esavo.vospace.service.cmd.oper.control.InsertMetadataRetrievalLogCommand;
import esavo.vospace.service.cmd.oper.control.InsertShareLogCommand;
import esavo.vospace.service.cmd.oper.control.InsertTransferLogCommand;
import esavo.vospace.service.cmd.oper.control.InsertUserControlCommand;

public class VospaceServiceCommandFactory {

    public static IVospaceCommand queryViewsCommand (final String type) {
        return new QueryViewsCommand(type);
    }
    
    public static IVospaceCommand queryProtocolsCommand (final String type) {
        return new QueryProtocolsCommand(type);
    }
    
    public static IVospaceCommand queryPropertiesCommand (final String type) {
        return new QueryPropertiesCommand(type);
    }
    
    public static IVospaceCommand queryNodeCommand (final VOSpaceURI uriTarget,
            final String detailParam, final VOSpaceURI uriParam, final String limitParam) {
        return new QueryNodeCommand(uriTarget, detailParam, uriParam, limitParam);
    }
    
    public static IVospaceCommand queryNodeCommand (final VOSpaceURI uriTarget,
            final Boolean fullDetails) {
        return new QueryNodeCommand(uriTarget, fullDetails);
    }
    
    public static IVospaceCommand queryGroupCommand (final String manager, final String name) {
        return new QueryGroupCommand(manager, name);
    }
    
    public static IVospaceCommand queryUserCommand (final String name) {
        return new QueryUserCommand(name);
    }
    
    public static IVospaceCommand queryUserListCommand () {
        return new QueryUserListCommand();
    }
    
    public static IVospaceCommand insertLinkNodeCommand (LinkNodeTO nodeTO) {
        return new InsertLinkCommand(nodeTO);
    }
    
    public static IVospaceCommand insertContainerCommand (ContainerNodeTO nodeTO) {
        return new InsertContainerCommand(nodeTO);
    }
    
    public static IVospaceCommand insertUnstructuredDataCommand (UnstructuredDataNodeTO nodeTO) {
        return new InsertUnstructuredDataCommand(nodeTO);
    }
    
    public static IVospaceCommand insertStructuredDataCommand (StructuredDataNodeTO nodeTO) {
        return new InsertStructuredDataCommand(nodeTO);
    }
    
    public static IVospaceCommand insertUserCommand (UserTO user) {
        return new InsertUserCommand(user);
    }
    
    public static IVospaceCommand insertUserControlCommand (UserControlTO user) {
        return new InsertUserControlCommand(user);
    }
    
    public static IVospaceCommand insertUserControlCommand (UserTO user) {
        return new InsertUserControlCommand(user);
    }
    
    public static IVospaceCommand insertGroupCommand (GroupTO group) {
        return new InsertGroupCommand(group);
    }
    
    public static IVospaceCommand removeNodeCommand (NodeTO node) {
        return new RemoveNodeCommand(node);
    }
    
    public static IVospaceCommand removeUserCommand (UserTO userTO) {
        return new RemoveUserCommand(userTO);
    }
    
    public static IVospaceCommand removeGroupCommand (GroupTO group) {
        return new RemoveGroupCommand(group);
    }
    
    public static IVospaceCommand setNodeCommand (final NodeTO nodeTO) {
        return new SetNodeCommand(nodeTO);
    }
    
    public static IVospaceCommand setNodeCommand (final NodeTO nodeTO, final Long oldLength) {
        return new SetNodeCommand(nodeTO, oldLength);
    }
    
    public static IVospaceCommand moveNodeCommand (final NodeTO source, final NodeTO target, UserTO user) {
        return new TransferNodeCommand(source, target, false, user);
    }
    
    public static IVospaceCommand copyNodeCommand (final NodeTO source, final NodeTO target, UserTO user) {
        return new TransferNodeCommand(source, target, true, user);
    }
    
    public static IVospaceCommand queryNodeSharedByUserCommand (final String user, final String propertyUri) {
        return new QueryNodeSharedByUser(user, propertyUri);
    }
    
    public static IVospaceCommand queryNodeSharedByGroupCommand (final String user, final String propertyUri) {
        return new QueryNodeSharedByGroup(user, propertyUri);
    }
    
    public static IVospaceCommand queryUserQuotaCommand (final UserTO user) {
        return new QueryUserQuotaCommand(user);
    }
    
    public static IVospaceCommand queryUserControlCommand (final String userName) {
        return new QueryUserControlCommand(userName);
    }
    
    public static IVospaceCommand insertAccessLogCommand (final AccessLogTO log) {
        return new InsertAccessLogCommand(log);
    }
    
    public static IVospaceCommand insertTransferLogCommand (final TransferLogTO log) {
        return new InsertTransferLogCommand(log);
    }
    
    public static IVospaceCommand insertDataMngLogCommand (final DataMngLogTO log) {
        return new InsertDataMngLogCommand(log);
    }
    
    public static IVospaceCommand insertMetadataLogCommand (final MetadataRetrievalLogTO log, final Boolean overwrite) {
        return new InsertMetadataRetrievalLogCommand(log, overwrite);
    }
    
    public static IVospaceCommand insertShareLogCommand (final ShareLogTO log) {
        return new InsertShareLogCommand(log);
    }
    
    public static IVospaceCommand transferNodeCommand (final NodeTO source, final NodeTO target, final Boolean typeTransfer, UserTO user) {
        return new TransferNodeCommand(source, target, typeTransfer, user);
    }
    
    public static IVospaceCommand updateUserQuota (final String userName) {
        return new UpdateUserQuotaCommand(userName);
    }
    
    public static IVospaceCommand updateUserQuota (final String userName, final Long size, final Boolean add) {
        return new UpdateUserQuotaCommand(userName, size, add);
    }
}
