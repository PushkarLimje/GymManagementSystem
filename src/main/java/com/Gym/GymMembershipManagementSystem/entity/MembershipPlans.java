package com.Gym.GymMembershipManagementSystem.entity;

import com.Gym.GymMembershipManagementSystem.entity.Selections.GymSpecialization;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long planId;

    @Column(nullable = false, unique = true)
    private String planName;

    @Column(nullable = false)
    private int durationMonths ;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 100)
    private String description;

    @ElementCollection(targetClass = GymSpecialization.class)
    @CollectionTable(
            name = "plan_specializations",
            joinColumns = @JoinColumn(name = "plan_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<GymSpecialization> includedSpecializations;

    @OneToOne(mappedBy = "plan")
    private Subscriptions subscription;


}
