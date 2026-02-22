package com.sandystereo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @Entity @Table(name = "payments")
public class Payment {
    @Id @GeneratedValue @UuidGenerator private UUID id;
    @ManyToOne(optional = false) @JoinColumn(name = "user_id") private User user;
    @ManyToOne(optional = false) @JoinColumn(name = "course_id") private Course course;
    @Column(nullable = false) private BigDecimal amount;
    @Column(nullable = false) private String status = "pending";
    private String transactionReference;
    @Column(nullable = false) private OffsetDateTime createdAt;
    @PrePersist void prePersist(){createdAt=OffsetDateTime.now();}
}
