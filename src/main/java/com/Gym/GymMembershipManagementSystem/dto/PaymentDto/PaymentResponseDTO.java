package com.Gym.GymMembershipManagementSystem.dto.PaymentDto;

import com.Gym.GymMembershipManagementSystem.entity.Selections.PaymentStatus;
import lombok.Data;

@Data
public class PaymentResponseDTO {
//    Payment status
    private Long paymentId;
    private PaymentStatus status;
    private String message;
}
