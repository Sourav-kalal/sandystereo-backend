package com.sandystereo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @Entity @Table(name = "events")
public class Event {
    @Id @GeneratedValue @UuidGenerator private UUID id;
    @Column(nullable = false) private String name;
    private String description;
    @Column(nullable = false) private OffsetDateTime eventDate;
    private String bannerUrl;
    private String googleFormLink;
    @Column(nullable = false) private Boolean isActive = true;
    @Column(nullable = false) private OffsetDateTime createdAt;
    @Column(nullable = false) private OffsetDateTime updatedAt;
    @PrePersist void prePersist(){createdAt=OffsetDateTime.now();updatedAt=createdAt;}
    @PreUpdate void preUpdate(){updatedAt=OffsetDateTime.now();}
}
