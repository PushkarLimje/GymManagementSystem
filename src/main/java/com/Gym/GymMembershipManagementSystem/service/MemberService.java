package com.Gym.GymMembershipManagementSystem.service;

import com.Gym.GymMembershipManagementSystem.dto.memberDto.MemberCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.memberDto.MemberResponseDTO;
import com.Gym.GymMembershipManagementSystem.dto.memberDto.MemberUpdateDTO;
import com.Gym.GymMembershipManagementSystem.entity.Attendance;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import com.Gym.GymMembershipManagementSystem.entity.Selections.StatusMember;
import com.Gym.GymMembershipManagementSystem.exceptions.MemberAccessException;
import com.Gym.GymMembershipManagementSystem.repository.MemberRepository;
import com.Gym.GymMembershipManagementSystem.repository.SubscriptionsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Builder
@Service
@AllArgsConstructor
@Transactional
public class MemberService {
    private SubscriptionsRepository subscriptionsRepository;
    private MemberRepository memberRepository;
    private ModelMapper modelMapper;

//    validateActiveMember(Long memberId)
//    hasActiveSubscription(Long memberId)
    @Transactional
    public MemberResponseDTO getMemberById(Long memberId) {
        Member member =  memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return modelMapper.map(member, MemberResponseDTO.class);
    }

    @Transactional
    public MemberResponseDTO createMember(
            @NotNull MemberCreateDTO dto
    ){
        if (memberRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("Email is present ");
        }
//        BUSINESS RULE 4: Default member status on creation
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .joinDate(dto.getJoinDate())
                .status(StatusMember.ACTIVE)
                .build();

        Member savedMember = memberRepository.save(member);
        return modelMapper.map(savedMember, MemberResponseDTO.class);
    }

    public void validateActiveMember(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (member.getStatus() != StatusMember.ACTIVE) {
            throw new IllegalStateException("Member is not active");
        }
    }

    @Transactional
    public MemberUpdateDTO updateMember(Long memberId, @NotNull MemberUpdateDTO dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

//        validateMemberAccess(member.getId()L);
        validateMemberAccess(member);

        if (dto.getName() != null && !dto.getName().isBlank()) {
            member.setName(dto.getName());
        }

        if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
            if (memberRepository.existsByPhoneAndIdNot(dto.getPhone(), memberId)) {
                throw new RuntimeException("Phone already in use");
            }
            member.setPhone(dto.getPhone());
        }
        if (dto.getGender() != null) {
            member.setGender(dto.getGender());
        }


        return modelMapper.map(member, MemberUpdateDTO.class);
    }

    @Transactional
    public void validateMemberAccess(@NotNull Member member) {
//        BUSINESS RULE 5: Suspended members have NO access
        if (member.getStatus() == StatusMember.SUSPENDED) {
            throw new MemberAccessException( "Member is suspended");
        }

        if (member.getStatus() == StatusMember.INACTIVE) {
            throw new MemberAccessException("Member is inactive");
        }
    }

}
