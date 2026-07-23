package com.crisan.gestion_aulas.web.mapper;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.web.dto.booking.BookingResponse;
import com.crisan.gestion_aulas.web.dto.booking.CreateBookingRequest;
import com.crisan.gestion_aulas.web.dto.booking.UpdateBookingRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class BookingWebMapperTest {

    private final BookingWebMapper mapper = new BookingWebMapper();

    @Test
    void toDomainFromCreateRequestMapsFieldsWithoutUser() {
        CreateBookingRequest request = new CreateBookingRequest();
        request.setBookingDate(LocalDate.of(2030, 1, 1));
        request.setStartTime(LocalTime.of(9, 0));
        request.setEndTime(LocalTime.of(10, 0));
        request.setClassroomId(5L);
        request.setStateId(3L);

        Booking booking = mapper.toDomain(request);

        assertThat(booking.getBookingDate()).isEqualTo(LocalDate.of(2030, 1, 1));
        assertThat(booking.getStartTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(booking.getEndTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(booking.getClassroom().getClassroomId()).isEqualTo(5L);
        assertThat(booking.getState().getStateId()).isEqualTo(3L);
        assertThat(booking.getUser()).isNull();
    }

    @Test
    void toDomainFromUpdateRequestMapsFieldsWithoutUser() {
        UpdateBookingRequest request = new UpdateBookingRequest();
        request.setBookingDate(LocalDate.of(2030, 2, 2));
        request.setStartTime(LocalTime.of(11, 0));
        request.setEndTime(LocalTime.of(12, 0));
        request.setClassroomId(8L);
        request.setStateId(2L);

        Booking booking = mapper.toDomain(request);

        assertThat(booking.getClassroom().getClassroomId()).isEqualTo(8L);
        assertThat(booking.getState().getStateId()).isEqualTo(2L);
        assertThat(booking.getUser()).isNull();
    }

    @Test
    void toResponseMapsNamesAndFullUserName() {
        Booking booking = Booking.builder()
                .bookingId(1L)
                .bookingDate(LocalDate.of(2030, 3, 3))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(9, 0))
                .classroom(Classroom.builder().name("A1").build())
                .user(User.builder().name("John").lastName("Doe").build())
                .state(State.builder().stateDescription("CONFIRMED").build())
                .build();

        BookingResponse response = mapper.toResponse(booking);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getClassroom()).isEqualTo("A1");
        assertThat(response.getUser()).isEqualTo("John Doe");
        assertThat(response.getState()).isEqualTo("CONFIRMED");
    }
}
