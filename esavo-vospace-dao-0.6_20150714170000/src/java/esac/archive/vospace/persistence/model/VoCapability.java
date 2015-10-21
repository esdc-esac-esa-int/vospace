package esac.archive.vospace.persistence.model;
// Generated Mar 29, 2014 6:27:54 PM by Hibernate Tools 3.3.0.GA

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * VoCapability generated by hbm2java
 */
@Entity
@Table(name="vo_capability"
    ,schema="vos"
    , uniqueConstraints = @UniqueConstraint(columnNames="uri") 
)
public class VoCapability  implements java.io.Serializable {


     private Integer voCapabilityOid;
     private String uri;
     private String endPoint;
     
     private Set<Node> nodes = new HashSet<Node>(0);
     private Set<CapabilityParam> capabilityParams = new HashSet<CapabilityParam>(0);

    public VoCapability() {
    }

	
    public VoCapability(String uri, String endPoint) {
        this.uri = uri;
        this.endPoint = endPoint;
    }
    /*public VoCapability(String uri, String endPoint, String param, Set<NodeCapability> nodeCapabilities) {
       this.uri = uri;
       this.endPoint = endPoint;
       this.param = param;
       this.nodeCapabilities = nodeCapabilities;
    }*/
   
    @SequenceGenerator(name="generator", sequenceName="vo_capability_vo_capability_oid_seq")
    @Id 
    @GeneratedValue(strategy=SEQUENCE, generator="generator")
    @Column(name="vo_capability_oid", unique=true, nullable=false)
    public Integer getVoCapabilityOid() {
        return this.voCapabilityOid;
    }
    
    public void setVoCapabilityOid(Integer voCapabilityOid) {
        this.voCapabilityOid = voCapabilityOid;
    }

    
    @Column(name="uri", unique=true, nullable=false)
    public String getUri() {
        return this.uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }

    
    @Column(name="end_point", nullable=false)
    public String getEndPoint() {
        return this.endPoint;
    }
    
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="voCapability")
    public Set<CapabilityParam> getCapabilityParams() {
        return this.capabilityParams;
    }
    
    public void setCapabilityParams(Set<CapabilityParam> capabilityParams) {
        this.capabilityParams = capabilityParams;
    }
    
    @ManyToMany(fetch=FetchType.LAZY, mappedBy="nodeCapabilities")
    public Set<Node> getNodes() {
        return this.nodes;
    }
    
    public void setNodes(Set<Node> nodeCapabilities) {
        this.nodes = nodeCapabilities;
    }

}

