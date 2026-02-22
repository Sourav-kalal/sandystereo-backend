package com.sandystereo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @Entity @Table(name = "profiles")
public class Profile {
    @Id @GeneratedValue @UuidGenerator
    private UUID id;
    @OneToOne(optional = false) @JoinColumn(name = "user_id", unique = true)
    private User user;
    private String fullName;
    private String avatarUrl;
    @Column(nullable = false) private OffsetDateTime createdAt;
    @Column(nullable = false) private OffsetDateTime updatedAt;
    @PrePersist void prePersist(){createdAt=OffsetDateTime.now();updatedAt=createdAt;}
    @PreUpdate void preUpdate(){updatedAt=OffsetDateTime.now();}
}
