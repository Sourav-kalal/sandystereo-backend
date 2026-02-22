package com.sandystereo.backend.entity;

import com.sandystereo.backend.enums.CourseLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @Entity @Table(name = "courses")
public class Course {
    @Id @GeneratedValue @UuidGenerator private UUID id;
    @Column(nullable = false) private String title;
    @Column(nullable = false, columnDefinition = "TEXT") private String description;
    private String imageUrl;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private CourseLevel level = CourseLevel.Beginner;
    private String duration;
    @Column(nullable = false) private Boolean isActive = true;
    private String googleFormLink;
    private String whatsappNumber;
    private BigDecimal upiPrice;
    @Column(nullable = false) private OffsetDateTime createdAt;
    @Column(nullable = false) private OffsetDateTime updatedAt;
    @PrePersist void prePersist(){createdAt=OffsetDateTime.now();updatedAt=createdAt;}
    @PreUpdate void preUpdate(){updatedAt=OffsetDateTime.now();}
}
