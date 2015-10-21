package esavo.vospace.common.model.transferobjects;

public enum VOSpaceNodeType {

    UNKNOWN("Unknown"),  
    UNSTRUCTURED_DATA_NODE("UnstructuredDataNode"),
    STRUCTURED_DATA_NODE("StructuredDataNode"),
    CONTAINER_NODE("ContainerNode"),
    LINK_NODE("LinkNode");
    
    private String type;
    
    private VOSpaceNodeType(String type) {
      this.type=type;
    }
    
    public static VOSpaceNodeType toObject(String s) {
      if(UNSTRUCTURED_DATA_NODE.toString().equals(s)) {
        return UNSTRUCTURED_DATA_NODE;
      } else if(STRUCTURED_DATA_NODE.toString().equals(s)) {
        return STRUCTURED_DATA_NODE;
      } else if(CONTAINER_NODE.toString().equals(s)) {
        return CONTAINER_NODE;
      } else if(LINK_NODE.toString().equals(s)) {
        return LINK_NODE;
      } else {
        return UNKNOWN;
      }
    }
    
    @Override
    public String toString() {
      if(this.type == null) {
        return super.toString();
      } else {
        return this.type;
      }
    }
}
