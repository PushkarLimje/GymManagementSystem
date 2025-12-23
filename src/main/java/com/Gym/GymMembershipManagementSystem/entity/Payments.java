package com.Gym.GymMembershipManagementSystem.entity;

import com.Gym.GymMembershipManagementSystem.entity.Selections.PaymentGateway;
import com.Gym.GymMembershipManagementSystem.entity.Selections.PaymentMethod;
import com.Gym.GymMembershipManagementSystem.entity.Selections.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@Entity
@Table(name = "payments",
        indexes = {
                @Index(name = "idx_payment_status", columnList = "paymentStatus"),
                @Index(name = "idx_payment_date", columnList = "paymentDate")
        })
@NoArgsConstructor
@AllArgsConstructor
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(unique = true)
    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscriptions subscriptions;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentGateway paymentGateway;

    @Column(unique = true, nullable = false)
    private String gatewayPaymentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private String failureReason;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime paymentDate;
}
