package com.Gym.GymMembershipManagementSystem.service.subcriptions;

import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionResponseDTO;
import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionUpdateDTO;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import com.Gym.GymMembershipManagementSystem.entity.MembershipPlans;
import com.Gym.GymMembershipManagementSystem.entity.Selections.SubscriptionStatus;
import com.Gym.GymMembershipManagementSystem.entity.Subscriptions;
import com.Gym.GymMembershipManagementSystem.entity.Trainer;
import com.Gym.GymMembershipManagementSystem.exceptions.SubscriptionNotFoundException;
import com.Gym.GymMembershipManagementSystem.repository.MemberRepository;
import com.Gym.GymMembershipManagementSystem.repository.MembershipPlansRepository;
import com.Gym.GymMembershipManagementSystem.repository.SubscriptionsRepository;
import com.Gym.GymMembershipManagementSystem.repository.TrainerRepository;
import com.Gym.GymMembershipManagementSystem.service.SubscriptionsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
@Transactional
public class SubscriptionService implements SubscriptionsService {

    private final SubscriptionsRepository subscriptionRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final MembershipPlansRepository membershipPlansRepository;
//    private final SubscriptionsService subscriptionsService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public boolean hasActiveSubscription(Long memberId) {

        return subscriptionRepository
                .existsByMember_IdAndStatusAndEndDateGreaterThanEqual(
                        memberId,
                        SubscriptionStatus.ACTIVE,
                        LocalDate.now()
                );
    }
    // ---------- validations ----------
    private Member validateMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(()-> new EntityNotFoundException("No Member found "));
    }

    private Trainer validateTrainer(Long trainerId){
        return trainerRepository.findById(trainerId)
                .orElseThrow(()-> new EntityNotFoundException("No Trainer found "));
    }

    private MembershipPlans validatePlan(Long planId){
        return membershipPlansRepository.findById(planId)
                .orElseThrow(()-> new EntityNotFoundException("No Membership Plan found "));
    }

    @Contract(" -> new")
    private @NotNull LocalDate calculateStartDate() {
        return LocalDate.now();
    }

    private @NotNull LocalDate calculateEndDate(
            LocalDate startDate,
            int durationMonths
    ) {
        if (durationMonths <= 0) {
            throw new IllegalStateException("Invalid plan duration");
        }
        return startDate.plusMonths(durationMonths);
    }

