package com.example.streamserver.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;
@Data
@Entity
@Table(name="App_Role", uniqueConstraints = {@UniqueConstraint(name = "APP_ROLE_UK", columnNames = "Role_Name")})
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Role_Id", nullable = false)
    private UUID roleId;


    @Column(name="Role_Name", length=32, nullable = false)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
