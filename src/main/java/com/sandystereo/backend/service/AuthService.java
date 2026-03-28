package com.sandystereo.backend.service;

import com.sandystereo.backend.dto.AuthRequest;
import com.sandystereo.backend.dto.AuthResponse;
import com.sandystereo.backend.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
}
