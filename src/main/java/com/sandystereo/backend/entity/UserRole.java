package com.sandystereo.backend.entity;

import com.sandystereo.backend.enums.AppRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @Entity @Table(name = "user_roles")
public class UserRole {
    @Id @GeneratedValue @UuidGenerator
    private UUID id;
    @ManyToOne(optional = false) @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private AppRole role;
    @Column(nullable = false) private OffsetDateTime createdAt;
    @PrePersist void prePersist(){createdAt=OffsetDateTime.now();}
}
