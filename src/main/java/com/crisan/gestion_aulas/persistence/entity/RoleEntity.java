package com.crisan.gestion_aulas.persistence.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_des", nullable = false, unique = true, length = 30)
    private String roleDescription;
}
