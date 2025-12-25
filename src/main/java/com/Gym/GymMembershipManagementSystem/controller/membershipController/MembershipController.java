package com.Gym.GymMembershipManagementSystem.controller.membershipController;

import com.Gym.GymMembershipManagementSystem.dto.MembershipPlanDto.MembershipPlanCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.MembershipPlanDto.MembershipPlanResponseDTO;
import com.Gym.GymMembershipManagementSystem.dto.MembershipPlanDto.MembershipPlanUpdateDTO;
import com.Gym.GymMembershipManagementSystem.service.MembershipPlansService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/plans")
public class MembershipController {

    private final MembershipPlansService membershipPlansService;

    @PostMapping("")
    public ResponseEntity<MembershipPlanResponseDTO> createPlan(
            @RequestBody @Valid MembershipPlanCreateDTO dto
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(membershipPlansService.createMembershipPlan(dto));
    }

    @PutMapping("/{planId}")
    public ResponseEntity<MembershipPlanResponseDTO> updatePlan(
            @PathVariable Long planId,
            @RequestBody MembershipPlanUpdateDTO dto
    ) {
        return ResponseEntity.ok(
                membershipPlansService.updateMembershipPlan(planId, dto)
        );
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(
            @PathVariable Long planId
    ) {
        membershipPlansService.deleteMembershipPlan(planId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{planId}")
    public ResponseEntity<MembershipPlanResponseDTO> getMembershipPlan(
            @PathVariable Long planId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(membershipPlansService
                        .getMembershipPlan(planId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<MembershipPlanResponseDTO>> getActivePlans() {
        return ResponseEntity.ok(membershipPlansService.getActivePlans());
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePlan(@PathVariable Long id) {
        membershipPlansService.deactivatePlan(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activatePlan(@PathVariable Long id) {
        membershipPlansService.activatePlan(id);
        return ResponseEntity.ok().build();
    }



}
