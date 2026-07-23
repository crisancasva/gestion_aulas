package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.domain.service.UserService;
import com.crisan.gestion_aulas.web.dto.user.CreateUserRequest;
import com.crisan.gestion_aulas.web.dto.user.UpdateUserRequest;
import com.crisan.gestion_aulas.web.dto.user.UserResponse;
import com.crisan.gestion_aulas.web.mapper.UserWebMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserWebMapper userWebMapper;

    @InjectMocks
    private UserController controller;

    private final UserResponse response = UserResponse.builder().id(1L).build();

    @Test
    void getAllReturnsMappedResponses() {
        User user = User.builder().userId(1L).build();
        when(userService.getAll()).thenReturn(List.of(user));
        when(userWebMapper.toResponse(user)).thenReturn(response);

        assertThat(controller.getAll().getBody()).containsExactly(response);
    }

    @Test
    void getByIdReturnsResponse() {
        User user = User.builder().userId(1L).build();
        when(userService.getById(1L)).thenReturn(Optional.of(user));
        when(userWebMapper.toResponse(user)).thenReturn(response);

        assertThat(controller.getById(1L).getBody()).isEqualTo(response);
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(userService.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.getById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Usuario no encontrado");
    }

    @Test
    void getByEmailReturnsResponse() {
        User user = User.builder().email("a@b.com").build();
        when(userService.getByEmail("a@b.com")).thenReturn(Optional.of(user));
        when(userWebMapper.toResponse(user)).thenReturn(response);

        assertThat(controller.getByEmail("a@b.com").getBody()).isEqualTo(response);
    }

    @Test
    void getByEmailThrowsWhenMissing() {
        when(userService.getByEmail("a@b.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.getByEmail("a@b.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Usuario no encontrado");
    }

    @Test
    void createReturnsCreatedStatus() {
        CreateUserRequest request = new CreateUserRequest();
        User domain = User.builder().build();
        User saved = User.builder().userId(1L).build();
        when(userWebMapper.toDomain(request)).thenReturn(domain);
        when(userService.create(domain)).thenReturn(saved);
        when(userWebMapper.toResponse(saved)).thenReturn(response);

        ResponseEntity<UserResponse> result = controller.create(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void updateReturnsOk() {
        UpdateUserRequest request = new UpdateUserRequest();
        User domain = User.builder().build();
        User updated = User.builder().userId(1L).build();
        when(userWebMapper.toDomain(request)).thenReturn(domain);
        when(userService.update(1L, domain)).thenReturn(updated);
        when(userWebMapper.toResponse(updated)).thenReturn(response);

        assertThat(controller.update(1L, request).getBody()).isEqualTo(response);
    }

    @Test
    void deleteReturnsNoContent() {
        ResponseEntity<Void> result = controller.delete(5L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(userService).delete(5L);
    }
}
