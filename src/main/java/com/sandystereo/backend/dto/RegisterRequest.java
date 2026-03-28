package com.sandystereo.backend.dto;

import com.sandystereo.backend.enums.AppRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RegisterRequest(
    @Email String email, 
    @NotBlank String password, 
    String fullName,
    String avatarUrl,
    java.time.LocalDate dateOfBirth,
    String gender,
    String fatherName,
    String motherName,
    String phoneNumber,
    String address,
    Set<AppRole> roles
) {
}
