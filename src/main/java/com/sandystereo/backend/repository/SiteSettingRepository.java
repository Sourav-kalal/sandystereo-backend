package com.sandystereo.backend.repository;

import com.sandystereo.backend.entity.SiteSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SiteSettingRepository extends JpaRepository<SiteSetting, UUID> {
}
