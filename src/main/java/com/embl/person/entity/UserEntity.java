package com.embl.person.entity;

import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;
import java.io.*;
import java.util.*;

@Table(name = "user")
@Entity
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;
    private String name;
    private String password;
    private int active;
    private String firstName;
    private String lastName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public UserEntity() {

    }


    public UserEntity(UserEntity userEntity) {
        this.active = userEntity.active;
        this.firstName = userEntity.firstName;
        this.lastName = userEntity.lastName;
        this.password = userEntity.password;
        this.userId = userEntity.userId;
        this.name = userEntity.name;
        this.roles =userEntity.roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
