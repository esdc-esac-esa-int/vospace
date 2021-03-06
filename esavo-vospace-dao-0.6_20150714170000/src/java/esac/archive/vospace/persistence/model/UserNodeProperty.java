package esac.archive.vospace.persistence.model;
// Generated Aug 6, 2014 11:06:34 AM by Hibernate Tools 3.3.0.GA


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * UserNodeProperty generated by hbm2java
 */
@Entity
@Table(name="user_node_property"
    , schema="vos"
)
public class UserNodeProperty extends NodeProperty {

     private Set<Users> users = new HashSet<Users>(0);

    public UserNodeProperty() {}

    //@OneToMany(fetch=FetchType.LAZY, mappedBy="userNodeProperty")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "share_user", schema = "vos",
    joinColumns = { @JoinColumn(name = "node_property_id", nullable = false, updatable = false) }, 
    inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) })
    public Set<Users> getShareUsers() {
        return this.users;
    }
    
    public void setShareUsers(Set<Users> shareUsers) {
        this.users = shareUsers;
    }




}


