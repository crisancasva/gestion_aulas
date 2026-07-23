package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.domain.repository.BookingRepository;
import com.crisan.gestion_aulas.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    private static final String CURRENT_EMAIL = "user@test.com";

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking validBooking;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(CURRENT_EMAIL, null));

        validBooking = Booking.builder()
                .bookingId(1L)
                .bookingDate(LocalDate.now().plusDays(1))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .classroom(Classroom.builder().classroomId(5L).build())
                .build();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void mockCurrentUser() {
        when(userRepository.getByEmail(CURRENT_EMAIL))
                .thenReturn(Optional.of(User.builder().userId(7L).email(CURRENT_EMAIL).build()));
    }

    @Test
    void getAllDelegatesToRepository() {
        List<Booking> expected = List.of(validBooking);
        when(bookingRepository.getAll()).thenReturn(expected);

        assertThat(bookingService.getAll()).isEqualTo(expected);
        verify(bookingRepository).getAll();
    }

    @Test
    void getByIdDelegatesToRepository() {
        when(bookingRepository.getById(1L)).thenReturn(Optional.of(validBooking));

        assertThat(bookingService.getById(1L)).contains(validBooking);
        verify(bookingRepository).getById(1L);
    }

    @Test
    void getByDateDelegatesToRepository() {
        LocalDate date = LocalDate.now().plusDays(2);
        when(bookingRepository.getByDate(date)).thenReturn(List.of(validBooking));

        assertThat(bookingService.getByDate(date)).containsExactly(validBooking);
        verify(bookingRepository).getByDate(date);
    }

    @Test
    void deleteDelegatesToRepository() {
        bookingService.delete(7L);
        verify(bookingRepository).delete(7L);
    }

    @Test
    void createBookingSavesWhenValid() {
        mockCurrentUser();
        when(bookingRepository.getByClassroomAndDate(5L, validBooking.getBookingDate()))
                .thenReturn(List.of());
        when(bookingRepository.save(validBooking)).thenReturn(validBooking);

        Booking result = bookingService.createBooking(validBooking);

        assertThat(result).isEqualTo(validBooking);
        verify(bookingRepository).save(validBooking);
    }

    @Test
    void createBookingRejectsNullDate() {
        mockCurrentUser();
        validBooking.setBookingDate(null);

        assertThatThrownBy(() -> bookingService.createBooking(validBooking))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("fecha");
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void createBookingRejectsPastDate() {
        mockCurrentUser();
        validBooking.setBookingDate(LocalDate.now().minusDays(1));

        assertThatThrownBy(() -> bookingService.createBooking(validBooking))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("pasadas");
    }

    @Test
    void createBookingRejectsNullTimes() {
        mockCurrentUser();
        validBooking.setStartTime(null);

        assertThatThrownBy(() -> bookingService.createBooking(validBooking))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("hora de inicio y la hora de fin");
    }

    @Test
    void createBookingRejectsStartNotBeforeEnd() {
        mockCurrentUser();
        validBooking.setStartTime(LocalTime.of(11, 0));
        validBooking.setEndTime(LocalTime.of(10, 0));

        assertThatThrownBy(() -> bookingService.createBooking(validBooking))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("menor que la hora de fin");
    }

    @Test
    void createBookingRejectsOverlappingBooking() {
        mockCurrentUser();
        Booking existing = Booking.builder()
                .bookingId(99L)
                .startTime(LocalTime.of(9, 30))
                .endTime(LocalTime.of(10, 30))
                .build();
        when(bookingRepository.getByClassroomAndDate(5L, validBooking.getBookingDate()))
                .thenReturn(List.of(existing));

        assertThatThrownBy(() -> bookingService.createBooking(validBooking))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ya está reservada");
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void createBookingAllowsAdjacentBooking() {
        mockCurrentUser();
        Booking existing = Booking.builder()
                .bookingId(99L)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(11, 0))
                .build();
        when(bookingRepository.getByClassroomAndDate(5L, validBooking.getBookingDate()))
                .thenReturn(List.of(existing));
        when(bookingRepository.save(validBooking)).thenReturn(validBooking);

        assertThat(bookingService.createBooking(validBooking)).isEqualTo(validBooking);
    }

    @Test
    void getMyBookingsReturnsBookingsForAuthenticatedUser() {
        mockCurrentUser();
        List<Booking> mine = List.of(validBooking);
        when(bookingRepository.getByUser(7L)).thenReturn(mine);

        assertThat(bookingService.getMyBookings()).isEqualTo(mine);
        verify(bookingRepository).getByUser(7L);
    }

    @Test
    void getMyBookingsThrowsWhenUserNotFound() {
        when(userRepository.getByEmail(CURRENT_EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.getMyBookings())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Usuario no encontrado");
        verify(bookingRepository, never()).getByUser(anyLong());
    }

    @Test
    void updateBookingThrowsWhenNotFound() {
        when(bookingRepository.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.updateBooking(1L, validBooking))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reserva no encontrada");
    }

    @Test
    void updateBookingUpdatesFieldsAndSaves() {
        Booking existing = Booking.builder()
                .bookingId(1L)
                .bookingDate(LocalDate.now())
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(8, 30))
                .build();
        when(bookingRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(bookingRepository.getByClassroomAndDate(5L, validBooking.getBookingDate()))
                .thenReturn(List.of());
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> inv.getArgument(0));

        Booking result = bookingService.updateBooking(1L, validBooking);

        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(captor.capture());
        Booking saved = captor.getValue();
        assertThat(saved.getBookingDate()).isEqualTo(validBooking.getBookingDate());
        assertThat(saved.getStartTime()).isEqualTo(validBooking.getStartTime());
        assertThat(saved.getEndTime()).isEqualTo(validBooking.getEndTime());
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void updateBookingIgnoresSameBookingWhenCheckingOverlap() {
        Booking existing = Booking.builder()
                .bookingId(1L)
                .bookingDate(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .build();
        when(bookingRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(bookingRepository.getByClassroomAndDate(5L, validBooking.getBookingDate()))
                .thenReturn(List.of(existing));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> inv.getArgument(0));

        assertThat(bookingService.updateBooking(1L, validBooking)).isNotNull();
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void updateBookingRejectsOverlapWithOtherBooking() {
        Booking existing = Booking.builder().bookingId(1L).build();
        Booking other = Booking.builder()
                .bookingId(2L)
                .startTime(LocalTime.of(9, 30))
                .endTime(LocalTime.of(10, 30))
                .build();
        when(bookingRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(bookingRepository.getByClassroomAndDate(anyLong(), any()))
                .thenReturn(List.of(other));

        assertThatThrownBy(() -> bookingService.updateBooking(1L, validBooking))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ya está reservada");
        verify(bookingRepository, never()).save(any());
    }
}
