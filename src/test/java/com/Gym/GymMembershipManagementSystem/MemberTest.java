package com.Gym.GymMembershipManagementSystem;

import com.Gym.GymMembershipManagementSystem.dto.memberDto.MemberCreateDTO;
import com.Gym.GymMembershipManagementSystem.dto.memberDto.MemberResponseDTO;
import com.Gym.GymMembershipManagementSystem.dto.memberDto.MemberUpdateDTO;
import com.Gym.GymMembershipManagementSystem.entity.Member;
import com.Gym.GymMembershipManagementSystem.entity.Selections.Gender;
import com.Gym.GymMembershipManagementSystem.repository.MemberRepository;
import com.Gym.GymMembershipManagementSystem.service.MemberService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class MemberTest {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberService memberService;

//    @Test
//    public void testCreateMember(){
//        Member member = Member.builder()
//                .name("PushkarLimje")
//                .phone("8416846130")
//                .email("pushkar@gmail.com")
//                .joinDate(LocalDate.of(2025,12,24))
//                .gender(Gender.MALE)
//                .build();
//
//        MemberCreateDTO dto = modelMapper.map(member, MemberCreateDTO.class);
//
//        MemberCreateDTO  newMember = memberService.createMember(dto);
//        System.out.println(dto);
//    }

//    @Test
//    public void testUpdateMember(){
//        Member member = Member.builder()
//                .name("ShrutikaLimje")
//                .phone("8956895674")
//                .gender(Gender.FEMALE)
//                .build();
//        MemberUpdateDTO dto = modelMapper.map(member, MemberUpdateDTO.class);
//        MemberUpdateDTO newMember = memberService.updateMember(1L,dto);
//        System.out.println(dto);
//    }

    @Test
    public void testFindMember(){

        MemberResponseDTO memberResponseDTO = memberService.getMemberById(1L);

        System.out.println(memberResponseDTO);
    }

}
