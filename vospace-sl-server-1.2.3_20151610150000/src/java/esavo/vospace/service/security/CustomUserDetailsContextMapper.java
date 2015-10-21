package esavo.vospace.service.security;

import java.util.Collection;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

public class CustomUserDetailsContextMapper implements UserDetailsContextMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx,
            String username, Collection<? extends GrantedAuthority> authorities) {

        String firstname = ctx.getStringAttribute("cn");
        @SuppressWarnings("unused")
        String secondname = ctx.getStringAttribute("sn");
        String mail = ctx.getStringAttribute("mail");
        //TODO: address,...
        return new CustomUserDetails(username, firstname, /*secondname,*/ mail);
    }

    @Override
    public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
        // TODO Auto-generated method stub

    }

}
