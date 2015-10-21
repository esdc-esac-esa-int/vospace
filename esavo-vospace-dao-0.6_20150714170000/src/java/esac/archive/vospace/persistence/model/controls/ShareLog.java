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
 * ShareLog generated by hbm2java
 */
@Entity
@Table(name="share_log"
    ,schema="controls"
)
public class ShareLog  implements java.io.Serializable {


     private Integer shareLogOid;
     private User user;
     private Date operationTimestamp;
     private Date endTimestamp;
     private String node;

    public ShareLog() {
    }

	
    public ShareLog(Integer shareLogOid, User user, Date operationTimestamp, String node ) {
        this.shareLogOid = shareLogOid;
        this.user = user;
        this.operationTimestamp = operationTimestamp;
        this.node = node;
    }
    public ShareLog(Integer shareLogOid, User user, Date operationTimestamp, Date endTimestamp, String node) {
       this.shareLogOid = shareLogOid;
       this.user = user;
       this.operationTimestamp = operationTimestamp;
       this.endTimestamp = endTimestamp;
       this.node = node;
    }
   
    @SequenceGenerator(name="generator", sequenceName="controls.share_log_share_log_oid_seq")
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="generator")
    @Column(name="share_log_oid", unique=true, nullable=false)
    public Integer getShareLogOid() {
        return this.shareLogOid;
    }
    
    public void setShareLogOid(Integer shareLogOid) {
        this.shareLogOid = shareLogOid;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="operation_timestamp", nullable=false, length=29)
    public Date getOperationTimestamp() {
        return this.operationTimestamp;
    }
    
    public void setOperationTimestamp(Date operationTimestamp) {
        this.operationTimestamp = operationTimestamp;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_timestamp", length=29)
    public Date getEndTimestamp() {
        return this.endTimestamp;
    }
    
    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    
    @Column(name="node", nullable=false)
    public String getNode() {
        return this.node;
    }
    
    public void setNode(String node) {
        this.node = node;
    }
}

