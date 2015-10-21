package esac.archive.vospace.persistence.model;
// Generated Mar 29, 2014 6:27:54 PM by Hibernate Tools 3.3.0.GA

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * VoProtocol generated by hbm2java
 */
@Entity
@Table(name="vo_protocol"
    ,schema="vos"
)
public class VoProtocol  implements java.io.Serializable {


     private Integer voProtocolOid;
     private String uri;
     private String endPoint;
     private VosType protocolType;
     
     private Set<ProtocolParam> protocolParams = new HashSet<ProtocolParam>(0);

    public VoProtocol() {
    }

    public VoProtocol(String uri, String endPoint) {
       this.uri = uri;
       this.endPoint = endPoint;
    }
   
    @SequenceGenerator(name="generator", sequenceName="vo_protocol_vo_protocol_oid_seq")
    @Id 
    @GeneratedValue(strategy=SEQUENCE, generator="generator")
    @Column(name="vo_protocol_oid", unique=true, nullable=false)
    public Integer getVoProtocolOid() {
        return this.voProtocolOid;
    }
    
    public void setVoProtocolOid(Integer voProtocolOid) {
        this.voProtocolOid = voProtocolOid;
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
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="voProtocol")
    public Set<ProtocolParam> getProtocolParams() {
        return this.protocolParams;
    }
    
    public void setProtocolParams(Set<ProtocolParam> protocolParams) {
        this.protocolParams = protocolParams;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="protocol_type", nullable=false)
    public VosType getProtocolType() {
        return this.protocolType;
    }
    
    public void setProtocolType(VosType protocolType) {
        this.protocolType = protocolType;
    }


}


