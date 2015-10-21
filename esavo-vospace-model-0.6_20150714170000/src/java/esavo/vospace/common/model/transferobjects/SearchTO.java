package esavo.vospace.common.model.transferobjects;

public class SearchTO {

    public enum Detail {min, max, properties};
    
    private VOSpaceURI uri;
    private int limit;
    private String matches;
    private VOSpaceURI node;
    private Detail detail = Detail.properties;

    public Detail getDetail() {
      return detail;
    }
    
    public int getLimit() {
      return limit;
    }

    public String getMatches() {
      return matches;
    }
    
    public VOSpaceURI getNode() {
      return node;
    }
    
    public VOSpaceURI getURI() {
      return uri;
    }

    public void setDetail(Detail detail) {
      this.detail = detail;
    }
    
    public void setDetail(String detail) throws IllegalArgumentException {
      if(detail.equals(Detail.min.toString())) {
        setDetail(Detail.min);
      } else if(detail.equals(Detail.max.toString())) {
        setDetail(Detail.max);
      } else if(detail.equals(Detail.properties.toString())) {
        setDetail(Detail.properties);
      } else {
        throw new IllegalArgumentException("Detail '" + detail + "' is not valid");     
      }
    }

    public void setLimit(int limit) {
      this.limit = limit;
    }

    public void setMatches(String matches) {
      this.matches = matches;
    }

    public void setNode(VOSpaceURI node) {
      this.node = node;
    }  
    
    public void setURI(VOSpaceURI uri) {
      this.uri = uri;
    }
}
