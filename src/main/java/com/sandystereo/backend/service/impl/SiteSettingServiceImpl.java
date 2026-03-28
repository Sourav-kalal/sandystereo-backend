package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.entity.SiteSetting;
import com.sandystereo.backend.repository.SiteSettingRepository;
import com.sandystereo.backend.service.SiteSettingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SiteSettingServiceImpl implements SiteSettingService {
    private final SiteSettingRepository repository;
    public SiteSettingServiceImpl(SiteSettingRepository repository) { this.repository = repository; }

    @Override public List<SiteSetting> findAll() { return repository.findAll(); }
    @Override public SiteSetting findById(UUID id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("SiteSetting not found")); }
    @Override public SiteSetting findBySettingKey(String settingKey) { return repository.findBySettingKey(settingKey).orElseThrow(() -> new EntityNotFoundException("SiteSetting not found")); }
    @Override public SiteSetting save(SiteSetting siteSetting) { return repository.save(siteSetting); }
    @Override public void deleteById(UUID id) { repository.deleteById(id); }
}
