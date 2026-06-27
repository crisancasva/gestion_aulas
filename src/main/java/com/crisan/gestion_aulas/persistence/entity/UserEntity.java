package com.crisan.gestion_aulas.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long userId;

    @Column(name = "users_name", nullable = false, length = 50)
    private String name;

    @Column(name = "users_lastname", nullable = false, length = 50)
    private String lastName;

    @Column(name = "users_email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "users_password", nullable = false)
    private String password;

    @Column(name = "users_date_create", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private StateEntity state;
}
