package esac.archive.vospace.persistence.model;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@Embeddable
@Entity
@Table(name="node_property", schema="vos")
@Inheritance(strategy = InheritanceType.JOINED)
public class NodeProperty implements java.io.Serializable {
    
    private Integer nodePropertyOid;
    private VoProperty voProperty;
    private Node node;
    private String value;
    
    /*private Set<Users> shareUserList = new HashSet<Users>(0);
    private Set<Groups> shareGroupList = new HashSet<Groups>(0);*/
    
    public NodeProperty() {
        
    }
    
    @SequenceGenerator(name="generator", sequenceName="node_property_node_property_oid_seq")
    @Id 
    @GeneratedValue(strategy=SEQUENCE, generator="generator")   
    @Column(name="node_property_oid", unique=true, nullable=false)
    public Integer getNodePropertyOid() {
        return nodePropertyOid;
    }
    
    /**
     * @return the voProperty
     */
    /*@ManyToOne
    @JoinColumn(name="property_id",
                nullable = false,
                updatable = false)*/
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="property_id", nullable=false)
    public VoProperty getVoProperty() {
        return voProperty;
    }

    /**
     * @return the value
     */
    @Column(name="value", nullable=true)
    public String getValue() {
        return value;
    }

    /**
     * @param voProperty the voProperty to set
     */
    public void setVoProperty(VoProperty voProperty) {
        this.voProperty = voProperty;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
/*
    *//**
     * @return the users
     *//*
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_node_property", schema = "vos",
    joinColumns = { @JoinColumn(name = "node_property_id", nullable = false, updatable = false) }, 
    inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) })
    public final Set<Users> getShareUserList() {
        return shareUserList;
    }

    *//**
     * @return the groups
     *//*
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_node_property", schema = "vos",
    joinColumns = { @JoinColumn(name = "node_property_id", nullable = false, updatable = false) }, 
    inverseJoinColumns = { @JoinColumn(name = "group_id", nullable = false, updatable = false) })
    public final Set<Groups> getShareGroupList() {
        return shareGroupList;
    }

    *//**
     * @param users the users to set
     *//*
    public final void setShareUserList(Set<Users> users) {
        this.shareUserList = users;
    }

    *//**
     * @param groups the groups to set
     *//*
    public final void setShareGroupList(Set<Groups> groups) {
        this.shareGroupList = groups;
    }*/

    /**
     * Fetch type = EAGER to be able to retrieve the Node 
     * associated with the NodeProperty.
     * @return the node
     */    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="node_id", nullable=false)
    public final Node getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public final void setNode(Node node) {
        this.node = node;
    }

    /**
     * @param nodePropertyOid the nodePropertyOid to set
     */
    public final void setNodePropertyOid(Integer nodePropertyOid) {
        this.nodePropertyOid = nodePropertyOid;
    }
    
    //Add equals and hashCode methods
    
}
