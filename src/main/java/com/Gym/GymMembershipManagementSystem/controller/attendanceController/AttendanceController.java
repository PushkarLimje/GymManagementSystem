package com.Gym.GymMembershipManagementSystem.controller.attendanceController;

import com.Gym.GymMembershipManagementSystem.dto.AttendanceDto.AttendanceMonthlyReportDTO;
import com.Gym.GymMembershipManagementSystem.dto.AttendanceDto.AttendanceResponseDTO;
import com.Gym.GymMembershipManagementSystem.service.AttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/check-in/{memberId}")
    public ResponseEntity<AttendanceResponseDTO> markCheckIn(
            @PathVariable Long memberId
    ){
        AttendanceResponseDTO attendanceResponseDTO = attendanceService.markCheckIn(memberId);
        return ResponseEntity.status(HttpStatus.FOUND).body(attendanceResponseDTO);
    }

    @PutMapping("/check-out/{attendanceId}")
    public ResponseEntity<Void> markCheckOut(@PathVariable Long attendanceId) {
        attendanceService.markCheckOut(attendanceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/history/{memberId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getAllAttendance(
            @PathVariable Long memberId
    ){
        return ResponseEntity.ok(
                attendanceService.getAllAttendance(memberId)
        );
    }

    @GetMapping("/today/{memberId}")
    public ResponseEntity<AttendanceResponseDTO> getTodayAttendance(
            @PathVariable Long memberId
    ){
        return ResponseEntity.ok(
                attendanceService.getTodayAttendance(memberId)
        );
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceByDate(
            @PathVariable LocalDate date){
        List<AttendanceResponseDTO> response = attendanceService.getAttendanceByDate(date);
        return ResponseEntity.ok(
                attendanceService.getAttendanceByDate(date)
        );
    }

    @GetMapping("/report/{memberId}/{year}/{month}")
    public ResponseEntity<AttendanceMonthlyReportDTO> getMonthlyReport(
            @PathVariable Long memberId,
            @PathVariable int year,
            @PathVariable int month
    ) {
        return ResponseEntity.ok(
                attendanceService.getMonthlyReport(memberId, year, month)
        );
    }


}
