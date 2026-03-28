package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.dto.AuthRequest;
import com.sandystereo.backend.dto.AuthResponse;
import com.sandystereo.backend.dto.RegisterRequest;
import com.sandystereo.backend.entity.User;
import com.sandystereo.backend.entity.UserRole;
import com.sandystereo.backend.enums.AppRole;
import com.sandystereo.backend.entity.Profile;
import com.sandystereo.backend.repository.AdmissionRepository;
import com.sandystereo.backend.repository.ProfileRepository;
import com.sandystereo.backend.repository.UserRepository;
import com.sandystereo.backend.repository.UserRoleRepository;
import com.sandystereo.backend.security.JwtService;
import com.sandystereo.backend.service.AuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ProfileRepository profileRepository;
    private final AdmissionRepository admissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, 
                           UserRoleRepository userRoleRepository, 
                           ProfileRepository profileRepository,
                           AdmissionRepository admissionRepository,
                           PasswordEncoder passwordEncoder, 
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.profileRepository = profileRepository;
        this.admissionRepository = admissionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(u -> { throw new EntityExistsException("User already exists"); });
        User user = new User();
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(user);

        // Create Profile
        Profile profile = new Profile();
        profile.setUser(saved);
        profile.setFullName(request.fullName());
        profile.setAvatarUrl(request.avatarUrl());
        profile.setDateOfBirth(request.dateOfBirth());
        profile.setGender(request.gender());
        profile.setFatherName(request.fatherName());
        profile.setMotherName(request.motherName());
        profile.setPhoneNumber(request.phoneNumber());
        profile.setAddress(request.address());
        profileRepository.save(profile);

        // Link existing admissions
        admissionRepository.findByEmail(request.email()).forEach(admission -> {
            admission.setUser(saved);
            admissionRepository.save(admission);
        });

        // Forcibly assign only 'student' role on registration
        UserRole userRole = new UserRole();
        userRole.setUser(saved);
        userRole.setRole(AppRole.student);
        userRoleRepository.save(userRole);

        return new AuthResponse(jwtService.generateToken(saved.getId().toString(), Map.of(
            "roles", List.of(AppRole.student.name()),
            "email", saved.getEmail(),
            "fullName", profile.getFullName() != null ? profile.getFullName() : "",
            "dateOfBirth", profile.getDateOfBirth() != null ? profile.getDateOfBirth().toString() : "",
            "gender", profile.getGender() != null ? profile.getGender() : "",
            "fatherName", profile.getFatherName() != null ? profile.getFatherName() : "",
            "motherName", profile.getMotherName() != null ? profile.getMotherName() : "",
            "phoneNumber", profile.getPhoneNumber() != null ? profile.getPhoneNumber() : "",
            "address", profile.getAddress() != null ? profile.getAddress() : "",
            "avatarUrl", profile.getAvatarUrl() != null ? profile.getAvatarUrl() : ""
        )));
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new org.springframework.security.authentication.BadCredentialsException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new org.springframework.security.authentication.BadCredentialsException("Invalid credentials");
        }
        List<String> roles = userRoleRepository.findAll().stream()
                .filter(x -> x.getUser().getId().equals(user.getId()))
                .map(x -> x.getRole().name())
                .toList();
        
        Profile profile = profileRepository.findByUser(user).orElse(new Profile());

        return new AuthResponse(jwtService.generateToken(user.getId().toString(), Map.of(
            "roles", roles,
            "email", user.getEmail(),
            "fullName", profile.getFullName() != null ? profile.getFullName() : "",
            "dateOfBirth", profile.getDateOfBirth() != null ? profile.getDateOfBirth().toString() : "",
            "gender", profile.getGender() != null ? profile.getGender() : "",
            "fatherName", profile.getFatherName() != null ? profile.getFatherName() : "",
            "motherName", profile.getMotherName() != null ? profile.getMotherName() : "",
            "phoneNumber", profile.getPhoneNumber() != null ? profile.getPhoneNumber() : "",
            "address", profile.getAddress() != null ? profile.getAddress() : "",
            "avatarUrl", profile.getAvatarUrl() != null ? profile.getAvatarUrl() : ""
        )));
    }
}
