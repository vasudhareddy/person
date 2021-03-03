package com.embl.person.model;

import org.springframework.security.core.*;

import java.util.*;

public class Actor implements Authentication {

    private String loggedInUserName;

    public Actor(String loggedInUserName) {
        this.loggedInUserName = loggedInUserName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return loggedInUserName;
    }
}
