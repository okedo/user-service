package com.spikecorp.authserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "userTable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable = false)
    private String username;
    @Column(unique=false, nullable = false)
    private String password;
    @Column(unique=true, nullable = false)
    private String email;
}