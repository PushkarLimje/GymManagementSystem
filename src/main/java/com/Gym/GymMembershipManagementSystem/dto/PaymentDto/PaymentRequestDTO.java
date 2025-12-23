package com.Gym.GymMembershipManagementSystem.dto.PaymentDto;

import com.Gym.GymMembershipManagementSystem.entity.Member;
import com.Gym.GymMembershipManagementSystem.entity.Selections.PaymentGateway;
import com.Gym.GymMembershipManagementSystem.entity.Selections.PaymentStatus;
import com.Gym.GymMembershipManagementSystem.entity.Subscriptions;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
//    Make a payment

    private String transactionId;

    private Long memberId;
    private Long subscriptionId;

    private BigDecimal amount;
    private PaymentGateway paymentGateway;

    private String failureReason;
}
