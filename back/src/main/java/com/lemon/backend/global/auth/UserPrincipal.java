package com.lemon.backend.global.auth;

import com.lemon.backend.domain.users.user.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class UserPrincipal implements OAuth2User {

    private Users user;
    private Map<String, Object> attributes;

    public UserPrincipal(Users user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public static UserPrincipal create(Users user, Map<String, Object> attributes) {
        return new UserPrincipal(user, attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getName() {
        return user.getId().toString();
    }
}
