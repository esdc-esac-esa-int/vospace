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
 * AccessLog generated by hbm2java
 */
@Entity
@Table(name="access_log"
    ,schema="controls"
)
public class AccessLog  implements java.io.Serializable {


     private Integer accessLogOid;
     private User user;
     private Date loginTimestamp;
     private Date logoutTimestamp;
     private Long quota;
     private String internetsite;
     private String environment;

    public AccessLog() {
    }

	
    public AccessLog(Integer accessLogOid, User user, Date loginTimestamp, Long quota) {
        this.accessLogOid = accessLogOid;
        this.user = user;
        this.loginTimestamp = loginTimestamp;
        this.quota = quota;
    }
    public AccessLog(Integer accessLogOid, User user, Date loginTimestamp, Date logoutTimestamp, Long quota, String internetsite, String environment) {
       this.accessLogOid = accessLogOid;
       this.user = user;
       this.loginTimestamp = loginTimestamp;
       this.logoutTimestamp = logoutTimestamp;
       this.quota = quota;
       this.internetsite = internetsite;
       this.environment = environment;
    }
       
    @SequenceGenerator(name="generator", sequenceName="controls.access_log_access_log_oid_seq")
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="generator")
    @Column(name="access_log_oid", unique=true, nullable=false)
    public Integer getAccessLogOid() {
        return this.accessLogOid;
    }
    
    public void setAccessLogOid(Integer accessLogOid) {
        this.accessLogOid = accessLogOid;
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
    @Column(name="login_timestamp", length=29)
    public Date getLoginTimestamp() {
        return this.loginTimestamp;
    }
    
    public void setLoginTimestamp(Date loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="logout_timestamp", length=29)
    public Date getLogoutTimestamp() {
        return this.logoutTimestamp;
    }
    
    public void setLogoutTimestamp(Date logoutTimestamp) {
        this.logoutTimestamp = logoutTimestamp;
    }

    
    @Column(name="quota", nullable=false)
    public Long getQuota() {
        return this.quota;
    }
    
    public void setQuota(Long quota) {
        this.quota = quota;
    }

    
    @Column(name="internetsite")
    public String getInternetsite() {
        return this.internetsite;
    }
    
    public void setInternetsite(String internetsite) {
        this.internetsite = internetsite;
    }

    
    @Column(name="environment")
    public String getEnvironment() {
        return this.environment;
    }
    
    public void setEnvironment(String environment) {
        this.environment = environment;
    }




}

