package com.Gym.GymMembershipManagementSystem.dto.MembershipPlanDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.GymSpecialization;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class MembershipPlanUpdateDTO {
    private String planName;
    @Positive(message = "Duration must be greater than zero")
    private Integer durationMonths;

    @PositiveOrZero(message = "Price must be zero or greater")
    private BigDecimal price;

    private String description;

    private Set<GymSpecialization> includedSpecializations;
}
