package com.Gym.GymMembershipManagementSystem.service;

import com.Gym.GymMembershipManagementSystem.dto.AttendanceDto.AttendanceResponseDTO;
import com.Gym.GymMembershipManagementSystem.entity.Attendance;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import com.Gym.GymMembershipManagementSystem.entity.Selections.AttendanceStatus;
import com.Gym.GymMembershipManagementSystem.exceptions.AttendanceAlreadyCheckedOutException;
import com.Gym.GymMembershipManagementSystem.repository.AttendanceRepository;
import com.Gym.GymMembershipManagementSystem.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public AttendanceResponseDTO markCheckIn(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(" Member Not Found "));
        LocalDate today = LocalDate.now();

        Optional<Attendance> existingAttendance = attendanceRepository.findByMemberAndAttendanceDate(member, today);

        if (existingAttendance.isPresent()){
            throw new RuntimeException("Check-in already marked for today");
        }

        Attendance attendance = new Attendance();
        attendance.setMember(member);
        attendance.setAttendanceDate(today);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.CHECKED_IN);
        Attendance saved = attendanceRepository.save(attendance);

//        List<Attendance> attendanceList =
        return modelMapper.map(saved , AttendanceResponseDTO.class);
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
    public AttendanceResponseDTO getTodayAttendance(Long memberId){
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByMember_MemberIdAndAttendanceDate(memberId, today);

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


}
