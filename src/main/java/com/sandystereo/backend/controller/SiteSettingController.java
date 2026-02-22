package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.SiteSetting;
import com.sandystereo.backend.repository.SiteSettingRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sitesettings")
public class SiteSettingController {
    private final SiteSettingRepository repository;
    public SiteSettingController(SiteSettingRepository repository){this.repository=repository;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<SiteSetting> list(){return repository.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public SiteSetting get(@PathVariable UUID id){return repository.findById(id).orElseThrow();}
    @PostMapping @PreAuthorize("hasRole('admin')") public SiteSetting create(@RequestBody SiteSetting e){return repository.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public SiteSetting update(@PathVariable UUID id,@RequestBody SiteSetting e){e.setId(id);return repository.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){repository.deleteById(id);}    
}
