package com.Gym.GymMembershipManagementSystem.entity.Selections;

import lombok.AllArgsConstructor;

//@AllArgsConstructor
public enum PaymentMethod {
    CASH(false),
    UPI(true),
    DEBIT_CARD(true),
    CREDIT_CARD(true),
    NET_BANKING(true),
    WALLET(true);

    private final boolean online;

    PaymentMethod(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

}
