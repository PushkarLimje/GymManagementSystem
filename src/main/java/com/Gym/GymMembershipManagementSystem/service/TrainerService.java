package com.Gym.GymMembershipManagementSystem.service;

import com.Gym.GymMembershipManagementSystem.dto.trainerDto.TrainerCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.trainerDto.TrainerResponseDTO;
import com.Gym.GymMembershipManagementSystem.dto.trainerDto.TrainerUpdateDTO;
import com.Gym.GymMembershipManagementSystem.entity.Trainer;
import com.Gym.GymMembershipManagementSystem.repository.TrainerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Transactional
@Builder
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    // ---------------- CREATE ----------------
    public TrainerResponseDTO createTrainer(@NotNull TrainerCreateDTO dto) {

        // BUSINESS RULE 1: Unique phone
        if (trainerRepository.existsByPhone(dto.getPhone())) {
            throw new IllegalArgumentException("Trainer phone already exists");
        }

        // BUSINESS RULE 2: Salary must be non-negative
        if (dto.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        Trainer trainer = Trainer.builder()
                .name(dto.getName())
                .specialization(dto.getSpecialization())
                .phone(dto.getPhone())
                .salary(dto.getSalary())
                .build();

        return modelMapper.map(
                trainerRepository.save(trainer),
                TrainerResponseDTO.class
        );
    }

    // ---------------- GET ----------------
    public TrainerResponseDTO getTrainerById(Long trainerId) {

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        return modelMapper.map(trainer, TrainerResponseDTO.class);
    }

    @Transactional
    public TrainerResponseDTO updateTrainer(
            Long trainerId,
            TrainerUpdateDTO dto
    ) {

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        // OPTIONAL BUSINESS RULE:
        // prevent update if trainer already assigned
        if (trainer.getSubscription() != null) {
            throw new IllegalStateException("Assigned trainer cannot be updated");
        }

        if (dto.getName() != null && !dto.getName().isBlank()) {
            trainer.setName(dto.getName());
        }

        if (dto.getSpecialization() != null) {
            trainer.setSpecialization(dto.getSpecialization());
        }

        if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
            if (trainerRepository.existsByPhone(dto.getPhone())) {
                throw new IllegalArgumentException("Trainer phone already exists");
            }
            trainer.setPhone(dto.getPhone());
        }

        if (dto.getSalary() != null) {
            if (dto.getSalary().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Salary cannot be negative");
            }
            trainer.setSalary(dto.getSalary());
        }

        return modelMapper.map(trainer, TrainerResponseDTO.class);
    }


    // ---------------- DELETE ----------------
    public void deleteTrainer(Long trainerId) {

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        // BUSINESS RULE 3: Cannot delete assigned trainer
        if (trainer.getSubscription() != null) {
            throw new IllegalStateException(
                    "Trainer is assigned to a subscription"
            );
        }

        trainerRepository.delete(trainer);
    }

    // ---------------- VALIDATION FOR SUBSCRIPTION ----------------
    public Trainer validateTrainerForSubscription(Long trainerId) {

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        // BUSINESS RULE 4: Trainer can handle only one subscription
        if (trainer.getSubscription() != null) {
            throw new IllegalStateException("Trainer already assigned");
        }

        return trainer; // used by SubscriptionService
    }

}
