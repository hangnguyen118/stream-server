/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.streamserver.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

/**
 *
 * @author ntdie
 */
@Data
@Entity
@Table(name="User", uniqueConstraints = {@UniqueConstraint(name="USER_UK", columnNames = "User_Name")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="User_Id", nullable = false)
    private Long userId;

    @Column(name="User_Name", length = 64, nullable = false)
    private String username;

    @Column(name="Email", length = 64, nullable = false)
    private String email;

    @Column(name = "Encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;

    @Column(name="Phone", length = 10, nullable = false)
    private String phone;

    @Column(name="Enable", length = 1, nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<AppRole> roles;
}
