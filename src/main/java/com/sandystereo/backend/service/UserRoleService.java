package com.sandystereo.backend.service;

import com.sandystereo.backend.enums.AppRole;
import com.sandystereo.backend.entity.UserRole;
import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    List<UserRole> findAll();
    UserRole findById(UUID id);
    UserRole save(UserRole userRole);
    void deleteById(UUID id);
    void updateUserRole(UUID userId, AppRole newRole);
}
