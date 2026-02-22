package com.sandystereo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @Entity @Table(name = "site_settings")
public class SiteSetting {
    @Id @GeneratedValue @UuidGenerator private UUID id;
    @Column(nullable = false, unique = true) private String settingKey;
    private String settingValue;
    @Column(nullable = false) private OffsetDateTime updatedAt;
    @PrePersist void prePersist(){updatedAt=OffsetDateTime.now();}
    @PreUpdate void preUpdate(){updatedAt=OffsetDateTime.now();}
}
