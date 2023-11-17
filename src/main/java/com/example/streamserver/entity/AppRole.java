package com.example.streamserver.entity;

import jakarta.persistence.*;

@Entity
@Table(name="App_Role", uniqueConstraints = {@UniqueConstraint(name = "APP_ROLE_UK", columnNames = "Role_Name")})
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Role_Id", nullable = false)
    private Long roleId;

    @Column(name="Role_Name", length=32, nullable = false)
    private String rolename;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
