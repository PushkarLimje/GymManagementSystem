package com.Gym.GymMembershipManagementSystem.dto.memberDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.Gender;
import com.Gym.GymMembershipManagementSystem.entity.Selections.StatusMember;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberResponseDTO {
//    Send member data to client
    private Long memberId;
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private StatusMember status;
    private LocalDate joinDate;
}
