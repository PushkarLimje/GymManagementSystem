package com.Gym.GymMembershipManagementSystem.dto.AttendanceDto;

import lombok.Data;

@Data
public class AttendanceMonthlyReportDTO {
    private Long memberId;
    private String memberName;
    private int totalDays;
    private int presentDays;
    private int absentDays;
    private double attendancePercentage;
}
