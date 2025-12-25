package com.Gym.GymMembershipManagementSystem.appConfig;

import com.Gym.GymMembershipManagementSystem.entity.Attendance;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import com.Gym.GymMembershipManagementSystem.entity.Selections.AttendanceStatus;
import com.Gym.GymMembershipManagementSystem.entity.Selections.StatusMember;
import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.repository.AttendanceRepository;
import com.Gym.GymMembershipManagementSystem.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@EnableScheduling
@Configuration
@Service
@AllArgsConstructor
public class SchedulerConfig {
    private final MemberRepository memberRepository;
    private final AttendanceRepository attendanceRepository;

    // Runs daily at 23:59
    @Scheduled(cron = "0 59 23 * * ?")
    @Transactional
    public void markAbsentMembers() {

        LocalDate today = LocalDate.now();

        List<Member> activeMembers =
                memberRepository.findAll().stream()
                        .filter(m ->
                                m.getStatus() == StatusMember.ACTIVE &&
                                        m.getSubscription() != null &&
                                        m.getSubscription().getStatus() == SubscriptionStatus.ACTIVE
                        )
                        .toList();

        for (Member member : activeMembers) {

            boolean present =
                    attendanceRepository
                            .findByMemberAndAttendanceDate(member, today)
                            .isPresent();

            if (!present) {
                Attendance absent = Attendance.builder()
                        .member(member)
                        .attendanceDate(today)
                        .status(AttendanceStatus.ABSENT)
                        .build();

                attendanceRepository.save(absent);
            }
        }
    }
}
