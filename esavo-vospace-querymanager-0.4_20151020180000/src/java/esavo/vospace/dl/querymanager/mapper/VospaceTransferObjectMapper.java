package esavo.vospace.dl.querymanager.mapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import esac.archive.absi.interfaces.dl.querymanager.mapper.AbstractTransferObjectMapper;
import esac.archive.absi.interfaces.dl.querymanager.mapper.ITransferObjectMapper;
import esac.archive.absi.modules.common.querybean.SelectionItem;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esac.archive.vospace.persistence.model.CapabilityParam;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.DataNode;
import esac.archive.vospace.persistence.model.GroupNodeProperty;
import esac.archive.vospace.persistence.model.Groups;
import esac.archive.vospace.persistence.model.LinkNode;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.ProtocolParam;
import esac.archive.vospace.persistence.model.StructuredDataNode;
import esac.archive.vospace.persistence.model.UnstructuredDataNode;
import esac.archive.vospace.persistence.model.UserNodeProperty;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.ViewParam;
import esac.archive.vospace.persistence.model.VoCapability;
import esac.archive.vospace.persistence.model.VoProperty;
import esac.archive.vospace.persistence.model.VoProtocol;
import esac.archive.vospace.persistence.model.VoView;
import esac.archive.vospace.persistence.model.VosType;
import esac.archive.vospace.persistence.model.controls.AccessLog;
import esac.archive.vospace.persistence.model.controls.DataMngLog;
import esac.archive.vospace.persistence.model.controls.Group;
import esac.archive.vospace.persistence.model.controls.MetadataRetrievalLog;
import esac.archive.vospace.persistence.model.controls.ShareLog;
import esac.archive.vospace.persistence.model.controls.TransferLog;
import esac.archive.vospace.persistence.model.controls.User;
import esavo.vospace.common.model.transferobjects.CapabilityParamTO;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.DataNodeTO;
import esavo.vospace.common.model.transferobjects.GroupNodePropertyTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.ProtocolParamTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserNodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.ViewParamTO;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
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
 * Convenience class to map objects from different layers (for example, TO with DTO's).
 * @author Sara Nieto
 */
public class VospaceTransferObjectMapper extends AbstractTransferObjectMapper implements
        ITransferObjectMapper {

    /**
     * Commons-logging variable.
     */
    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(VospaceTransferObjectMapper.class);

    /**
     * Dozer bean mapper.
     */
    private static Mapper MAPPER = DozerBeanMapperSingletonWrapper.getInstance();

    /**
     * Returns whether this class is Vospace project specific.
     * @param persistentClass Input persistent class.
     * @return True or false.
     * */
    @Override
    public final boolean isPersistentClassProjectSpecific(final Object persistentClass) {
        
        if ((persistentClass instanceof Node)) {
            return true;
        } else if ((persistentClass instanceof LinkNode)) {
            return true;
        } else if ((persistentClass instanceof DataNode)) {
            return true;
        } else if ((persistentClass instanceof ContainerNode)) {
            return true;
        } else if ((persistentClass instanceof StructuredDataNode)) {
            return true;
        } else if ((persistentClass instanceof UnstructuredDataNode)) {
            return true;
        } else if ((persistentClass instanceof Users)) {
            return true;
        } else if ((persistentClass instanceof Groups)) {
            return true;
        } else if ((persistentClass instanceof VoProperty)) {
            return true;
        } else if ((persistentClass instanceof VoCapability)) {
            return true;
        } else if ((persistentClass instanceof VoProtocol)) {
            return true;
        } else if ((persistentClass instanceof VoView)) {
            return true;
        } else if ((persistentClass instanceof CapabilityParam)) {
            return true;
        } else if ((persistentClass instanceof ViewParam)) {
            return true;
        } else if ((persistentClass instanceof ProtocolParam)) {
            return true;
        } else if ((persistentClass instanceof NodeProperty)) {
            return true;
        } else if ((persistentClass instanceof AccessLog)) {
            return true;
        } else if ((persistentClass instanceof DataMngLog)) {
            return true;
        } else if ((persistentClass instanceof Group)) {
            return true;
        } else if ((persistentClass instanceof MetadataRetrievalLog)) {
            return true;
        } else if ((persistentClass instanceof ShareLog)) {
            return true;
        } else if ((persistentClass instanceof TransferLog)) {
            return true;
        } else if ((persistentClass instanceof User)) {
            return true;
        } else {
            return true;
        }
    }

    /**
     * Populate transfer object from persistent object.
     * @param persistentClass Persistent class
     * @return ResultBeanItem
     * @param selectionItems Items originally in the "select" of the request.
     */
    @Override
    public final ResultBeanItem populateProjectSpecificTransferObject(final Object persistentClass,
            final List<SelectionItem> selectionItems) {
        
        if ((persistentClass instanceof Node)) {
            return MAPPER.map(persistentClass, NodeTO.class);
        } else if ((persistentClass instanceof LinkNode)) {
            return MAPPER.map(persistentClass, LinkNodeTO.class);
        } else if ((persistentClass instanceof DataNode)) {
            return MAPPER.map(persistentClass, DataNodeTO.class);
        } else if ((persistentClass instanceof ContainerNode)) {
            return MAPPER.map(persistentClass, ContainerNodeTO.class);
        } else if ((persistentClass instanceof StructuredDataNode)) {
            return MAPPER.map(persistentClass, StructuredDataNodeTO.class);
        } else if ((persistentClass instanceof UnstructuredDataNode)) {
            return MAPPER.map(persistentClass, UnstructuredDataNodeTO.class);
        } else if ((persistentClass instanceof Users)) {
            return MAPPER.map(persistentClass, UserTO.class);
        } else if ((persistentClass instanceof Groups)) {
            return MAPPER.map(persistentClass, GroupTO.class);
        } else if ((persistentClass instanceof VoProperty)) {
            return MAPPER.map(persistentClass, VoPropertyTO.class);
        } else if ((persistentClass instanceof VoCapability)) {
            return MAPPER.map(persistentClass, VoCapabilityTO.class);
        } else if ((persistentClass instanceof VoProtocol)) {
            return MAPPER.map(persistentClass, VoProtocolTO.class);
        } else if ((persistentClass instanceof VoView)) {
            return MAPPER.map(persistentClass, VoViewTO.class);
        } else if ((persistentClass instanceof CapabilityParam)) {
            return MAPPER.map(persistentClass, CapabilityParamTO.class);
        } else if ((persistentClass instanceof ViewParam)) {
            return MAPPER.map(persistentClass, ViewParamTO.class);
        } else if ((persistentClass instanceof ProtocolParam)) {
            return MAPPER.map(persistentClass, ProtocolParamTO.class);
        } else if ((persistentClass instanceof NodeProperty)) {
            return MAPPER.map(persistentClass, NodePropertyTO.class);
        } else if ((persistentClass instanceof AccessLog)) {
            return MAPPER.map(persistentClass, AccessLogTO.class);
        } else if ((persistentClass instanceof DataMngLog)) {
            return MAPPER.map(persistentClass, DataMngLogTO.class);
        } else if ((persistentClass instanceof Group)) {
            return MAPPER.map(persistentClass, GroupControlTO.class);
        } else if ((persistentClass instanceof MetadataRetrievalLog)) {
            return MAPPER.map(persistentClass, MetadataRetrievalLogTO.class);
        } else if ((persistentClass instanceof ShareLog)) {
            return MAPPER.map(persistentClass, ShareLogTO.class);
        } else if ((persistentClass instanceof TransferLog)) {
            return MAPPER.map(persistentClass, TransferLogTO.class);
        } else if ((persistentClass instanceof User)) {
            return MAPPER.map(persistentClass, UserTO.class);
        } else {
            return null;
        }
    }
    
    public static NodeTO populateNodeTO (final Node node, final VOSpaceURI uri, final Boolean retrieval) {
        NodeTO nodeTO = null;        
        if (node instanceof LinkNode) {
            nodeTO = populateLinkNodeTO(((LinkNode)node), uri, retrieval);
        } else if (node instanceof ContainerNode) { 
            nodeTO = populateContainerNodeTO(((ContainerNode)node), uri);
        } else if (node instanceof StructuredDataNode) {
            nodeTO = populateStructuredDataNodeTO(((StructuredDataNode)node), uri, retrieval);
        } else if (node instanceof UnstructuredDataNode) {
            nodeTO = populateUnstructuredDataNodeTO(((UnstructuredDataNode)node), uri, retrieval);
        }
        return nodeTO;
    }
    
    public static LinkNodeTO populateLinkNodeTO (final LinkNode linkNode, final VOSpaceURI uri, 
            final Boolean retrieval) {
        if (linkNode == null) {
            return null;
        }
        
        LinkNodeTO linkNodeTO = new LinkNodeTO();
        linkNodeTO.setId(linkNode.getNodeOid());
        linkNodeTO.setUri(uri);
        UserTO userTO = populateUserTO(linkNode.getUsers());
        linkNodeTO.setUserTO(userTO);
        if (retrieval) {
            // capabilities
            List<VoCapabilityTO> capabilities = 
                    populateVoCapabilityTOList(linkNode.getNodeCapabilities());
            linkNodeTO.setCapabilities(capabilities);
            // node properties
            List<NodePropertyTO> properties = 
                    populateNodePropertyTOList(linkNode.getNodePropertySet());        
            linkNodeTO.setNodeProperties(properties);
        }
        VOSpaceURI target = new VOSpaceURI(linkNode.getTarget());
        linkNodeTO.setTarget(target);        
        
        return linkNodeTO;
    }
    
    public static ContainerNodeTO populateContainerNodeTO (final ContainerNode containerNode, 
            final VOSpaceURI uri, final Boolean children, final Boolean retrieval) {
        ContainerNodeTO containerNodeTO = populateContainerNodeTO (containerNode, uri);
        if (children) {
            // add children
            List<NodeTO> childrenList = 
                    populateContainerChildren(containerNode.getChildrens(), uri);
            containerNodeTO.setNodeList(childrenList);
        }
        
        return containerNodeTO;
    }
    
    private static ContainerNodeTO populateContainerNodeTO (final ContainerNode containerNode,
            final VOSpaceURI uri) {
        if (containerNode == null) {
            return null;
        }
        
        ContainerNodeTO containerNodeTO = new ContainerNodeTO();
        containerNodeTO.setId(containerNode.getNodeOid());
        containerNodeTO.setUri(uri);
        UserTO userTO = populateUserTO(containerNode.getUsers());
        containerNodeTO.setUserTO(userTO);
        containerNodeTO.setNodeList(new ArrayList<NodeTO>()); //empty children list
        //if (retrieval) {
            // capabilities
            List<VoCapabilityTO> capabilities = 
                    populateVoCapabilityTOList(containerNode.getNodeCapabilities());
            containerNodeTO.setCapabilities(capabilities);
            // node properties
            List<NodePropertyTO> properties = 
                    populateNodePropertyTOList(containerNode.getNodePropertySet());        
            containerNodeTO.setNodeProperties(properties);
            // views
            List<VoViewTO> acceptViews =
                    populateAcceptVoViewTOList (containerNode.getViews());
            List<VoViewTO> provideViews =
                    populateProvideVoViewTOList (containerNode.getViews());
            containerNodeTO.setAcceptViews(acceptViews);
            containerNodeTO.setProvideViews(provideViews);
        //}
        return containerNodeTO;
    }
    
    public static List<NodeTO> populateContainerChildren (final Set<Node> children, final VOSpaceURI uri) {
        if (children == null) {
            return null;
        }
        List<NodeTO> nodeList = new ArrayList<NodeTO>();
        for (Node node : children) {
            
            VOSpaceURI uriChild = new VOSpaceURI(uri + File.separator 
                    + node.getName());
            
            if (node instanceof LinkNode) {
                LinkNode link = (LinkNode) node;
                LinkNodeTO linkChild = populateLinkNodeTO(link, uriChild, true);
                nodeList.add(linkChild);
            } else if (node instanceof ContainerNode) {
                ContainerNode container = (ContainerNode) node;
                ContainerNodeTO containerTO = populateContainerNodeTO(container, uriChild);
                nodeList.add(containerTO);
            } else if (node instanceof StructuredDataNode) {
                StructuredDataNode structured = (StructuredDataNode) node;
                StructuredDataNodeTO structuredTO = populateStructuredDataNodeTO(structured, uriChild, true);
                nodeList.add(structuredTO);
            } else if (node instanceof UnstructuredDataNode) {
                UnstructuredDataNode unstructured = (UnstructuredDataNode) node;
                UnstructuredDataNodeTO unstructuredTO = populateUnstructuredDataNodeTO(unstructured, uriChild, true);
                nodeList.add(unstructuredTO);
            }            
        }
        
        return nodeList;
    }
    
    public static StructuredDataNodeTO populateStructuredDataNodeTO (final StructuredDataNode structuredDataNode,
            final VOSpaceURI uri, final Boolean retrieval) {
        if (structuredDataNode == null) {
            return null;
        }
        
        StructuredDataNodeTO structuredDataNodeTO = new StructuredDataNodeTO();
        structuredDataNodeTO.setId(structuredDataNode.getNodeOid());
        structuredDataNodeTO.setUri(uri);
        UserTO userTO = populateUserTO(structuredDataNode.getUsers());
        structuredDataNodeTO.setUserTO(userTO);
        if (retrieval) {
            // capabilities
            List<VoCapabilityTO> capabilities = 
                    populateVoCapabilityTOList(structuredDataNode.getNodeCapabilities());
            structuredDataNodeTO.setCapabilities(capabilities);
            // node properties
            List<NodePropertyTO> properties = 
                    populateNodePropertyTOList(structuredDataNode.getNodePropertySet());        
            structuredDataNodeTO.setNodeProperties(properties);
            // views
            List<VoViewTO> acceptViews =
                    populateAcceptVoViewTOList (structuredDataNode.getViews());
            List<VoViewTO> provideViews =
                    populateProvideVoViewTOList (structuredDataNode.getViews());
            structuredDataNodeTO.setAcceptViews(acceptViews);
            structuredDataNodeTO.setProvideViews(provideViews);
        }
        
        return structuredDataNodeTO;
    }
    
    public static UnstructuredDataNodeTO populateUnstructuredDataNodeTO (final UnstructuredDataNode unstructuredDataNode,
            final VOSpaceURI uri, final Boolean retrieval) {
        if (unstructuredDataNode == null) {
            return null;
        }
        
        UnstructuredDataNodeTO unstructuredDataNodeTO = new UnstructuredDataNodeTO();
        unstructuredDataNodeTO.setId(unstructuredDataNode.getNodeOid());
        unstructuredDataNodeTO.setUri(uri);
        UserTO userTO = populateUserTO(unstructuredDataNode.getUsers());
        unstructuredDataNodeTO.setUserTO(userTO);
        if (retrieval) {
            // capabilities
            List<VoCapabilityTO> capabilities = 
                    populateVoCapabilityTOList(unstructuredDataNode.getNodeCapabilities());
            unstructuredDataNodeTO.setCapabilities(capabilities);
            // node properties
            List<NodePropertyTO> properties = 
                    populateNodePropertyTOList(unstructuredDataNode.getNodePropertySet());        
            unstructuredDataNodeTO.setNodeProperties(properties);
            // views
            List<VoViewTO> acceptViews =
                    populateAcceptVoViewTOList (unstructuredDataNode.getViews());
            List<VoViewTO> provideViews =
                    populateProvideVoViewTOList (unstructuredDataNode.getViews());
            unstructuredDataNodeTO.setAcceptViews(acceptViews);
            unstructuredDataNodeTO.setProvideViews(provideViews);
        }
        
        return unstructuredDataNodeTO;
    }
    
    public static List<VoViewTO> populateAcceptVoViewTOList (final Set<VoView> voViewList) {
        if (voViewList == null) {
            return null;
        }
        
        List<VoViewTO> voViewTOList = new ArrayList<VoViewTO>();
        for (VoView voView : voViewList) {
            if (voView.getViewType() == VosType.ACCEPT) {
                VoViewTO viewTO = populateVoViewTO (voView);
                voViewTOList.add(viewTO);
            }
        }
        return voViewTOList;
    }
    
    public static List<VoViewTO> populateTypeVoViewTOList (final List<VoView> voViewList, final VosType type) {
        if (voViewList == null) {
            return null;
        }
        
        List<VoViewTO> voViewTOList = new ArrayList<VoViewTO>();
        for (VoView voView : voViewList) {
            if (voView.getViewType() == type) {
                VoViewTO viewTO = populateVoViewTO (voView);
                voViewTOList.add(viewTO);
            }
        }
        return voViewTOList;
    }
    
    public static List<VoViewTO> populateProvideVoViewTOList (final Set<VoView> voViewList) {
        if (voViewList == null) {
            return null;
        }
        
        List<VoViewTO> voViewTOList = new ArrayList<VoViewTO>();
        for (VoView voView : voViewList) {
            if (voView.getViewType() == VosType.PROVIDE) {
                VoViewTO viewTO = populateVoViewTO (voView);
                voViewTOList.add(viewTO);
            }
        }
        return voViewTOList;
    }
    
    public static VoViewTO populateVoViewTO (final VoView voView) {
        if (voView == null) {
            return null;
        }
        
        VoViewTO voViewTO = new VoViewTO();
        voViewTO.setId(voView.getVoViewOid());
        VOSpaceURI uri = new VOSpaceURI(voView.getUri());
        voViewTO.setURI(uri);
        voViewTO.setOriginal(voView.isOriginal());
        
        //Views
        List<ViewParamTO> viewParamTOList = 
                populateViewParamTOList(voView.getViewParams());        
        voViewTO.setParams(viewParamTOList);
        
        return voViewTO;
    }
    
    public static List<ViewParamTO> populateViewParamTOList (final Set<ViewParam> paramList) {
        if (paramList == null) {
            return null;
        }
        
        List<ViewParamTO> paramTOList = new ArrayList<ViewParamTO>();
        for (ViewParam viewParam : paramList) {
            ViewParamTO paramTO = populateViewParamTO(viewParam);
            paramTOList.add(paramTO);
        }
        
        return paramTOList;
    }
    
    public static ViewParamTO populateViewParamTO (final ViewParam param) {
        if (param == null) {
            return null;
        }
        
        ViewParamTO paramTO = new ViewParamTO();
        paramTO.setParamId(param.getViewParamOid());
        VOSpaceURI uri = new VOSpaceURI(param.getName());
        paramTO.setURI(uri);
        paramTO.setValue(param.getValue());
        paramTO.setViewId(param.getVoView().getVoViewOid());
        
        return paramTO;
    }
    
    public static List<NodePropertyTO> populateNodePropertyTOList (final Set<NodeProperty> nodePropertyList) {
        if (nodePropertyList == null) {
            return null;
        }
        
        List<NodePropertyTO> nodePropertyTOList = new ArrayList<NodePropertyTO>();
        for (NodeProperty nodeProperty : nodePropertyList) {
            NodePropertyTO nodePropertyTO = populateNodePropertyTO(nodeProperty);
            nodePropertyTOList.add(nodePropertyTO);
        }
        return nodePropertyTOList;
    }
    
    public static NodePropertyTO populateNodePropertyTO (final NodeProperty nodeProperty) {
        if (nodeProperty == null) {
            return null;
        }
        
        NodePropertyTO nodePropertyTO = null;        
        
        if (nodeProperty instanceof UserNodeProperty) {
            nodePropertyTO = new UserNodePropertyTO();
            List<UserTO> userList = populateUserTOList(((UserNodeProperty) nodeProperty).getShareUsers());
            ((UserNodePropertyTO) nodePropertyTO).setUserList(userList);
        } else if (nodeProperty instanceof GroupNodeProperty) {
            nodePropertyTO = new GroupNodePropertyTO();
            List<GroupTO> groupList = populateGroupTOList(((GroupNodeProperty) nodeProperty).getShareGroups());
            ((GroupNodePropertyTO) nodePropertyTO).setGroupList(groupList);
        } else {
            nodePropertyTO = new NodePropertyTO();
        }
        
        //VoPropertyTO
        VoPropertyTO voPropertyTO = populateVoPropertyTO(nodeProperty.getVoProperty());
        nodePropertyTO.setVoPropertyTO(voPropertyTO);
        String value = nodeProperty.getValue();
        nodePropertyTO.setValue(value);
        
        return nodePropertyTO;
    }
    
    public static List<VoProtocolTO> populateVoProtocolTOList (final List<VoProtocol> voProtocolList) {
        if (voProtocolList == null) {
            return null;
        }
        
        List<VoProtocolTO> voProtocolTOList = new ArrayList<VoProtocolTO>();
        for (VoProtocol voProtocol : voProtocolList) {
            VoProtocolTO protocolTO = populateVoProtocolTO(voProtocol);
            voProtocolTOList.add(protocolTO);
        }
        
        return voProtocolTOList; 
    }
    
    public static VoProtocolTO populateVoProtocolTO (final VoProtocol voProtocol) {
        if (voProtocol == null) {
            return null;
        }
        
        VoProtocolTO voProtocolTO = new VoProtocolTO();
        voProtocolTO.setURI(new VOSpaceURI(voProtocol.getUri()));
        voProtocolTO.setEndPoint(new VOSpaceURI(voProtocol.getEndPoint()));
        voProtocolTO.setId(voProtocol.getVoProtocolOid());
        
        return voProtocolTO;
    }
    
    public static List<VoPropertyTO> populateVoPropertyTOList (final List<VoProperty> voPropertyList) {
        if (voPropertyList == null) {
            return null;
        }
        
        List<VoPropertyTO> voPropertyTOList = new ArrayList<VoPropertyTO>();
        for (VoProperty voProperty : voPropertyList) {
            VoPropertyTO propertyTO = populateVoPropertyTO(voProperty);
            voPropertyTOList.add(propertyTO);
        }
        
        return voPropertyTOList; 
    }
    
    public static VoPropertyTO populateVoPropertyTO (final VoProperty voProperty) {
        if (voProperty == null) {
            return null;
        }
        
        VoPropertyTO voPropertyTO = new VoPropertyTO();
        voPropertyTO.setURI(voProperty.getUri());
        voPropertyTO.setId(voProperty.getVoPropertyOid());
        voPropertyTO.setReadOnly(voProperty.isReadonly());
        voPropertyTO.setDescription(voProperty.getDescription());
        
        return voPropertyTO;
    }
    
    public static List<VoCapabilityTO> populateVoCapabilityTOList (final Set<VoCapability> voCapabilityList) {
        if (voCapabilityList == null) {
            return null;
        }
        
        List<VoCapabilityTO> voCapabilityTOList = new ArrayList<VoCapabilityTO>();
        for (VoCapability voCapability : voCapabilityList) {
            VoCapabilityTO capabilityTO = populateVoCapabilityTO(voCapability);
            voCapabilityTOList.add(capabilityTO);
        }
        
        return voCapabilityTOList; 
    }
    
    public static VoCapabilityTO populateVoCapabilityTO (final VoCapability voCapability) {
        if (voCapability == null) {
            return null;
        }
        
        VoCapabilityTO voCapabilityTO = new VoCapabilityTO();
        VOSpaceURI capabilityURI = new VOSpaceURI(voCapability.getUri());
        voCapabilityTO.setURI(capabilityURI);
        voCapabilityTO.setCapabilityId(voCapability.getVoCapabilityOid());
        VOSpaceURI endpointURI = new VOSpaceURI(voCapability.getEndPoint());
        voCapabilityTO.setEndPoint(endpointURI);
        
        List<CapabilityParamTO> capabilityParamTOList = 
                populateCapabilityParamsTOList(voCapability.getCapabilityParams());
        voCapabilityTO.setParams(capabilityParamTOList);
        
        return voCapabilityTO;
    }
    
    private static List<CapabilityParamTO> populateCapabilityParamsTOList (Set<CapabilityParam> paramList) {
        if (paramList == null) {
            return null;
        }
        
        List<CapabilityParamTO> paramTOList = new ArrayList<CapabilityParamTO>();
        for (CapabilityParam param : paramList) {
            CapabilityParamTO paramTO = populateCapabilityParamsTO(param);
            paramTOList.add(paramTO);
        }        
        
        return paramTOList;
    }
    
    private static CapabilityParamTO populateCapabilityParamsTO (final CapabilityParam param) {
        if (param == null) {
            return null;
        }
        
        CapabilityParamTO paramTO = new CapabilityParamTO();
        paramTO.setParamId(param.getCapabilityParamOid());
        VOSpaceURI paramURI = new VOSpaceURI(param.getName());
        paramTO.setURI(paramURI);
        paramTO.setCapabilityId(param.getVoCapability().getVoCapabilityOid());
        paramTO.setValue(param.getValue());
        
        return paramTO;
    }
    
    public static List<UserTO> populateUserTOList (final Set<Users> userList) {
        if (userList == null) {
            return null;
        }
        
        List<UserTO> list = new ArrayList<UserTO>();
        for (Users user : userList) {
            UserTO userTO = populateUserTO(user);
            list.add(userTO);
        }
        return list;
    }
    
    public static UserTO populateUserTO (final Users user) {
        if (user == null) {
            return null;
        }
        
        UserTO userTO = new UserTO();
        userTO.setId(user.getUserOid());
        userTO.setName(user.getName());
        userTO.setFullName(user.getFullName());
        userTO.setMail(user.getMail());
        userTO.setQuota(user.getQuota());
        userTO.setQLimit(user.getQLimit()); // added quota limit
        userTO.setCreationDate(user.getCreationDate());
        
        return userTO;
    }
    
    public static List<GroupTO> populateGroupTOList (final Set<Groups> groupList) {
        if (groupList == null) {
            return null;
        }
        
        List<GroupTO> list = new ArrayList<GroupTO>();
        for (Groups groups : groupList) {
            GroupTO groupTO = populateGroupTO(groups);
            list.add(groupTO);
        }
        return list;
    }
    
    public static List<GroupTO> populateGroupTOList (final List<Groups> groupList) {
        if (groupList == null) {
            return null;
        }
        
        List<GroupTO> list = new ArrayList<GroupTO>();
        for (Groups groups : groupList) {
            GroupTO groupTO = populateGroupTO(groups);
            list.add(groupTO);
        }
        return list;
    }
    
    public static GroupTO populateGroupTO (final Groups group) {
        if (group == null) {
            return null;
        }
        
        GroupTO groupTO = new GroupTO();
        groupTO.setName(group.getName());
        groupTO.setDescription(group.getDescription());
        UserTO manager = populateUserTO(group.getManager());
        groupTO.setManagerTO(manager);
        groupTO.setCreationDate(group.getCreationDate());
        List<UserTO> memebers = populateUserTOList(group.getMembers());
        groupTO.setMembers(memebers);
        
        return groupTO;
    }
    
    public static AccessLogTO populateAccessLogTO (final AccessLog accessLog) {
        if (accessLog == null) {
            return null;
        }
        
        AccessLogTO logTO = new AccessLogTO();
        logTO.setEnvironment(accessLog.getEnvironment());
        logTO.setInternetSite(accessLog.getInternetsite());
        logTO.setLoginTimestamp(accessLog.getLoginTimestamp());
        logTO.setLogoutTimestamp(accessLog.getLogoutTimestamp());
        logTO.setQuota(accessLog.getQuota());
        logTO.setUserControlTO(populateUserControlTO(accessLog.getUser())); //accessLog.getUser()
        
        return logTO;
    }
    
    public static UserControlTO populateUserControlTO (final User user) {
        if (user == null) {
            return null;
        }
        
        UserControlTO userTO = new UserControlTO();
        userTO.setAddress1(user.getAddress1());
        userTO.setAddress2(user.getAddress2());
        userTO.setCountry(user.getCountry());
        userTO.setFax(user.getFax());
        userTO.setInstitute(user.getInstitute());
        userTO.setMail(user.getMail());
        userTO.setName(user.getUname());
        userTO.setPhone(user.getPhoneNumber());
        userTO.setState(user.getState());
        userTO.setSurname(user.getSurname());
        userTO.setTown(user.getTown());
        userTO.setZipCode(user.getZipCode());
        
        return userTO;
    }
    
    public static TransferLogTO populateTransferTO (final TransferLog log) {
        if (log == null) {
            return null;
        }
        
        TransferLogTO logTO = new TransferLogTO();
        logTO.setOperType(log.getOperationType().toString());
        logTO.setProtocolParam(log.getProtocolParam());
        logTO.setViewParam(log.getViewParam());
        logTO.setStartTimestamp(log.getStartTimestamp());
        logTO.setStopTimestamp(log.getStopTimestamp());
        logTO.setTotalSize(log.getTotalSize());
        logTO.setUserControlTO(populateUserControlTO(log.getUser()));
        
        return logTO;
    }

}
