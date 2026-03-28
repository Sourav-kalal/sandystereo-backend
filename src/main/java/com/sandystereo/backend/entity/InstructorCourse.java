package com.sandystereo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name = "instructor_courses")
public class InstructorCourse {
    @Id @GeneratedValue @UuidGenerator private UUID id;
    @ManyToOne(optional = false) @JoinColumn(name = "instructor_id") private User instructor;
    @ManyToOne(optional = false) @JoinColumn(name = "course_id") private Course course;
    @Column(nullable = false, updatable = false) private OffsetDateTime assignedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getInstructor() { return instructor; }
    public void setInstructor(User instructor) { this.instructor = instructor; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public OffsetDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(OffsetDateTime assignedAt) { this.assignedAt = assignedAt; }

    @PrePersist void prePersist(){assignedAt=OffsetDateTime.now();}
}
