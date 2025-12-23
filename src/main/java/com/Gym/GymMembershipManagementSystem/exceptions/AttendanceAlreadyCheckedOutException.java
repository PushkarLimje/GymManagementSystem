package com.Gym.GymMembershipManagementSystem.exceptions;

public class AttendanceAlreadyCheckedOutException extends RuntimeException {
    public AttendanceAlreadyCheckedOutException(String message) {
        super(message);
    }

    public AttendanceAlreadyCheckedOutException() {
        super("Attendance already checked out");
    }

}
