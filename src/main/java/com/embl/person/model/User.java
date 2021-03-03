package com.embl.person.model;

import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

import java.util.*;

/*
 This class sets the logged in user name which audit aware uses to save this name for created by and modfiedby
 user for each entity
 */
public class User {
    public static String setUser(String loggedInUserName) {
        SecurityContextHolder.getContext().setAuthentication(new Actor(loggedInUserName.toUpperCase()));
        return getUser();
    }

    public static String getUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).map(Authentication::getName).orElse(null);
    }
}
