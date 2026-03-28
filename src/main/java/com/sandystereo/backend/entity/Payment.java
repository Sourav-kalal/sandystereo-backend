package com.sandystereo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import com.sandystereo.backend.enums.PaymentCategory;
import com.sandystereo.backend.enums.PaymentType;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name = "payments")
public class Payment {
    @Id @GeneratedValue @UuidGenerator private UUID id;
    @ManyToOne(optional = true) @JoinColumn(name = "user_id") private User user;
    @ManyToOne(optional = true) @JoinColumn(name = "course_id") private Course course;
    @Column(nullable = false) private BigDecimal amount;
    @Column(nullable = false) private String status = "completed";
    private String transactionReference;
    @Enumerated(EnumType.STRING) private PaymentType type = PaymentType.REVENUE;
    @Enumerated(EnumType.STRING) private PaymentCategory category = PaymentCategory.COURSE_FEE;
    private String title;
    @Column(nullable = false, updatable = false) private OffsetDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public PaymentType getType() { return type; }
    public void setType(PaymentType type) { this.type = type; }

    public PaymentCategory getCategory() { return category; }
    public void setCategory(PaymentCategory category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @PrePersist void prePersist(){createdAt=OffsetDateTime.now();}
}
