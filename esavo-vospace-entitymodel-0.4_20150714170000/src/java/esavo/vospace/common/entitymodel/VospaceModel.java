package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.controls.AbstractControlsModel;
import esavo.vospace.common.entitymodel.control.EAccessLog;
import esavo.vospace.common.entitymodel.control.EDataMngLog;
import esavo.vospace.common.entitymodel.control.EGroupControl;
import esavo.vospace.common.entitymodel.control.EMetadataRetrievalLog;
import esavo.vospace.common.entitymodel.control.EShareLog;
import esavo.vospace.common.entitymodel.control.ETransferLog;
import esavo.vospace.common.entitymodel.control.EUserControl;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class VospaceModel extends AbstractControlsModel {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 5584834558023972262L;
    
    /** Representation of capability param entity. */
    public final ECapabilityParam CAPABILITY_PARAM = ECapabilityParam.getInstance();
    
    /** Representation of ContainerNode entity. */
    public final EContainerNode CONTAINER_NODE = EContainerNode.getInstance();
    
    /** Representation of DataNode entity. */
    public final EDataNode DATA_NODE = EDataNode.getInstance();
    
    /** Representation of Group entity. */
    public final EGroup GROUP = EGroup.getInstance();
    
    /** Representation of LinkNode entity. */
    public final ELinkNode LINK_NODE = ELinkNode.getInstance();
    
    /** Representation of Node entity. */
    public final ENode NODE = ENode.getInstance();
    
    /** Representation of ProtocolParam entity. */
    public final EProtocolParam PROTOCOL_PARAM = EProtocolParam.getInstance();
    
    /** Representation of StructuredDataNode entity. */
    public final EStructuredDataNode STRUCTURED_DATA_NODE = EStructuredDataNode.getInstance();
    
    /** Representation of UnstructuredDataNode entity. */
    public final EUnstructuredDataNode UNSTRUCTURED_DATA_NODE = EUnstructuredDataNode.getInstance();
    
    /** Representation of User entity. */
    public final EUser USER = EUser.getInstance();
    
    /** Representation of ViewParam entity. */
    public final EViewParam VIEW_PARAM = EViewParam.getInstance();
    
    /** Representation of VoCapability entity. */
    public final EVoCapability VO_CAPABILITY = EVoCapability.getInstance();
    
    /** Representation of VoProperty entity. */
    public final EVoProperty VO_PROPERTY = EVoProperty.getInstance();
    
    /** Representation of VoProtocol entity. */
    public final EVoProtocol VO_PROTOCOL = EVoProtocol.getInstance();
    
    /** Representation of VoView entity. */
    public final EVoView VO_VIEW = EVoView.getInstance();
    
    /** Representation of NodeProperty entity. */
    public final ENodeProperty NODE_PROPERTY = ENodeProperty.getInstance();
    
    /** Representation of UserNodeProperty entity. */
    public final EUserNodeProperty USER_NODE_PROPERTY = EUserNodeProperty.getInstance();
    
    /** Representation of GroupNodeProperty entity. */
    public final EGroupNodeProperty GROUP_NODE_PROPERTY = EGroupNodeProperty.getInstance();
    
    
    /** VOSpace Control schema */
    
    /** Representation of AccessLog entity. */
    public final EAccessLog ACCESS_LOG =  EAccessLog.getInstance();
    
    /** Representation of DataMngLog entity. */
    public final EDataMngLog DATA_MNG_LOG =  EDataMngLog.getInstance();
    
    /** Representation of GroupControl entity. */
    public final EGroupControl GROUP_CONTROL =  EGroupControl.getInstance();
    
    /** Representation of MetadataRetrievalLog entity. */
    public final EMetadataRetrievalLog METADATA_RETRIEVAL_LOG =  EMetadataRetrievalLog.getInstance();
    
    /** Representation of ShareLog entity. */
    public final EShareLog SHARE_LOG =  EShareLog.getInstance();
    
    /** Representation of TransferLog entity. */
    public final ETransferLog TRANSFER_LOG =  ETransferLog.getInstance();
    
    /** Representation of UserControl entity. */
    public final EUserControl USER_CONTROL =  EUserControl.getInstance();
    
    /** VOSpace Control schema */
    
    
    /** Singleton. */
    private static VospaceModel instance = new VospaceModel();
    
    private VospaceModel() {}
    
    /**
     * @return the only class instance.
     */
    public static VospaceModel getInstance() {
        return instance;
    }
    
}
