package esac.archive.vospace.persistence.model.controls;
// Generated Sep 11, 2014 5:28:54 PM by Hibernate Tools 3.3.0.GA


import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Group generated by hbm2java
 */
@Entity
@Table(name="group"
    ,schema="controls"
)
public class Group  implements java.io.Serializable {


     private Integer groupOid;
     private User user;
     private String name;
     private Date creationDate;
     private Date deletionDate;

    public Group() {
    }

	
    public Group(Integer groupOid, User user, String name, Date creationDate) {
        this.groupOid = groupOid;
        this.user = user;
        this.name = name;
        this.creationDate = creationDate;
    }
    public Group(Integer groupOid, User user, String name, Date creationDate, Date deletionDate) {
       this.groupOid = groupOid;
       this.user = user;
       this.name = name;
       this.creationDate = creationDate;
       this.deletionDate = deletionDate;
    }
   
    @SequenceGenerator(name="generator", sequenceName="controls.group_group_oid_seq")
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="generator")
    @Column(name="group_oid", unique=true, nullable=false)
    public Integer getGroupOid() {
        return this.groupOid;
    }
    
    public void setGroupOid(Integer groupOid) {
        this.groupOid = groupOid;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    
    @Column(name="name", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_date", nullable=false, length=29)
    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deletion_date", length=29)
    public Date getDeletionDate() {
        return this.deletionDate;
    }
    
    public void setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
    }




}


