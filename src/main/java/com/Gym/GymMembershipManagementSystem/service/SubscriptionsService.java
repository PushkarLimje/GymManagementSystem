package com.Gym.GymMembershipManagementSystem.service;

import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionResponseDTO;
import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionUpdateDTO;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import com.Gym.GymMembershipManagementSystem.entity.MembershipPlans;
import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.entity.Trainer;
import com.Gym.GymMembershipManagementSystem.repository.SubscriptionsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionsService {

    public boolean hasActiveSubscription(Long memberId) ;

    public SubscriptionResponseDTO createSubscription(SubscriptionCreateDTO dto) ;

    public SubscriptionResponseDTO getSubscriptionById(Long subscriptionId) ;

    public SubscriptionResponseDTO getActiveSubscriptionByMember(Long memberId);

    public void cancelSubscription(Long subscriptionId);

    public void validateActiveSubscription(Long memberId);

    public SubscriptionResponseDTO updateSubscription(
            Long subscriptionId,
            SubscriptionUpdateDTO dto
    ) ;

    public SubscriptionResponseDTO renewSubscription(Long subscriptionId) ;

    public List<SubscriptionResponseDTO> getSubscriptionHistory(Long memberId) ;
}
