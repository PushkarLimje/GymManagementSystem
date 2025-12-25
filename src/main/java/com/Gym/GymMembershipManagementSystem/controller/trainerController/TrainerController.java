package com.Gym.GymMembershipManagementSystem.controller.trainerController;

import com.Gym.GymMembershipManagementSystem.dto.trainerDto.TrainerCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.trainerDto.TrainerResponseDTO;
import com.Gym.GymMembershipManagementSystem.dto.trainerDto.TrainerUpdateDTO;
import com.Gym.GymMembershipManagementSystem.service.TrainerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainers")
@AllArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;

    // ---------------- CREATE ----------------
    @PostMapping
    public ResponseEntity<TrainerResponseDTO> createTrainer(
            @RequestBody @Valid TrainerCreateDTO dto
    ) {
        TrainerResponseDTO response = trainerService.createTrainer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ---------------- GET BY ID ----------------
    @GetMapping("/{trainerId}")
    public ResponseEntity<TrainerResponseDTO> getTrainerById(
            @PathVariable Long trainerId
    ) {
        return ResponseEntity.ok(
                trainerService.getTrainerById(trainerId)
        );
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{trainerId}")
    public ResponseEntity<Void> deleteTrainer(
            @PathVariable Long trainerId
    ) {
        trainerService.deleteTrainer(trainerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{trainerId}")
    public ResponseEntity<TrainerResponseDTO> updateTrainer(
            @PathVariable Long trainerId,
            @RequestBody TrainerUpdateDTO dto
    ) {
        return ResponseEntity.ok(
                trainerService.updateTrainer(trainerId, dto)
        );
    }

}
