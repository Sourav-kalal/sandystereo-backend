package com.sandystereo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @Entity @Table(name = "admissions")
public class Admission {
    @Id @GeneratedValue @UuidGenerator private UUID id;
    @ManyToOne(optional = false) @JoinColumn(name = "course_id") private Course course;
    @ManyToOne @JoinColumn(name = "user_id") private User user;
    @Column(nullable = false) private String applicantName;
    @Column(nullable = false) private LocalDate dateOfBirth;
    @Column(nullable = false) private String gender;
    @Column(nullable = false) private String fatherName;
    @Column(nullable = false) private String motherName;
    @Column(nullable = false) private String email;
    @Column(nullable = false) private String phoneNumber;
    @Column(nullable = false) private String address;
    private String photoUrl;
    @Column(nullable = false) private String classMode = "offline";
    private String preferredBatch;
    private String referralCode;
    @Column(nullable = false) private String status = "pending";
    @Column(nullable = false) private OffsetDateTime createdAt;
    @PrePersist void prePersist(){createdAt=OffsetDateTime.now();}
}
