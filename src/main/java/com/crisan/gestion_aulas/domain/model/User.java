package com.crisan.gestion_aulas.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long userId;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    private Role role;
    private State state;
}
