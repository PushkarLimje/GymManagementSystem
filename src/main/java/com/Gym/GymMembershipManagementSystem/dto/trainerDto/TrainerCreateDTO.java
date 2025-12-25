package com.Gym.GymMembershipManagementSystem.dto.trainerDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.GymSpecialization;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TrainerCreateDTO {
//    Add trainer
    private String name;

    private GymSpecialization specialization;

    private String phone;

    private BigDecimal salary;
}
