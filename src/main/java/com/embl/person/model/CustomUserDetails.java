package com.embl.person.model;

import com.embl.person.entity.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

public class CustomUserDetails extends UserEntity implements UserDetails {

    UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        super(userEntity);
        this.userEntity=userEntity;
        User.setUser(userEntity.getName());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = userEntity.getRoles().stream().map(role -> {
                    return new SimpleGrantedAuthority("ROLE_" + role.getRole());
                }

        ).collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getUsername() {
        return userEntity.getName();
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
