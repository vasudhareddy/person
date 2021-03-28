package com.embl.person.model;

import com.embl.person.entity.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Data
@Builder
public class UserRequest {
    private String name;
    private String password;
    private int active;
    private String firstName;
    private String lastName;
    private Set<Role> roles;


    @Tolerate
    public UserRequest() {

    }

}
