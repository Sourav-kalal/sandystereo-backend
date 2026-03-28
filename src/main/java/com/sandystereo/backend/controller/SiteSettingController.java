package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.SiteSetting;
import com.sandystereo.backend.service.SiteSettingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.sandystereo.backend.service.SiteSettingService;

@RestController
@RequestMapping("/api/sitesettings")
public class SiteSettingController {
    private final SiteSettingService service;
    public SiteSettingController(SiteSettingService service){this.service=service;}
    @GetMapping public List<SiteSetting> list(){return service.findAll();}
    @GetMapping("/{id}") public SiteSetting get(@PathVariable UUID id){return service.findById(id);}
    @PostMapping @PreAuthorize("hasRole('admin')") public SiteSetting create(@RequestBody SiteSetting e){return service.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public SiteSetting update(@PathVariable UUID id,@RequestBody SiteSetting e){e.setId(id);return service.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){service.deleteById(id);}    
}
