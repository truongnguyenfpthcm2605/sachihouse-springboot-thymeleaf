package com.shachi.shachihouse.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class OAuth2UserDetailCustom  implements OAuth2User, UserDetails {

    private String username;
    private String password;

    private String fullname;

    private List<GrantedAuthority> authorities;
    private Map<String,Object> attributes;

    public OAuth2UserDetailCustom(String fullname, String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.authorities = authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return fullname;
    }
}
