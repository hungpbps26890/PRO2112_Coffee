package com.poly.coffee.auth;

import com.poly.coffee.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class UserRoot implements UserDetails, OAuth2User {

    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    private Map<String, Object>attributes;

    public static UserRoot create(User user) {
        if (user.getIsActive()) {
            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            return UserRoot.builder()
                    .user(user)
                    .authorities(authorities)
                    .build();
        }

        return null;
    }

    public static UserRoot create(User user, Map<String, Object> attributes) {
        UserRoot userRoot = UserRoot.create(user);
        if (userRoot != null) {
            userRoot.setAttributes(attributes);
            return userRoot;
        }
        return null;

    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
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
    public String getName() {
        return this.user.getFirstName();
    }

}
