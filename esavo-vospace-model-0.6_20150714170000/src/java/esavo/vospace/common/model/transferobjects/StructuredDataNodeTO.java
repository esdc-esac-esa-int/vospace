package esavo.vospace.common.model.transferobjects;

import esavo.vospace.common.entitymodel.EStructuredDataNode;
import esavo.vospace.common.entitymodel.VospaceModel;

public class StructuredDataNodeTO extends DataNodeTO {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -2798508257930958702L;

    /**
     * Associated entity of this transfer object.
     */
    private EStructuredDataNode structuredDataNode = VospaceModel.getInstance().STRUCTURED_DATA_NODE;
    
    /**
     * Default constructor
     */
    public StructuredDataNodeTO() {
        
    }
    
    @Override
    public VOSpaceNodeType getType() {
      return VOSpaceNodeType.STRUCTURED_DATA_NODE;
    }
    
}
