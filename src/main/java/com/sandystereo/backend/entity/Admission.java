package com.sandystereo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name = "admissions")
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
    @Column(nullable = false, updatable = false) private OffsetDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getClassMode() { return classMode; }
    public void setClassMode(String classMode) { this.classMode = classMode; }

    public String getPreferredBatch() { return preferredBatch; }
    public void setPreferredBatch(String preferredBatch) { this.preferredBatch = preferredBatch; }

    public String getReferralCode() { return referralCode; }
    public void setReferralCode(String referralCode) { this.referralCode = referralCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist void prePersist(){createdAt=OffsetDateTime.now();}
}
