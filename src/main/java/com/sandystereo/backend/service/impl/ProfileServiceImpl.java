package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.entity.Profile;
import com.sandystereo.backend.entity.UserRole;
import com.sandystereo.backend.repository.InstructorCourseRepository;
import com.sandystereo.backend.repository.ProfileRepository;
import com.sandystereo.backend.repository.UserRoleRepository;
import com.sandystereo.backend.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repository;
    private final UserRoleRepository userRoleRepository;
    private final InstructorCourseRepository instructorCourseRepository;
    public ProfileServiceImpl(ProfileRepository repository, UserRoleRepository userRoleRepository, InstructorCourseRepository instructorCourseRepository) { 
        this.repository = repository; 
        this.userRoleRepository = userRoleRepository;
        this.instructorCourseRepository = instructorCourseRepository;
    }

    @Override public List<Profile> findAll() { 
        List<Profile> profiles = repository.findAll(); 
        profiles.forEach(this::populateTransientFields);
        return profiles;
    }

    @Override public Profile findById(UUID id) { 
        Profile profile = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Profile not found")); 
        populateTransientFields(profile);
        return profile;
    }

    private void populateTransientFields(Profile profile) {
        if (profile.getUser() != null) {
            UUID userId = profile.getUser().getId();
            
            // Populate Roles
            List<String> roles = userRoleRepository.findByUserId(userId)
                .stream()
                .map(ur -> ur.getRole().name())
                .toList();
            profile.setRoles(roles);
            
            // Populate Assigned Courses (if instructor)
            if (roles.contains("instructor")) {
                List<java.util.Map<String, String>> courses = instructorCourseRepository.findByInstructorId(userId)
                    .stream()
                    .map(ic -> {
                        java.util.Map<String, String> map = new java.util.HashMap<>();
                        map.put("id", ic.getId().toString());
                        map.put("title", ic.getCourse().getTitle());
                        return map;
                    })
                    .toList();
                profile.setAssignedCourses(courses);
            }
        }
    }
    @Override public Profile save(Profile profile) { return repository.save(profile); }
    @Override public Profile update(UUID id, Profile e) {
        Profile existing = findById(id);
        if (e.getFullName() != null) existing.setFullName(e.getFullName());
        if (e.getAvatarUrl() != null) existing.setAvatarUrl(e.getAvatarUrl());
        return repository.save(existing);
    }
    @Override public void deleteById(UUID id) { repository.deleteById(id); }
}
