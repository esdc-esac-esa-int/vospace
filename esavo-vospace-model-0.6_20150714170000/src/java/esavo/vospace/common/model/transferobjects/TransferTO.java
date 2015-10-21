package esavo.vospace.common.model.transferobjects;

public class TransferTO {
    private VOSpaceURI target;
    private VoViewTO view;
    private VoProtocolTO protocol;
    private boolean keepBytes = false;
    private String direction;
    
    public enum Direction { 
      PUSH_TO_VOSPACE("pushToVoSpace"), 
      PUSH_FROM_VOSPACE("pushFromVoSpace"), 
      PULL_TO_VOSPACE("pullToVoSpace"), 
      PULL_FROM_VOSPACE("pullFromVoSpace");
      
      private String s;
      Direction(String s) {
        this.s = s;
      }
      @Override
      public String toString(){
        return this.s;
      }
    }
    
    public String getDirection() {
      return direction;
    }
    
    public VoProtocolTO getProtocol() {
      return protocol;
    }
    
    public VOSpaceURI getTarget() {
      return this.target;
    }

    public VoViewTO getView() {
      return this.view;
    }

    public boolean isKeepBytes() {
      return keepBytes;
    }
    
    public void setDirection(VOSpaceURI direction) {
      this.direction = direction.toString();
    }
    
    public void setDirection(Direction direction) {
      this.direction = direction.toString();
    } 
    
    public void setDirection(String direction) throws IllegalArgumentException {
      try { // check whether it is a URI
        setDirection(new VOSpaceURI(direction));
      } catch (IllegalArgumentException e) { // or a valid direction
        if(Direction.PUSH_FROM_VOSPACE.toString().equals(direction)) {
          setDirection(Direction.PUSH_FROM_VOSPACE);
        } else if(Direction.PUSH_TO_VOSPACE.toString().equals(direction)) {
          setDirection(Direction.PUSH_TO_VOSPACE);
        } else if(Direction.PULL_FROM_VOSPACE.toString().equals(direction)) {
          setDirection(Direction.PULL_FROM_VOSPACE);
        } else if(Direction.PULL_TO_VOSPACE.toString().equals(direction)) {
          setDirection(Direction.PULL_TO_VOSPACE);
        } else {
          throw new IllegalArgumentException("Direction " + direction
              + " is not valid");
        }        
      }
    }
    
    public void setKeepBytes(boolean keepBytes) {
      this.keepBytes = keepBytes;
    }  
    
    public void setProtocol(VoProtocolTO protocol) {
      this.protocol = protocol;
    }

    public void setTarget(VOSpaceURI target) {
      this.target = target;
    }
    
    public void setView(VoViewTO view) {
      this.view = view;
    }
}
