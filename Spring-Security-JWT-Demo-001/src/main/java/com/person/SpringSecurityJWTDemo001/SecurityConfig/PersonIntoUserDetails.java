package com.person.SpringSecurityJWTDemo001.SecurityConfig;

import com.person.SpringSecurityJWTDemo001.dto.Person;
import com.person.SpringSecurityJWTDemo001.dto.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonIntoUserDetails implements UserDetails {
    private String email;
    private String password;
    private boolean active;

    private List<GrantedAuthority> authorities;

    /**
     * This is the place where the Person class object get converted into UserDetails class object
     */
    public PersonIntoUserDetails(Person person) {
        this.email = person.getEmail();
        this.password = person.getSensitiveDetails().getPassword();
        this.active = person.getSensitiveDetails().getActive() == 1;
        authorities = new ArrayList<>();
        for (Roles roles : person.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(roles.getRole()));
        }
        System.out.println(authorities);
        System.out.println("Test - 1");

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
        return email;
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
        return active;
    }
}
