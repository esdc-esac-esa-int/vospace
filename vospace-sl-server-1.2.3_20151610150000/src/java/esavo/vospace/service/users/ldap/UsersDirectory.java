package esavo.vospace.service.users.ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.UserTO;

/**
 * Connect to LDAP to retrieve information about users.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class UsersDirectory {

    final static String ldapSearchBase = "ou=People, o=esa.nl";
    final static String PROVIDER_URL_SSL = "ldaps://ldap.sciops.esa.int:389";
    final static String PROVIDER_URL = PROVIDER_URL_SSL; 
    final static String CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";    
    final static String WILD_CARD = "*";
    final static String MAIL = "mail";
    final static String CN = "cn";
    
    private static Log Log = LogFactory.getLog(UsersDirectory.class);
    
    /**
     * Retrieve user suggestion from LDAP server based on the criteria passed by
     * @param searchCriteria
     * @return list of UserTO that match searchCriteria
     */
    public List<UserTO> getSuggestion (String searchCriteria) {
        
        List<UserTO> userList = new ArrayList<UserTO>();
        
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL,PROVIDER_URL);

        DirContext ctx;
        try {
           ctx = new InitialDirContext(env);
        } catch (NamingException e) {
            e.printStackTrace();
           throw new RuntimeException(e);
        }
        
        NamingEnumeration<?> resultsByName = null;
        NamingEnumeration<?> resultsBySurname = null;
        try {
            
           SearchControls controls = new SearchControls();
           controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
           resultsByName = ctx.search(ldapSearchBase, CN+"="+searchCriteria+WILD_CARD, controls);
           resultsBySurname = ctx.search(ldapSearchBase, CN+"="+WILD_CARD+" "+searchCriteria+WILD_CARD, controls);
           
           // Analyze results by name
           while (resultsByName.hasMore()) {
              SearchResult searchResult = (SearchResult) resultsByName.next();            
              Attributes attributes = searchResult.getAttributes();  
              
              String uid = searchResult.getName();
              String uname = uid.substring(uid.indexOf("=") + 1);
              String fullName = attributes.get(CN).get().toString();
              String mail = "";              
              Attribute mailAttribute = attributes.get("mail");
              if (mailAttribute != null) {
                  mail = attributes.get(MAIL).get().toString();
                  
                  UserTO user = new UserTO();
                  user.setName(uname);
                  user.setFullName(fullName);
                  user.setMail(mail);                
                  userList.add(user);
              }
              
              //Log.debug("getSuggestion(criteria="+searchCriteria+") -> "
              //        + "User---------- "+uid + ", " + fullName);        
           }
           
           
           // Analyze results by surname
           while (resultsBySurname.hasMore()) {
              SearchResult searchResult = (SearchResult) resultsBySurname.next();            
              Attributes attributes = searchResult.getAttributes();  
              
              String uid = searchResult.getName();
              String uname = uid.substring(uid.indexOf("=") + 1);
              String fullName = attributes.get(CN).get().toString();
              String mail = "";              
              Attribute mailAttribute = attributes.get("mail");
              if (mailAttribute != null) {
                  mail = attributes.get(MAIL).get().toString();
                  
                  UserTO user = new UserTO();
                  user.setName(uname);
                  user.setFullName(fullName);
                  user.setMail(mail);                
                  userList.add(user);
              }
              
              //Log.debug("getSuggestion(criteria="+searchCriteria+") -> "
              //        + "User---------- "+uid + ", " + fullName);        
           }
        } catch (NameNotFoundException e) {
             e.printStackTrace();
             Log.debug("GetSuggestion() " + e.getMessage());
        } catch (NamingException e) {
            e.printStackTrace();
            Log.debug("GetSuggestion() " + e.getMessage());
           throw new RuntimeException(e);
        } finally {
           if (resultsByName != null) {
              try {
                 resultsByName.close();
              } catch (Exception e) {
                   e.printStackTrace();
                   Log.debug("GetSuggestion() " + e.getMessage());
              }
           }
           if (ctx != null) {
              try {
                 ctx.close();
              } catch (Exception e) {
                  e.printStackTrace();
                  Log.debug("GetSuggestion() " + e.getMessage());
              }
           }
        }
        return userList;
    }
    
    /**
     * Retrieve LDAP UserTO from LDAP SERVER
     * @param uid, ldap user name
     * @return UserTO
     */
    public UserTO searchLdapUser (String uid) {
        
        UserTO user = new UserTO();
        
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, PROVIDER_URL);

        DirContext ctx;
        try {
           ctx = new InitialDirContext(env);
        } catch (NamingException e) {
            e.printStackTrace();
           throw new RuntimeException(e);
        }
        
        NamingEnumeration<?> results = null;
        try {
           SearchControls controls = new SearchControls();
           controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
           results = ctx.search(ldapSearchBase, "Uid="+uid, controls);
                      
           int count = 0;
           while (results.hasMore()) {
              SearchResult searchResult = (SearchResult) results.next();            
              Attributes attributes = searchResult.getAttributes();  
              
              String userId = searchResult.getName();
              String uname = userId.substring(userId.indexOf("=") + 1);
              String fullName = attributes.get(CN).get().toString();
              //String mail = attributes.get(MAIL).get().toString();
              String mail = "";              
              Attribute mailAttribute = attributes.get("mail");
              if (mailAttribute != null) {
                  mail = attributes.get(MAIL).get().toString();
                  user.setName(uname);
                  user.setFullName(fullName);
                  user.setMail(mail);
              }
              
              count++;
           }
           
           if (count > 1) {
               Log.debug("GetSuggestion() " + "Error: more than one results.");
           }
           
        } catch (NameNotFoundException e) {
             e.printStackTrace();
             Log.debug("SearchLdapUser()" + e.getMessage());
        } catch (NamingException e) {
            e.printStackTrace();
            Log.debug("SearchLdapUser()" + e.getMessage());
           throw new RuntimeException(e);
        } finally {
           if (results != null) {
              try {
                 results.close();
              } catch (Exception e) {
                   e.printStackTrace();
                   Log.debug("SearchLdapUser()" + e.getMessage());
              }
           }
           if (ctx != null) {
              try {
                 ctx.close();
              } catch (Exception e) {
                   e.printStackTrace();
                   Log.debug("SearchLdapUser()" + e.getMessage());
              }
           }
        }
        return user;
    }
    
}
