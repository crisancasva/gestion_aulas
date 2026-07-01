package com.crisan.gestion_aulas.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classroom {
    private Long classroomId;
    private String name;
    private Integer capacity;
    private String location;

    private State state;
}
