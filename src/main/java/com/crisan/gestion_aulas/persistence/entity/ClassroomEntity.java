package com.crisan.gestion_aulas.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classroom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cr_id")
    private Long classroomId;

    @Column(name = "cr_des", nullable = false, length = 50)
    private String name;

    @Column(name = "cr_cap", nullable = false)
    private Integer capacity;

    @Column(name = "cr_location", nullable = false, length = 50)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private StateEntity state;
}
