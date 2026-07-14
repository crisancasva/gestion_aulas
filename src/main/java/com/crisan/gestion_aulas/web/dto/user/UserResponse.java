package com.crisan.gestion_aulas.web.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String role;
    private String state;
}