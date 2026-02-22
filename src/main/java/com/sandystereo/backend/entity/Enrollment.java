package com.sandystereo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @Entity @Table(name = "enrollments")
public class Enrollment {
    @Id @GeneratedValue @UuidGenerator private UUID id;
    @ManyToOne(optional = false) @JoinColumn(name = "user_id") private User user;
    @ManyToOne(optional = false) @JoinColumn(name = "course_id") private Course course;
    @Column(nullable = false) private OffsetDateTime enrolledAt;
    @PrePersist void prePersist(){enrolledAt=OffsetDateTime.now();}
}
