package com.Gym.GymMembershipManagementSystem.controller.subscriptionController;

import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionUpdateDTO;
import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionResponseDTO;
import com.Gym.GymMembershipManagementSystem.service.SubscriptionsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@AllArgsConstructor
public class SubscriptionController implements SubscriptionApi{
    private final SubscriptionsService subscriptionService;


    @Override
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(SubscriptionCreateDTO dto) {
        return new ResponseEntity<>(
                subscriptionService.createSubscription(dto),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<SubscriptionResponseDTO> updateSubscription(Long subscriptionId, SubscriptionUpdateDTO dto) {
        return ResponseEntity.ok(
                subscriptionService.updateSubscription(subscriptionId, dto)
        );
    }

    @Override
    public ResponseEntity<Void> cancelSubscription(Long subscriptionId) {
        subscriptionService.cancelSubscription(subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<SubscriptionResponseDTO> renewSubscription(Long subscriptionId) {
        return ResponseEntity.ok(
                subscriptionService.renewSubscription(subscriptionId)
        );
    }

    @Override
    public ResponseEntity<SubscriptionResponseDTO> getSubscriptionById(Long subscriptionId) {
        return ResponseEntity.ok(
                subscriptionService.getSubscriptionById(subscriptionId)
        );
    }

    @Override
    public ResponseEntity<SubscriptionResponseDTO> getActiveSubscription(Long memberId) {
        return ResponseEntity.ok(
                subscriptionService.getActiveSubscriptionByMember(memberId)
        );
    }

    @Override
    public ResponseEntity<Void> validateActiveSubscription(Long memberId) {
        subscriptionService.validateActiveSubscription(memberId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<SubscriptionResponseDTO>> getSubscriptionHistory(Long memberId) {
        return ResponseEntity.ok(
                subscriptionService.getSubscriptionHistory(memberId)
        );
    }
}
