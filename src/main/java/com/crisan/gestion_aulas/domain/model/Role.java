package com.crisan.gestion_aulas.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    private Long roleId;
    private String roleDescription;
}
