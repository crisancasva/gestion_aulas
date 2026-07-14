package com.crisan.gestion_aulas.web.mapper;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.web.dto.user.CreateUserRequest;
import com.crisan.gestion_aulas.web.dto.user.UpdateUserRequest;
import com.crisan.gestion_aulas.web.dto.user.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {
    public User toDomain(CreateUserRequest request) {

        return User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.builder()
                        .roleId(request.getRoleId())
                        .build())
                .state(State.builder()
                        .stateId(request.getStateId())
                        .build())
                .build();
    }

    public UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getUserId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getRoleDescription())
                .state(user.getState().getStateDescription())
                .build();
    }

    public void updateDomain(UpdateUserRequest request, User user) {
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        user.setRole(Role.builder()
                        .roleId(request.getRoleId())
                        .build());

        user.setState(State.builder()
                .stateId(request.getStateId())
                .build());
    }

    public User toDomain(UpdateUserRequest request) {

        return User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(Role.builder()
                        .roleId(request.getRoleId())
                        .build())
                .state(State.builder()
                        .stateId(request.getStateId())
                        .build())
                .build();
    }
}
