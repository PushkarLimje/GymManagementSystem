package com.Gym.GymMembershipManagementSystem.entity.Selections;

public enum AttendanceStatus {
    PRESENT,        // Checked in and checked out
    CHECKED_IN,     // Checked in but not checked out yet
    ABSENT,         // Did not come
    LATE,           // Checked in late
    LEAVE           // Approved leave
}
