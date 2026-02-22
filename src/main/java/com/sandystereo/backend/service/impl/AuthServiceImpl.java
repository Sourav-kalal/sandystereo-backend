package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.dto.AuthRequest;
import com.sandystereo.backend.dto.AuthResponse;
import com.sandystereo.backend.dto.RegisterRequest;
import com.sandystereo.backend.entity.User;
import com.sandystereo.backend.entity.UserRole;
import com.sandystereo.backend.enums.AppRole;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
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

        var roles = request.roles() == null || request.roles().isEmpty() ? List.of(AppRole.student) : request.roles().stream().toList();
        roles.forEach(role -> {
            UserRole userRole = new UserRole();
            userRole.setUser(saved);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
        });

        return new AuthResponse(jwtService.generateToken(saved.getEmail(), Map.of("roles", roles.stream().map(Enum::name).toList())));
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new EntityNotFoundException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new EntityNotFoundException("Invalid credentials");
        }
        List<String> roles = userRoleRepository.findAll().stream()
                .filter(x -> x.getUser().getId().equals(user.getId()))
                .map(x -> x.getRole().name())
                .toList();
        return new AuthResponse(jwtService.generateToken(user.getEmail(), Map.of("roles", roles)));
    }
}
