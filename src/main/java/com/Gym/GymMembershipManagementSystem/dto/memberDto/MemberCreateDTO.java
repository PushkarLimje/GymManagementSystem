package com.Gym.GymMembershipManagementSystem.dto.memberDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.Gender;
import com.Gym.GymMembershipManagementSystem.entity.Selections.StatusMember;
import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberCreateDTO {
//    Create new member
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate joinDate;
    private StatusMember status;

}
