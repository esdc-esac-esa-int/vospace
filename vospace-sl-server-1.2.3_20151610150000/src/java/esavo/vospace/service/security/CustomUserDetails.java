package esavo.vospace.service.security;

import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

/**
 * CustomUserDetails to extend the information of an ldpa user.
 *
 * @author Sara Nieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 */
public class CustomUserDetails extends LdapUserDetailsImpl {

    public String username;
    public String fullname;
    public String mail;

    /**
    *
    */
   private static final long serialVersionUID = 5793162407476403777L;

   public CustomUserDetails (String username, String fullname,
           String mail) {
       this.username = username;
       this.fullname = fullname;
       this.mail = mail;
   }

   @Override
   public String getUsername() {
       return username;
   }

    public String getFullname() {
        return fullname;
    }

    public String getMail() {
        return mail;
    }

    public void setFullname(String firstname) {
        this.fullname = firstname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    @Override
    public int hashCode() {
        return username.hashCode();
    }
    
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof CustomUserDetails) {
            return username.equals(((CustomUserDetails) rhs).username);
        }
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        //sb.append("Enabled: ").append(this.enabled).append("; ");
        //sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        //sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        //sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

        /*if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }*/

        return sb.toString();
    }
}
