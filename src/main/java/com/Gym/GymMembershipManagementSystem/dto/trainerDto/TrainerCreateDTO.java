package com.Gym.GymMembershipManagementSystem.dto.trainerDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.GymSpecialization;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TrainerCreateDTO {
//    Add trainer
    @NotBlank
    private String name;

    @NotNull
    private GymSpecialization specialization;

    @NotBlank
    @Size(max = 15)
    private String phone;

    @NotNull
    @PositiveOrZero
    private BigDecimal salary;
}
