package com.Gym.GymMembershipManagementSystem.dto.MembershipPlanDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.GymSpecialization;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class MembershipPlanCreateDTO {

    @NotBlank(message = "Plan name is required")
    private String planName;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than zero")
    private Integer durationMonths;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be zero or greater")
    private BigDecimal price;

    private String description;

    @NotEmpty(message = "At least one specialization is required")
    private Set<GymSpecialization> includedSpecializations;
}
