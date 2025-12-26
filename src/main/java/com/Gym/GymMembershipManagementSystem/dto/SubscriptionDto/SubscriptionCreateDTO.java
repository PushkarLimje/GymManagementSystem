package com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SubscriptionCreateDTO {
//    Member selects plan
    private Long memberId;
    private Long membershipPlanId;
    private Long trainerId;
}
