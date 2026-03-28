package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.enums.AppRole;
import com.sandystereo.backend.entity.User;
import com.sandystereo.backend.entity.UserRole;
import com.sandystereo.backend.repository.UserRepository;
import com.sandystereo.backend.repository.UserRoleRepository;
import com.sandystereo.backend.service.UserRoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository repository;
    private final UserRepository userRepository;
    public UserRoleServiceImpl(UserRoleRepository repository, UserRepository userRepository) { 
        this.repository = repository; 
        this.userRepository = userRepository;
    }

    @Override public List<UserRole> findAll() { return repository.findAll(); }
    @Override public UserRole findById(UUID id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("UserRole not found")); }
    @Override public UserRole save(UserRole userRole) { return repository.save(userRole); }
    @Override public void deleteById(UUID id) { repository.deleteById(id); }

    @Override
    @Transactional
    public void updateUserRole(UUID userId, AppRole newRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        // Remove existing roles
        List<UserRole> existingRoles = repository.findByUserId(userId);
        repository.deleteAll(existingRoles);
        
        // Add new role
        UserRole ur = new UserRole();
        ur.setUser(user);
        ur.setRole(newRole);
        repository.save(ur);
    }
}
