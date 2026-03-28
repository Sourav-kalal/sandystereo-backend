package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.SiteSetting;
import java.util.List;
import java.util.UUID;

public interface SiteSettingService {
    List<SiteSetting> findAll();
    SiteSetting findById(UUID id);
    SiteSetting findBySettingKey(String settingKey);
    SiteSetting save(SiteSetting siteSetting);
    void deleteById(UUID id);
}
