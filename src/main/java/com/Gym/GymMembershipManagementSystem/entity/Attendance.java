package com.Gym.GymMembershipManagementSystem.entity;

import com.Gym.GymMembershipManagementSystem.entity.Selections.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(
        name = "attendance",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_member_attendance_date",
                        columnNames = {"member_id", "attendance_date"}
                )
        },
        indexes = {
                @Index(name = "idx_attendance_date", columnList = "attendance_date")
        }
        )
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime checkInTime;

    private  LocalDateTime checkOutTime;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

}
