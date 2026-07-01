package com.crisan.gestion_aulas.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class State {
    private Long stateId;
    private String stateDescription;
}
