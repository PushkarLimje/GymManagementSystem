package com.Gym.GymMembershipManagementSystem.entity.Selections;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentGateway {
    RAZORPAY(true, true),
    STRIPE(false, true),
    PAYPAL(false, true),
    CASHFREE(true, true),
    PAYTM(true, true),
    PHONEPE(true, true);

    private final boolean indian;
    private final boolean supportsUPI;

}
