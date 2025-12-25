package com.Gym.GymMembershipManagementSystem.service;

import com.Gym.GymMembershipManagementSystem.dto.MembershipPlanDto.MembershipPlanCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.MembershipPlanDto.MembershipPlanResponseDTO;
import com.Gym.GymMembershipManagementSystem.dto.MembershipPlanDto.MembershipPlanUpdateDTO;
import com.Gym.GymMembershipManagementSystem.entity.MembershipPlans;
import com.Gym.GymMembershipManagementSystem.repository.MembershipPlansRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
@Service
@AllArgsConstructor
@Transactional
public class MembershipPlansService {

    private final MembershipPlansRepository membershipPlansRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public MembershipPlanResponseDTO createMembershipPlan(
            @NotNull MembershipPlanCreateDTO dto
    ){
        if (membershipPlansRepository.existsByPlanName(dto.getPlanName())) {
            throw new IllegalArgumentException("Membership plan already exists");
        }
        MembershipPlans membershipPlans = MembershipPlans.builder()
                .planName(dto.getPlanName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .durationMonths(dto.getDurationMonths())
                .includedSpecializations(dto.getIncludedSpecializations())
                .active(true)
                .build();

        MembershipPlans savedPlan = membershipPlansRepository.save(membershipPlans);
        return modelMapper.map(savedPlan, MembershipPlanResponseDTO.class);
    }

    @Transactional
    public MembershipPlanResponseDTO updateMembershipPlan(
            Long planId,
            @NotNull MembershipPlanUpdateDTO dto
    ){
        MembershipPlans membershipPlans = membershipPlansRepository
                .findById(planId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Membership plan not found: " + planId)
                );

        if (dto.getPlanName() != null) {
            membershipPlans.setPlanName(dto.getPlanName());
        }

        if (dto.getPrice() != null) {
            membershipPlans.setPrice(dto.getPrice());
        }

        if (dto.getDescription() != null) {
            membershipPlans.setDescription(dto.getDescription());
        }

        if (dto.getDurationMonths() != null) {
            membershipPlans.setDurationMonths(dto.getDurationMonths());
        }

        if (dto.getIncludedSpecializations() != null) {
            membershipPlans.setIncludedSpecializations(dto.getIncludedSpecializations());
        }

        MembershipPlans updatedPlan =
                membershipPlansRepository.save(membershipPlans);

        return modelMapper.map(updatedPlan, MembershipPlanResponseDTO.class);
    }

    public List<MembershipPlanResponseDTO> getAllPlans() {
        return membershipPlansRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, MembershipPlanResponseDTO.class))
                .toList();
    }

    @Transactional
    public void deleteMembershipPlan(Long id){
        MembershipPlans membershipPlans = membershipPlansRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Membership Plan not found "));

        if (membershipPlans.getSubscription() != null) {
            throw new IllegalStateException("Plan is currently in use");
        }

         membershipPlans.setActive(false);       //  SOFT DELETE

//        System.out.println(membershipPlans);
//        membershipPlansRepository.delete(membershipPlans);
    }

    public MembershipPlanResponseDTO getMembershipPlan(
            Long planId
    ){
        MembershipPlans membershipPlans = membershipPlansRepository.findById(planId)
                .orElseThrow(() ->
                new RuntimeException("Membership Plan not found")
        );

        return modelMapper.map(membershipPlans, MembershipPlanResponseDTO.class);
    }

    public List<MembershipPlanResponseDTO> getActivePlans() {
        return membershipPlansRepository.findAll()
                .stream()
                .filter(MembershipPlans::isActive)
                .map(plan -> modelMapper.map(plan, MembershipPlanResponseDTO.class))
                .toList();
    }

    public MembershipPlans validatePlanForSubscription(Long planId) {

        MembershipPlans plan = membershipPlansRepository.findById(planId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Plan not found")
                );

        if (!plan.isActive()) {
            throw new IllegalStateException("Plan is inactive");
        }

        return plan;   // Return entity for SubscriptionService
    }

    public void deactivatePlan(Long planId) {
        MembershipPlans plan = membershipPlansRepository.findById(planId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Plan not found")
                );

        plan.setActive(false);
    }

    public void activatePlan(Long planId) {
        MembershipPlans plan = membershipPlansRepository.findById(planId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Plan not found")
                );

        plan.setActive(true);
    }


}
