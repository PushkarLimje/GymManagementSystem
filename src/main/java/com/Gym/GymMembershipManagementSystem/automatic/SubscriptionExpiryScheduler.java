package com.Gym.GymMembershipManagementSystem.automatic;

import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.entity.Subscriptions;
import com.Gym.GymMembershipManagementSystem.repository.SubscriptionsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionExpiryScheduler {
    private final SubscriptionsRepository subscriptionRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // daily at 12:00 AM
    public void expireSubscriptions() {

        List<Subscriptions> subscriptions =
                subscriptionRepository.findByStatus(SubscriptionStatus.ACTIVE);

        subscriptions.forEach(subscription -> {
            if (subscription.getEndDate().isBefore(LocalDate.now())) {
                subscription.setStatus(SubscriptionStatus.EXPIRED);
            }
        });
    }
}
