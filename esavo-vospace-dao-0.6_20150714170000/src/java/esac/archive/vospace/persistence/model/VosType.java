package esac.archive.vospace.persistence.model;

/**
 * Represents the type of VoView: Accept or Provide.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public enum VosType{
    ACCEPT, PROVIDE, CONTAIN;
    
    /*accept, provide, contain;*/
    /*accept("accept"), 
    provide("provide");
    
    private String type;
    
    private VosType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
      if(this.type == null) {
        return super.toString();
      } else {
        return this.type;
      }
    }*/
}
