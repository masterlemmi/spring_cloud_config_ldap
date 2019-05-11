package com.example.demo.main.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ConfigServerUser implements UserDetails {

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private String employeeName;
    private String emailAddress;

    @Override public String getPassword() {
        return null;
    }

    @Override public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override public boolean isEnabled() {
        return this.enabled;
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override public String getUsername() {
        return this.username;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setEmployeeName(String displayname) {
        this.employeeName = displayname;
    }


    public void setEmailAddress(String email) {
        this.emailAddress = email;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
