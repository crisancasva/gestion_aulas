package com.crisan.gestion_aulas.web.mapper;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.web.dto.user.CreateUserRequest;
import com.crisan.gestion_aulas.web.dto.user.UpdateUserRequest;
import com.crisan.gestion_aulas.web.dto.user.UserResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserWebMapperTest {

    private final UserWebMapper mapper = new UserWebMapper();

    @Test
    void toDomainFromCreateRequestMapsAllFields() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John");
        request.setLastName("Doe");
        request.setEmail("john@b.com");
        request.setPassword("secret");
        request.setRoleId(1L);
        request.setStateId(2L);

        User user = mapper.toDomain(request);

        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getEmail()).isEqualTo("john@b.com");
        assertThat(user.getPassword()).isEqualTo("secret");
        assertThat(user.getRole().getRoleId()).isEqualTo(1L);
        assertThat(user.getState().getStateId()).isEqualTo(2L);
    }

    @Test
    void toDomainFromUpdateRequestOmitsPassword() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("Jane");
        request.setLastName("Roe");
        request.setEmail("jane@b.com");
        request.setRoleId(3L);
        request.setStateId(4L);

        User user = mapper.toDomain(request);

        assertThat(user.getName()).isEqualTo("Jane");
        assertThat(user.getEmail()).isEqualTo("jane@b.com");
        assertThat(user.getPassword()).isNull();
        assertThat(user.getRole().getRoleId()).isEqualTo(3L);
    }

    @Test
    void updateDomainMutatesExistingUser() {
        User existing = User.builder()
                .userId(1L)
                .name("Old")
                .lastName("Name")
                .email("old@b.com")
                .password("keep")
                .build();
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("New");
        request.setLastName("Name2");
        request.setEmail("new@b.com");
        request.setRoleId(5L);
        request.setStateId(6L);

        mapper.updateDomain(request, existing);

        assertThat(existing.getName()).isEqualTo("New");
        assertThat(existing.getEmail()).isEqualTo("new@b.com");
        assertThat(existing.getRole().getRoleId()).isEqualTo(5L);
        assertThat(existing.getState().getStateId()).isEqualTo(6L);
        assertThat(existing.getPassword()).isEqualTo("keep");
    }

    @Test
    void toResponseMapsRoleAndStateDescriptions() {
        User user = User.builder()
                .userId(1L)
                .name("John")
                .lastName("Doe")
                .email("john@b.com")
                .role(Role.builder().roleDescription("ADMIN").build())
                .state(State.builder().stateDescription("ACTIVE").build())
                .build();

        UserResponse response = mapper.toResponse(user);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getRole()).isEqualTo("ADMIN");
        assertThat(response.getState()).isEqualTo("ACTIVE");
    }
}
