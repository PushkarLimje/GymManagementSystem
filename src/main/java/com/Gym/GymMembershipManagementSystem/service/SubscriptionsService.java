package com.Gym.GymMembershipManagementSystem.service;

import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.entity.Subscriptions;
import com.Gym.GymMembershipManagementSystem.repository.SubscriptionsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class SubscriptionsService {

    private final SubscriptionsRepository subscriptionRepository;

    @Transactional
    public boolean hasActiveSubscription(Long memberId) {

        return subscriptionRepository
                .existsByMember_MemberIdAndStatusAndEndDateGreaterThanEqual(
                        memberId,
                        SubscriptionStatus.ACTIVE,
                        LocalDate.now()
                );
    }


}
