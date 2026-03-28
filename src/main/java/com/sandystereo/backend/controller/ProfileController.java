package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.Profile;
import com.sandystereo.backend.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.sandystereo.backend.service.ProfileService;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService service;
    public ProfileController(ProfileService service){this.service=service;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<Profile> list(){return service.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public Profile get(@PathVariable UUID id){return service.findById(id);}
    @PostMapping @PreAuthorize("hasRole('admin')") public Profile create(@RequestBody Profile e){return service.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public Profile update(@PathVariable UUID id,@RequestBody Profile e){
        return service.update(id, e);
    }    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){service.deleteById(id);}    
}
