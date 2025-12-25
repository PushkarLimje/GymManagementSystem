package com.Gym.GymMembershipManagementSystem.dto.MembershipPlanDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.GymSpecialization;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class MembershipPlanResponseDTO {
//    Show available plans
    private Long planId;
    private String planName;
    private Integer  durationMonths ;
    private BigDecimal price;
    private String description;
    private Set<GymSpecialization> includedSpecializations;
    private boolean active;

}