//    private void validateDates(LocalDate startDate, LocalDate endDate){
//        if (startDate == null || endDate == null) {
//            throw new IllegalArgumentException("Start date and end date must not be null");
//        }
//
//        if (startDate.isBefore(LocalDate.now())) {
//            throw new IllegalArgumentException("Start date cannot be in the past");
//        }
//
//        if (!endDate.isAfter(startDate)) {
//            throw new IllegalArgumentException("End date must be after start date");
//        }
//    }

    @Override
    public SubscriptionResponseDTO createSubscription(@NotNull SubscriptionCreateDTO dto) {
        Member member = validateMember(dto.getMemberId());
        MembershipPlans plan = validatePlan(dto.getMembershipPlanId());
        Trainer trainer = (dto.getTrainerId() != null)
                ? validateTrainer(dto.getTrainerId())
                : null;

//        validateActiveSubscription(member.getId());

        LocalDate startDate = calculateStartDate();
        LocalDate endDate = calculateEndDate(startDate, plan.getDurationMonths());

        Subscriptions subscription = Subscriptions.builder()
                .member(member)
                .plan(plan)
                .trainer(trainer)
                .startDate(startDate)
                .endDate(endDate)
                .status(SubscriptionStatus.ACTIVE)
                .build();


        subscriptionRepository.save(subscription);

        return mapToResponse(subscription);
    }

    @Override
    public SubscriptionResponseDTO getSubscriptionById(Long subscriptionId) {
        Subscriptions subscriptions = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(()-> new SubscriptionNotFoundException("Subscription Not Found"));

        return mapToResponse(subscriptions);
    }

    @Override
    @Transactional(readOnly = true)
    public SubscriptionResponseDTO getActiveSubscriptionByMember(Long memberId) {

        if (memberId == null) {
            throw new IllegalArgumentException("Member id must not be null");
        }
        Member member = validateMember(memberId);
//        ensureNoActiveSubscription(member.getId());

        Subscriptions subscription = subscriptionRepository
                .findByMember_IdAndStatusAndEndDateGreaterThanEqual(
                        memberId,
                        SubscriptionStatus.ACTIVE,
                        LocalDate.now()
                )
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No active subscription found for member id: " + memberId
                        )
                );

        return mapToResponse(subscription);
    }

    @Transactional
    @Override
    public void cancelSubscription(Long subscriptionId) {
        Subscriptions sub = subscriptionRepository.findById(subscriptionId).orElseThrow(()-> new SubscriptionNotFoundException("Subscription not found"));

        if (sub.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException("Only active subscriptions can be cancelled");
        }

        sub.setStatus(SubscriptionStatus.CANCELLED);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateActiveSubscription(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("Member id must not be null");
        }

        // 1. Ensure member exists
        validateMember(memberId);

        // 2. Ensure active, non-expired subscription exists
        boolean hasActive = subscriptionRepository
                .existsByMember_IdAndStatusAndEndDateGreaterThanEqual(
                        memberId,
                        SubscriptionStatus.ACTIVE,
                        LocalDate.now()
                );

        if (!hasActive) {
            throw new IllegalStateException(
                    "Member does not have an active subscription"
            );
        }
    }

    private  SubscriptionResponseDTO mapToResponse(Subscriptions subscription){
        return modelMapper
                .map(
                        subscription,
                        SubscriptionResponseDTO.class
                );
    }

    private void ensureNoActiveSubscription(Long memberId) {
        if (subscriptionRepository.existsByMember_IdAndStatusAndEndDateGreaterThanEqual(
                memberId,
                SubscriptionStatus.ACTIVE,
                LocalDate.now()
        )) {
            throw new IllegalStateException(
                    "Member already has an active subscription"
            );
        }
    }

    @Override
    @Transactional
    public SubscriptionResponseDTO updateSubscription(
            Long subscriptionId,
            SubscriptionUpdateDTO dto
    ) {
        Subscriptions subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() ->
                        new SubscriptionNotFoundException("Subscription not found"));

        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Only active subscriptions can be updated"
            );
        }

        // Update Plan
        if (dto.getPlanId() != null) {
            MembershipPlans newPlan = validatePlan(dto.getPlanId());

            subscription.setPlan(newPlan);
            subscription.setEndDate(
                    subscription.getStartDate()
                            .plusMonths(newPlan.getDurationMonths())
            );
        }

        // Update Trainer
        if (dto.getTrainerId() != null) {
            Trainer trainer = validateTrainer(dto.getTrainerId());
            subscription.setTrainer(trainer);
        }

        return mapToResponse(subscription);
    }

    @Override
    @Transactional
    public SubscriptionResponseDTO renewSubscription(Long subscriptionId) {

        Subscriptions sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() ->
                        new SubscriptionNotFoundException("Subscription not found"));

        if (sub.getStatus() != SubscriptionStatus.EXPIRED) {
            throw new IllegalStateException(
                    "Only expired subscriptions can be renewed"
            );
        }

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(
                sub.getPlan().getDurationMonths()
        );

        sub.setStartDate(startDate);
        sub.setEndDate(endDate);
        sub.setStatus(SubscriptionStatus.ACTIVE);

        return mapToResponse(sub);
    }


    @Transactional(readOnly = true)
    public List<SubscriptionResponseDTO> getSubscriptionHistory(Long memberId) {

        validateMember(memberId);

        return subscriptionRepository
                .findByMember_Id(memberId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

}
