package com.crisan.gestion_aulas.persistence.mapper;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.persistence.entity.BookingEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StateMapper.class,
        UserMapper.class, ClassroomMapper.class})
public interface BookingMapper {
    Booking toBooking(BookingEntity bookingEntity);
    BookingEntity toBookingEntity(Booking booking);
    List<Booking> toBookings(List<BookingEntity> bookingEntities);
    List<BookingEntity> toBookingEntities(List<Booking> bookings);
}
