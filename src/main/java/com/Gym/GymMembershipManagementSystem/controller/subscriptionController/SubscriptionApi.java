package com.Gym.GymMembershipManagementSystem.controller.subscriptionController;

import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionResponseDTO;
import com.Gym.GymMembershipManagementSystem.dto.SubscriptionDto.SubscriptionUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/subscriptions")
public interface SubscriptionApi {
    @PostMapping
    ResponseEntity<SubscriptionResponseDTO> createSubscription(
            @RequestBody SubscriptionCreateDTO dto
    );

    @PutMapping("/{subscriptionId}")
    ResponseEntity<SubscriptionResponseDTO> updateSubscription(
            @PathVariable Long subscriptionId,
            @RequestBody SubscriptionUpdateDTO dto
    );

    @PatchMapping("/{subscriptionId}/cancel")
    ResponseEntity<Void> cancelSubscription(
            @PathVariable Long subscriptionId
    );

    @PatchMapping("/{subscriptionId}/renew")
    ResponseEntity<SubscriptionResponseDTO> renewSubscription(
            @PathVariable Long subscriptionId
    );

    @GetMapping("/{subscriptionId}")
    ResponseEntity<SubscriptionResponseDTO> getSubscriptionById(
            @PathVariable Long subscriptionId
    );

    @GetMapping("/member/{memberId}/active")
    ResponseEntity<SubscriptionResponseDTO> getActiveSubscription(
            @PathVariable Long memberId
    );

    @GetMapping("/member/{memberId}/validate")
    ResponseEntity<Void> validateActiveSubscription(
            @PathVariable Long memberId
    );

    @GetMapping("/member/{memberId}/history")
    ResponseEntity<List<SubscriptionResponseDTO>> getSubscriptionHistory(
            @PathVariable Long memberId
    );
}
