package com.crisan.gestion_aulas.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "state_des", nullable = false, unique = true, length = 30)
    private String stateDescription;
}
