package com.crisan.gestion_aulas.web.dto.classroom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomResponse {
    private Long id;
    private String name;
    private Integer capacity;
    private String location;
    private String state;
}
