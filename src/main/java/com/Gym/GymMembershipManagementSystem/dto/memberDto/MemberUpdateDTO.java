package com.Gym.GymMembershipManagementSystem.dto.memberDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.Gender;
import com.Gym.GymMembershipManagementSystem.entity.Selections.StatusMember;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberUpdateDTO {
//    Update member profile
    private String name;
    private String phone;
    private Gender gender;
    private StatusMember status;

}
