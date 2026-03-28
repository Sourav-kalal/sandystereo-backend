package com.sandystereo.backend.controller;

import com.sandystereo.backend.enums.AppRole;
import com.sandystereo.backend.entity.UserRole;
import com.sandystereo.backend.service.UserRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.sandystereo.backend.service.UserRoleService;

@RestController
@RequestMapping("/api/userroles")
public class UserRoleController {
    private final UserRoleService service;
    public UserRoleController(UserRoleService service){this.service=service;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<UserRole> list(){return service.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public UserRole get(@PathVariable UUID id){return service.findById(id);}
    @PostMapping @PreAuthorize("hasRole('admin')") public UserRole create(@RequestBody UserRole e){return service.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public UserRole update(@PathVariable UUID id,@RequestBody UserRole e){e.setId(id);return service.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){service.deleteById(id);}    

    @PatchMapping("/{userId}/role") @PreAuthorize("hasRole('admin')")
    public void updateRole(@PathVariable UUID userId, @RequestParam AppRole role) {
        service.updateUserRole(userId, role);
    }
}
