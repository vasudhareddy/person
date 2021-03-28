package com.embl.person.entity;

import javax.persistence.*;

@Table(name = "role")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", nullable = false, updatable = false)
    private Long roleId;
    private String role;

    public Role() {
    }

    public Role(Long roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
