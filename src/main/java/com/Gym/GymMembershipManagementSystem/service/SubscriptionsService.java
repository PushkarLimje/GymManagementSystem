package com.Gym.GymMembershipManagementSystem.service;

import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.repository.SubscriptionsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class SubscriptionsService {

    private final SubscriptionsRepository subscriptionRepository;

    @Transactional
    public boolean hasActiveSubscription(Long memberId) {

        return subscriptionRepository
                .existsByMember_IdAndStatusAndEndDateGreaterThanEqual(
                        memberId,
                        SubscriptionStatus.ACTIVE,
                        LocalDate.now()
                );
    }


}
