package com.user.management.security;

import com.user.management.persistance.entities.User;
import com.user.management.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User Details - Represents information about a logged in user.
 */
public class CustomUserDetails implements UserDetails {
    /**
     * The entity that represents the currently logged in user.
     */
    private final User user;
    private UserService userService;
    /**
     * List of Roles associated w/ the logged in user.
     */
    private List<GrantedAuthority> authorities;

    /**
     * Constructor.
     */
    public CustomUserDetails(final User user,UserService userService) {
        System.out.println("..Constructor .CustomUserDetails..."+user.getEmail());
        this.user = user;
        this.authorities = new ArrayList<>();
        this.userService = userService;
        try{
            // Generate authorities/roles
            List<GrantedAuthority> roles = new ArrayList<>();
            List<String> authories =userService.getAuthoritiesByEmail(user.getEmail());
            System.out.println("CustomUserDetails authories ...."+authories);
            if (authories != null) {
                for (int i = 0; i < authories.size(); i++) {
                    System.out.println("CustomUserDetails adding authority  : "+authories.get(i));
                    roles.add(new SimpleGrantedAuthority(authories.get(i)));
                }
            }
            // Save to immutable collection.
            authorities = roles;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
       return true;
    }

    /**
     * @return The user entity representing the currently logged in user.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * @return UserId of the currently logged in user.
     */
    public long getUserId() {
        return getUser().getUserId();
    }
}