package com.sandystereo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @Entity @Table(name = "classes")
public class CourseClass {
    @Id @GeneratedValue @UuidGenerator private UUID id;
    @ManyToOne(optional = false) @JoinColumn(name = "course_id") private Course course;
    @ManyToOne @JoinColumn(name = "instructor_id") private User instructor;
    @Column(nullable = false) private String title;
    @Column(nullable = false) private OffsetDateTime scheduledAt;
    private String meetLink;
    @Column(nullable = false) private OffsetDateTime createdAt;
    @PrePersist void prePersist(){createdAt=OffsetDateTime.now();}
}
