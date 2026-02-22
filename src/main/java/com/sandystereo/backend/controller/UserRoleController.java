package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.UserRole;
import com.sandystereo.backend.repository.UserRoleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/userroles")
public class UserRoleController {
    private final UserRoleRepository repository;
    public UserRoleController(UserRoleRepository repository){this.repository=repository;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<UserRole> list(){return repository.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public UserRole get(@PathVariable UUID id){return repository.findById(id).orElseThrow();}
    @PostMapping @PreAuthorize("hasRole('admin')") public UserRole create(@RequestBody UserRole e){return repository.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public UserRole update(@PathVariable UUID id,@RequestBody UserRole e){e.setId(id);return repository.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){repository.deleteById(id);}    
}
