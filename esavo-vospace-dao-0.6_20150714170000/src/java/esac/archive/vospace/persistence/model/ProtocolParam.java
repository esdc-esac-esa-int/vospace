package esac.archive.vospace.persistence.model;
// Generated Apr 7, 2014 5:34:20 PM by Hibernate Tools 3.3.0.GA


import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ProtocolParam generated by hbm2java
 */
@Entity
@Table(name="protocol_param"
    ,schema="vos"
)
public class ProtocolParam  implements java.io.Serializable {


     private Integer protocolParamOid;
     private VoProtocol voProtocol;
     private String name;
     private String value;

    public ProtocolParam() {
    }

    public ProtocolParam(Integer protocolParamOid, VoProtocol voProtocol, String name, String value) {
       this.protocolParamOid = protocolParamOid;
       this.voProtocol = voProtocol;
       this.name = name;
       this.value = value;
    }
   
    @SequenceGenerator(name="generator", sequenceName="protocol_param_protocol_param_oid_seq")
    @Id 
    @GeneratedValue(strategy=SEQUENCE, generator="generator")
    @Column(name="protocol_param_oid", unique=true, nullable=false)
    public Integer getProtocolParamOid() {
        return this.protocolParamOid;
    }
    
    public void setProtocolParamOid(Integer protocolParamOid) {
        this.protocolParamOid = protocolParamOid;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="vo_protocol_id", nullable=false)
    public VoProtocol getVoProtocol() {
        return this.voProtocol;
    }
    
    public void setVoProtocol(VoProtocol voProtocol) {
        this.voProtocol = voProtocol;
    }

    
    @Column(name="name", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="value", nullable=false)
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }




}


