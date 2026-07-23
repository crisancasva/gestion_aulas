package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user(Long id, String email) {
        return User.builder()
                .userId(id)
                .name("John")
                .lastName("Doe")
                .email(email)
                .password("raw")
                .role(Role.builder().roleId(1L).build())
                .state(State.builder().stateId(1L).build())
                .build();
    }

    @Test
    void getAllDelegatesToRepository() {
        when(userRepository.getAll()).thenReturn(List.of(user(1L, "a@b.com")));
        assertThat(userService.getAll()).hasSize(1);
    }

    @Test
    void getByIdDelegatesToRepository() {
        when(userRepository.getById(1L)).thenReturn(Optional.of(user(1L, "a@b.com")));
        assertThat(userService.getById(1L)).isPresent();
    }

    @Test
    void getByEmailDelegatesToRepository() {
        when(userRepository.getByEmail("a@b.com")).thenReturn(Optional.of(user(1L, "a@b.com")));
        assertThat(userService.getByEmail("a@b.com")).isPresent();
    }

    @Test
    void deleteDelegatesToRepository() {
        userService.delete(4L);
        verify(userRepository).delete(4L);
    }

    @Test
    void createEncodesPasswordAndSaves() {
        User u = user(null, "new@b.com");
        when(userRepository.getByEmail("new@b.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("raw")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.create(u);

        assertThat(result.getPassword()).isEqualTo("encoded");
        verify(passwordEncoder).encode("raw");
        verify(userRepository).save(u);
    }

    @Test
    void createRejectsDuplicateEmail() {
        User u = user(null, "dup@b.com");
        when(userRepository.getByEmail("dup@b.com")).thenReturn(Optional.of(user(2L, "dup@b.com")));

        assertThatThrownBy(() -> userService.create(u))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe un usuario");
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateThrowsWhenNotFound() {
        when(userRepository.getById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.update(1L, user(1L, "a@b.com")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Usuario no encontrado");
    }

    @Test
    void updateChangesFieldsAndSaves() {
        User existing = user(1L, "old@b.com");
        User incoming = user(1L, "updated@b.com");
        incoming.setName("Jane");
        when(userRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.getByEmail("updated@b.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        userService.update(1L, incoming);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Jane");
        assertThat(captor.getValue().getEmail()).isEqualTo("updated@b.com");
    }

    @Test
    void updateAllowsSameUserKeepingEmail() {
        User existing = user(1L, "same@b.com");
        User incoming = user(1L, "same@b.com");
        when(userRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.getByEmail("same@b.com")).thenReturn(Optional.of(user(1L, "same@b.com")));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        assertThat(userService.update(1L, incoming)).isNotNull();
    }

    @Test
    void updateRejectsEmailUsedByAnotherUser() {
        User existing = user(1L, "old@b.com");
        User incoming = user(1L, "taken@b.com");
        when(userRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.getByEmail("taken@b.com")).thenReturn(Optional.of(user(2L, "taken@b.com")));

        assertThatThrownBy(() -> userService.update(1L, incoming))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe un usuario");
        verify(userRepository, never()).save(any());
    }
}
