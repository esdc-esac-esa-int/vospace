package esavo.vospace.common.model.transferobjects;

import esavo.vospace.common.entitymodel.EUnstructuredDataNode;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class UnstructuredDataNodeTO extends DataNodeTO {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -4059588921187937883L;

    /**
     * Associated entity of this transfer object.
     */
    private EUnstructuredDataNode unstructuredDataNode = VospaceModel.getInstance().UNSTRUCTURED_DATA_NODE;
    
    /**
     * Default constructor
     */
    public UnstructuredDataNodeTO() {
        
    }
    
    @Override
    public VOSpaceNodeType getType() {
      return VOSpaceNodeType.UNSTRUCTURED_DATA_NODE;
    }
}
