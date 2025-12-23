package com.Gym.GymMembershipManagementSystem.repository;

import com.Gym.GymMembershipManagementSystem.entity.MembershipPlans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipPlansRepository extends JpaRepository<MembershipPlans, Long> {
}