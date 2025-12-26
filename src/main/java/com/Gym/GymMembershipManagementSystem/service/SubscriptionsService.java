package com.Gym.GymMembershipManagementSystem.service;

import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionResponseDTO;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import com.Gym.GymMembershipManagementSystem.entity.MembershipPlans;
import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.entity.Trainer;
import com.Gym.GymMembershipManagementSystem.repository.SubscriptionsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

public interface SubscriptionsService {

    public boolean hasActiveSubscription(Long memberId) ;

    public SubscriptionResponseDTO createSubscription(SubscriptionCreateDTO dto) ;

    public SubscriptionResponseDTO getSubscriptionById(Long subscriptionId) ;

    public SubscriptionResponseDTO getActiveSubscriptionByMember(Long memberId);

    public void cancelSubscription(Long subscriptionId);

    public void validateActiveSubscription(Long memberId);
}
