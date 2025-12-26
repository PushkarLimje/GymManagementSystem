package com.Gym.GymMembershipManagementSystem.automatic;

import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.entity.Subscriptions;
import com.Gym.GymMembershipManagementSystem.repository.SubscriptionsRepository;
import jakarta.persistence.Version;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class SubscriptionExpiryScheduler {

    private final SubscriptionsRepository subscriptionRepository;
    @Version
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // daily at 12:00 AM
    public void expireSubscriptions() {

        List<Subscriptions> expiredSubscriptions  =
                subscriptionRepository.findByStatusAndEndDateBefore(
                        SubscriptionStatus.ACTIVE,
                        LocalDate.now()
                );


        for (Subscriptions subscription : expiredSubscriptions) {
            subscription.setStatus(SubscriptionStatus.EXPIRED);
        }

        log.info("Expired {} subscriptions", expiredSubscriptions.size());
    }
}
