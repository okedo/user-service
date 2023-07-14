package com.spikecorp.authserver.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = false, nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_table_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}