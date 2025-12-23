package com.Gym.GymMembershipManagementSystem.dto.trainerDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.GymSpecialization;
import lombok.Data;

@Data
public class TrainerCreateDTO {
//    Add trainer
    private String name;

    private GymSpecialization specialization;

    private String phone;

    private int salary;
}
