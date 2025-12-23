package com.Gym.GymMembershipManagementSystem.dto.AttendanceDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.AttendanceStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceResponseDTO {
//    Show attendance history
    private Long attendanceId;

    // Member / Student info (avoid full entity)
    private Long memberId;
    private String memberName;

    // Attendance details
    private LocalDate attendanceDate;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    // Optional status
    private AttendanceStatus status;
}
