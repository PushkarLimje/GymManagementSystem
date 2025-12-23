package com.Gym.GymMembershipManagementSystem.repository;

import com.Gym.GymMembershipManagementSystem.dto.memberDto.MemberResponseDTO;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    MemberResponseDTO getById();

    boolean existsByEmail(String email);

    boolean existsByPhoneAndMemberIdNot(String phone, Long memberId);
}