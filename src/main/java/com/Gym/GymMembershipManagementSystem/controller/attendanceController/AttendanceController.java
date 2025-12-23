package com.Gym.GymMembershipManagementSystem.controller.attendanceController;

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
@ResponseBody
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/check-in/{memberId}")
    public ResponseEntity<AttendanceResponseDTO> markCheckIn(
            @PathVariable Long memberId){
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
        List<AttendanceResponseDTO> responseDTOList = attendanceService.getAllAttendance(memberId);
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDTOList);
    }

    @GetMapping("/today/{memberId}")
    public ResponseEntity<AttendanceResponseDTO> getTodayAttendance(
            @PathVariable Long memberId
    ){
        AttendanceResponseDTO response = attendanceService.getTodayAttendance(memberId);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceByDate(
            @PathVariable LocalDate date){
        List<AttendanceResponseDTO> response = attendanceService.getAttendanceByDate(date);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
}
