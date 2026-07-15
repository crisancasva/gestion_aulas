package com.crisan.gestion_aulas.web.mapper;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.web.dto.booking.BookingResponse;
import com.crisan.gestion_aulas.web.dto.booking.CreateBookingRequest;
import com.crisan.gestion_aulas.web.dto.booking.UpdateBookingRequest;
import org.springframework.stereotype.Component;

@Component
public class BookingWebMapper {
    public Booking toDomain(CreateBookingRequest request) {
        return Booking.builder()
                .bookingDate(request.getBookingDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .classroom(Classroom.builder()
                        .classroomId(request.getClassroomId())
                        .build())
                .user(User.builder()
                        .userId(request.getUserId())
                        .build())
                .state(State.builder()
                        .stateId(request.getStateId())
                        .build())
                .build();
    }

    public Booking toDomain(UpdateBookingRequest request){
        return Booking.builder()
                .bookingDate(request.getBookingDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .classroom(Classroom.builder()
                        .classroomId(request.getClassroomId())
                        .build())
                .state(State.builder()
                        .stateId(request.getStateId())
                        .build())
                .build();
    }

    public BookingResponse toResponse(Booking booking) {

        return BookingResponse.builder()
                .id(booking.getBookingId())
                .bookingDate(booking.getBookingDate())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .classroom(booking.getClassroom().getName())
                .user(
                        booking.getUser().getName() + " "
                                + booking.getUser().getLastName()
                )
                .state(booking.getState().getStateDescription())
                .build();
    }
}
