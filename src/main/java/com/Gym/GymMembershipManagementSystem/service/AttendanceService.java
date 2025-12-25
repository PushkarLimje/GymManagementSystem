package com.Gym.GymMembershipManagementSystem.service;

import com.Gym.GymMembershipManagementSystem.dto.AttendanceDto.AttendanceMonthlyReportDTO;
import com.Gym.GymMembershipManagementSystem.dto.AttendanceDto.AttendanceResponseDTO;
import com.Gym.GymMembershipManagementSystem.entity.Attendance;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import com.Gym.GymMembershipManagementSystem.entity.Selections.AttendanceStatus;
import com.Gym.GymMembershipManagementSystem.entity.Selections.StatusMember;
import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.exceptions.AttendanceAlreadyCheckedOutException;
import com.Gym.GymMembershipManagementSystem.repository.AttendanceRepository;
import com.Gym.GymMembershipManagementSystem.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@Setter
//@Configuration
@Service
@AllArgsConstructor
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public AttendanceResponseDTO markCheckIn(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(" Member Not Found "));
        LocalDate today = LocalDate.now();

        //  BUSINESS RULE 1
        if (member.getStatus() != StatusMember.ACTIVE) {
            throw new IllegalStateException("Inactive member cannot check in");
        }

        //  BUSINESS RULE 2
        if (member.getSubscription() == null ||
                member.getSubscription().getStatus() != SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException("Active subscription required");
        }

        // BUSINESS RULE 3: One attendance per day
        attendanceRepository.findByMemberAndAttendanceDate(member, today).ifPresent(a -> {
            throw new IllegalStateException("Already checked in today");
        });

        Attendance attendance = Attendance.builder()
                .member(member)
                .attendanceDate(today)
                .status(AttendanceStatus.CHECKED_IN)
                .build();
        Attendance saved = attendanceRepository.save(attendance);

//        List<Attendance> attendanceList =
        return toResponseDTO(attendanceRepository.save(attendance));
    }

    @Transactional
    public void markCheckOut(Long attendanceId){
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(()-> new IllegalArgumentException("Attendance record not found "));

        if (attendance.getCheckOutTime() != null)
            throw new AttendanceAlreadyCheckedOutException("Member has already checked out at "+ attendance.getCheckOutTime());

        attendance.setCheckOutTime(LocalDateTime.now());
        attendanceRepository.save(attendance);
        attendance.setStatus(AttendanceStatus.PRESENT);

    }


    @Transactional
    public List<AttendanceResponseDTO> getAllAttendance(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(" Member Not Found "));

    // returns all the days present
        return attendanceRepository.findByMemberOrderByAttendanceDateDesc(member)
            .stream()
                .map(a -> modelMapper.map(a, AttendanceResponseDTO.class))
            .toList();
    }


    @Transactional
    public AttendanceResponseDTO getTodayAttendance(Long memberId){
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByMember_IdAndAttendanceDate(memberId, today)
                .orElseThrow(() ->
                new RuntimeException("No attendance marked today")
        );

        return modelMapper.map(attendance, AttendanceResponseDTO.class);
    }

    @Transactional
    public List<AttendanceResponseDTO>getAttendanceByDate(LocalDate date){
        List<Attendance> attendanceList = attendanceRepository.findByAttendanceDate(date);

        return attendanceList
                .stream()
                .map(a->modelMapper.map(a, AttendanceResponseDTO.class))
                .toList();
    }

    private @NotNull AttendanceResponseDTO toResponseDTO(Attendance attendance) {

        AttendanceResponseDTO dto =
                modelMapper.map(attendance, AttendanceResponseDTO.class);

        dto.setMemberId(attendance.getMember().getId());
        dto.setMemberName(attendance.getMember().getName());

        return dto;
    }

    public AttendanceMonthlyReportDTO getMonthlyReport(
            Long memberId,
            int year,
            int month
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Attendance> records =
                attendanceRepository.findByMemberAndAttendanceDateBetween(
                        member, start, end
                );

        long present =
                records.stream()
                        .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                        .count();

        long absent =
                records.stream()
                        .filter(a -> a.getStatus() == AttendanceStatus.ABSENT)
                        .count();

        AttendanceMonthlyReportDTO dto = new AttendanceMonthlyReportDTO();
        dto.setMemberId(member.getId());
        dto.setMemberName(member.getName());
        dto.setTotalDays(records.size());
        dto.setPresentDays((int) present);
        dto.setAbsentDays((int) absent);
        dto.setAttendancePercentage(
                records.isEmpty() ? 0 :
                        (present * 100.0) / records.size()
        );

        return dto;
    }


}
