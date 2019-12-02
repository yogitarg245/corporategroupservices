package com.user.management.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.management.persistance.entities.Permission;
import com.user.management.persistance.entities.Role;
import com.user.management.persistance.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Integer id,  String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
      /*  this.name = name;
        this.username = username;*/
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(User user) {
        System.out.println(user + " user.getRoles() = "+user.getRoles());
        Set<Role> roles=user.getRoles();

        List<GrantedAuthority> authorities =new ArrayList<>();
        for(Role role:roles){
            Set<Permission> permissions=role.getPermissions();
            for(Permission permission:permissions){
                SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(permission.getName());
                authorities.add(simpleGrantedAuthority);
            }
        }

        return new UserPrinciple(
                user.getUserId(),
                /*user.getEmail(),
                user.getFirstName()+" "+user.getFamilyName(),*/
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}