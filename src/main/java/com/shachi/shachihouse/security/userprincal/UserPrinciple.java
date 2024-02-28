package com.shachi.shachihouse.security.userprincal;

import com.shachi.shachihouse.entities.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserPrinciple implements UserDetails {

    private String username;
    private String password;
    private String fullname;
    private List<? extends GrantedAuthority> authorities;
    BCryptPasswordEncoder p = new BCryptPasswordEncoder();

    public UserPrinciple(Account account){
        this.username = account.getEmail();
        this.password = account.getPassword();
        this.fullname = account.getFullname();
        this.authorities = account.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
    }

    public String getFullname() {
        return fullname;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
}
