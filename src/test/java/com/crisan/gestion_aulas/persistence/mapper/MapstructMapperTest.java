package com.crisan.gestion_aulas.persistence.mapper;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.persistence.entity.BookingEntity;
import com.crisan.gestion_aulas.persistence.entity.ClassroomEntity;
import com.crisan.gestion_aulas.persistence.entity.RoleEntity;
import com.crisan.gestion_aulas.persistence.entity.StateEntity;
import com.crisan.gestion_aulas.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MapstructMapperTest {

    private StateMapperImpl stateMapper;
    private RoleMapperImpl roleMapper;
    private ClassroomMapperImpl classroomMapper;
    private UserMapperImpl userMapper;
    private BookingMapperImpl bookingMapper;

    @BeforeEach
    void setUp() {
        stateMapper = new StateMapperImpl();
        roleMapper = new RoleMapperImpl();
        classroomMapper = new ClassroomMapperImpl();
        userMapper = new UserMapperImpl();
        bookingMapper = new BookingMapperImpl();

        ReflectionTestUtils.setField(classroomMapper, "stateMapper", stateMapper);
        ReflectionTestUtils.setField(userMapper, "roleMapper", roleMapper);
        ReflectionTestUtils.setField(userMapper, "stateMapper", stateMapper);
        ReflectionTestUtils.setField(bookingMapper, "stateMapper", stateMapper);
        ReflectionTestUtils.setField(bookingMapper, "userMapper", userMapper);
        ReflectionTestUtils.setField(bookingMapper, "classroomMapper", classroomMapper);
    }

    @Test
    void stateMapperRoundTripAndNulls() {
        StateEntity entity = StateEntity.builder().stateId(1L).stateDescription("ACTIVE").build();
        State state = stateMapper.toState(entity);
        assertThat(state.getStateDescription()).isEqualTo("ACTIVE");
        assertThat(stateMapper.toStateEntity(state).getStateId()).isEqualTo(1L);

        assertThat(stateMapper.toState(null)).isNull();
        assertThat(stateMapper.toStateEntity(null)).isNull();
        assertThat(stateMapper.toStates(List.of(entity))).hasSize(1);
        assertThat(stateMapper.toStateEntities(List.of(state))).hasSize(1);
    }

    @Test
    void roleMapperRoundTripAndNulls() {
        RoleEntity entity = RoleEntity.builder().roleId(2L).roleDescription("ADMIN").build();
        Role role = roleMapper.toRole(entity);
        assertThat(role.getRoleDescription()).isEqualTo("ADMIN");
        assertThat(roleMapper.toRoleEntity(role).getRoleId()).isEqualTo(2L);

        assertThat(roleMapper.toRole(null)).isNull();
        assertThat(roleMapper.toRoles(List.of(entity))).hasSize(1);
        assertThat(roleMapper.toRoleEntities(List.of(role))).hasSize(1);
    }

    @Test
    void classroomMapperMapsNestedState() {
        ClassroomEntity entity = ClassroomEntity.builder()
                .classroomId(3L).name("A1").capacity(20).location("B1")
                .state(StateEntity.builder().stateId(1L).stateDescription("ACTIVE").build())
                .build();

        Classroom classroom = classroomMapper.toClassroom(entity);

        assertThat(classroom.getName()).isEqualTo("A1");
        assertThat(classroom.getState().getStateDescription()).isEqualTo("ACTIVE");
        assertThat(classroomMapper.toClassroomEntity(classroom).getCapacity()).isEqualTo(20);
        assertThat(classroomMapper.toClassroom(null)).isNull();
    }

    @Test
    void userMapperMapsNestedRoleAndState() {
        UserEntity entity = UserEntity.builder()
                .userId(4L).name("John").lastName("Doe").email("j@b.com").password("x")
                .role(RoleEntity.builder().roleId(1L).roleDescription("ADMIN").build())
                .state(StateEntity.builder().stateId(1L).stateDescription("ACTIVE").build())
                .build();

        User user = userMapper.toUser(entity);

        assertThat(user.getEmail()).isEqualTo("j@b.com");
        assertThat(user.getRole().getRoleDescription()).isEqualTo("ADMIN");
        assertThat(user.getState().getStateDescription()).isEqualTo("ACTIVE");
        assertThat(userMapper.toEntity(user).getName()).isEqualTo("John");
        assertThat(userMapper.toUsers(List.of(entity))).hasSize(1);
    }

    @Test
    void bookingMapperMapsNestedGraph() {
        BookingEntity entity = BookingEntity.builder()
                .bookingId(5L)
                .bookingDate(LocalDate.of(2030, 1, 1))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .classroom(ClassroomEntity.builder().classroomId(3L).name("A1").build())
                .user(UserEntity.builder().userId(4L).name("John").build())
                .state(StateEntity.builder().stateId(1L).stateDescription("ACTIVE").build())
                .build();

        Booking booking = bookingMapper.toBooking(entity);

        assertThat(booking.getBookingId()).isEqualTo(5L);
        assertThat(booking.getClassroom().getName()).isEqualTo("A1");
        assertThat(booking.getUser().getName()).isEqualTo("John");
        assertThat(booking.getState().getStateDescription()).isEqualTo("ACTIVE");
        assertThat(bookingMapper.toBookingEntity(booking).getStartTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(bookingMapper.toBookings(List.of(entity))).hasSize(1);
        assertThat(bookingMapper.toBooking(null)).isNull();
    }
}
