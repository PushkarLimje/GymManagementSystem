package com.Gym.GymMembershipManagementSystem.controller.memberController;

import com.Gym.GymMembershipManagementSystem.dto.memberDto.MemberCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.memberDto.MemberResponseDTO;
import com.Gym.GymMembershipManagementSystem.dto.memberDto.MemberUpdateDTO;
import com.Gym.GymMembershipManagementSystem.entity.Selections.Gender;
import com.Gym.GymMembershipManagementSystem.entity.Selections.StatusMember;
import com.Gym.GymMembershipManagementSystem.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id){
        MemberResponseDTO response = memberService.getMemberById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<MemberUpdateDTO> updateMember(
            @PathVariable Long id,
            @RequestBody MemberUpdateDTO dto
    ){
        MemberUpdateDTO response = memberService.updateMember(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<MemberResponseDTO> createMember(
            @RequestBody
            @Valid MemberCreateDTO dto
    ){
        MemberResponseDTO response = memberService.createMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
