package com.Gym.GymMembershipManagementSystem.repository;

import com.Gym.GymMembershipManagementSystem.entity.Attendance;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByMemberAndAttendanceDate(Member member, LocalDate today);

    Optional<Attendance> findByMember_IdAndAttendanceDate(Long memberId, LocalDate today);

    List<Attendance> findByMemberOrderByAttendanceDateDesc(Member member);

    List<Attendance> findByAttendanceDate(LocalDate date);

    List<Attendance> findByMemberAndAttendanceDateBetween(
            Member member,
            LocalDate start,
            LocalDate end
    );
}