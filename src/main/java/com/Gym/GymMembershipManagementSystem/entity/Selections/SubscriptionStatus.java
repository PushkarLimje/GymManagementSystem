package com.Gym.GymMembershipManagementSystem.entity.Selections;

public enum SubscriptionStatus {
    ACTIVE,        // Subscription is currently valid
    EXPIRED,       // End date crossed
    PAUSED,        // Temporarily stopped (medical / vacation)
    CANCELLED,     // Cancelled before expiry
    PENDING,       // Created but payment not completed
    SUSPENDED      // Blocked due to rule violation / admin action
}
