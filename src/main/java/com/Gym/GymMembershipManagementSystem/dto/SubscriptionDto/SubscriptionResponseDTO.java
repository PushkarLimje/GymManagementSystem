package com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.StatusMember;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SubscriptionResponseDTO {
//    Show subscription details
    private Long subscriptionId;

    // Member info
    private Long memberId;
    private String memberName;
    private String memberEmail;

    // Plan info
    private Long planId;
    private String planName;
    private Integer durationMonths;
    private Double price;

    // Trainer info (optional)
    private Long trainerId;
    private String trainerName;

    // Subscription info
    private LocalDate startDate;
    private LocalDate endDate;
    private StatusMember status;
}
