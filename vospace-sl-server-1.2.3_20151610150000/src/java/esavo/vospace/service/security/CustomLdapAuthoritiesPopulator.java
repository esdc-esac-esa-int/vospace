package esavo.vospace.service.security;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl; // Deprecated
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;

/**
 *
 * <p>Extends current Spring/SpringSource DefaultLdapAuthoritiesPopulator to support a Group within a Group LDAP authorization.</p> 
 */
public class CustomLdapAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator {

  private class Person {
    private List<String> groups;
    private List<String> roles;    
    
    @SuppressWarnings("unused")
    public void setGroups(Enumeration<String> groups) {
      this.groups = new ArrayList<String>();
      while(groups.hasMoreElements()) {
        this.groups.add(groups.nextElement());
      }
    }
    
    public void setGroups(Enumeration<String> groups, String objectclass) {
      this.groups = new ArrayList<String>();
      String group;
      while(groups.hasMoreElements()) {
        group = groups.nextElement();
        if(group.indexOf(objectclass) != -1) {
          // syntax euclidperson.<group-name>:*
          this.groups.add(group.substring(objectclass.length()+1, 
                                          group.indexOf(":")));
        }        
      }
    }

    public List<String> getGroups() {
      if(groups == null) this.groups = new ArrayList<String>(); 
      return this.groups;
    }
    
    public void setRoles(Enumeration<String> roles) {
      this.roles = new ArrayList<String>();
      while(roles.hasMoreElements()) {
        this.roles.add(roles.nextElement());
      }
    }
    
    @SuppressWarnings("unused")
    public List<String> getRoles() {
      if(this.roles == null) this.roles = new ArrayList<String>(); 
      return this.roles;
    }
  }

  private class PersonAttributesMapper implements AttributesMapper {  
    @SuppressWarnings("unchecked")
    public Object mapFromAttributes(Attributes attrs) throws NamingException {  
       Person person = new Person();  
       Attribute roleAttribute = attrs.get("planckrole");
       if(roleAttribute != null) {
         person.setRoles((NamingEnumeration<String>)roleAttribute.getAll());
       }
       Attribute groupAttribute = attrs.get("rssdgrpmembershiporg");
       if(groupAttribute != null) {
         person.setGroups((NamingEnumeration<String>)groupAttribute.getAll(),
             "euclidperson");
       }       
       return person;  
    }  
 }    
  /**
   * 
   * @param contextSource
   * @param groupSearchBase
   */
  public CustomLdapAuthoritiesPopulator(ContextSource contextSource, String groupSearchBase) {
    super(contextSource, groupSearchBase);
  }

  /**
   * 
   * @param user
   * @param username
   */
  @Override
  protected Set<GrantedAuthority> getAdditionalRoles(DirContextOperations user, String username) {
    Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
    LdapTemplate ldapTemplate = new LdapTemplate(this.getContextSource());
    String dn = "uid="+ user.getStringAttribute("uid")+ ",ou=people,o=esa.nl";
    Person person = (Person)ldapTemplate.lookup(dn, new PersonAttributesMapper());
    /*for (String role: person.getRoles()) {
        grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_" + role.toUpperCase()));
    }*/
    for (String group: person.getGroups()) {
      grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + group.toUpperCase()));
    }    
    return grantedAuthorities;
  }
}