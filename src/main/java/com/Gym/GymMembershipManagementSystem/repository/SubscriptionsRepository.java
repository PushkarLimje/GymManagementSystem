package com.Gym.GymMembershipManagementSystem.repository;

import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.entity.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long> {
    boolean existsByMember_IdAndStatusAndEndDateGreaterThanEqual(
            Long memberId,
            SubscriptionStatus subscriptionStatus,
            LocalDate now);

    List<Subscriptions> findByStatus(SubscriptionStatus subscriptionStatus);

    Optional<Subscriptions> findByMember_IdAndStatusAndEndDateGreaterThanEqual(
            Long memberId,
            SubscriptionStatus subscriptionStatus,
            LocalDate now
    );

    List<Subscriptions> findByStatusAndEndDateBefore(SubscriptionStatus subscriptionStatus, LocalDate now);

    List<Subscriptions> findByMember_Id(Long memberId);
}