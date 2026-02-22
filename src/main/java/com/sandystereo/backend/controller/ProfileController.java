package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.Profile;
import com.sandystereo.backend.repository.ProfileRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileRepository repository;
    public ProfileController(ProfileRepository repository){this.repository=repository;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<Profile> list(){return repository.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public Profile get(@PathVariable UUID id){return repository.findById(id).orElseThrow();}
    @PostMapping @PreAuthorize("hasRole('admin')") public Profile create(@RequestBody Profile e){return repository.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public Profile update(@PathVariable UUID id,@RequestBody Profile e){e.setId(id);return repository.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){repository.deleteById(id);}    
}
