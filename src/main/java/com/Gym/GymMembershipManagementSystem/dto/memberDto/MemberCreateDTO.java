package com.Gym.GymMembershipManagementSystem.dto.memberDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.Gender;
import com.Gym.GymMembershipManagementSystem.entity.Selections.StatusMember;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberCreateDTO {
//    Create new member
    @NotBlank(message = "Name is required ")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "phone number is required ")
    private String phone;

    @NotNull(message = "gender is essential ")
    private Gender gender;

    @NotNull(message = "Join Date is needed")
    private LocalDate joinDate;

    private StatusMember status;

}
