package com.example.demo.main.security;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class CustomUserDetailsContextMapper extends LdapUserDetailsMapper implements UserDetailsContextMapper {
    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        LdapUserDetailsImpl ldapUserDetailsImpl = (LdapUserDetailsImpl) super.mapUserFromContext(ctx, username, authorities);
        ConfigServerUser configServerUser = new ConfigServerUser();
        configServerUser.setAccountNonExpired(ldapUserDetailsImpl.isAccountNonExpired());
        configServerUser.setAccountNonLocked(ldapUserDetailsImpl.isAccountNonLocked());
        configServerUser.setCredentialsNonExpired(ldapUserDetailsImpl.isCredentialsNonExpired());
        configServerUser.setEnabled(ldapUserDetailsImpl.isEnabled());
        configServerUser.setUsername(ldapUserDetailsImpl.getUsername());
        configServerUser.setAuthorities(ldapUserDetailsImpl.getAuthorities());
        configServerUser.setEmployeeName(ctx.getObjectAttribute("displayname").toString());
        configServerUser.setEmailAddress(ctx.getObjectAttribute("mail").toString());

        return configServerUser;
    }
}
