package com.Gym.GymMembershipManagementSystem.AttendanceTest;

import com.Gym.GymMembershipManagementSystem.entity.Attendance;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import com.Gym.GymMembershipManagementSystem.entity.Selections.StatusMember;
import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.entity.Subscriptions;
import com.Gym.GymMembershipManagementSystem.repository.AttendanceRepository;
import com.Gym.GymMembershipManagementSystem.repository.MemberRepository;
import com.Gym.GymMembershipManagementSystem.service.AttendanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTest {
    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    @Test
    void shouldFailCheckIn_whenNoActiveSubscription() {

        Member member = Member.builder()
                .id(1L)
                .status(StatusMember.ACTIVE)
                .subscription(null)
                .build();

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        assertThrows(
                IllegalStateException.class,
                () -> attendanceService.markCheckIn(1L)
        );
    }

    @Test
    void shouldFail_whenAttendanceAlreadyExists() {

        Subscriptions activeSubscription = Subscriptions.builder()
                .status(SubscriptionStatus.ACTIVE)
                .build();

        Member member = Member.builder()
                .id(1L)
                .status(StatusMember.ACTIVE)
                .subscription(activeSubscription)
                .build();

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        when(attendanceRepository.findByMemberAndAttendanceDate(
                member, LocalDate.now())
        ).thenReturn(Optional.of(new Attendance()));

        assertThrows(
                IllegalStateException.class,
                () -> attendanceService.markCheckIn(1L)
        );
        verify(attendanceRepository, never())
                .save(any(Attendance.class));
    }

}
