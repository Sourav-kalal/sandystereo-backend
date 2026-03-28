package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.User;
import com.sandystereo.backend.service.GoogleMeetService;
import com.sandystereo.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/google")
public class GoogleAuthController {

    private final GoogleMeetService googleMeetService;
    private final UserService userService;

    public GoogleAuthController(GoogleMeetService googleMeetService, UserService userService) {
        this.googleMeetService = googleMeetService;
        this.userService = userService;
    }

    @GetMapping("/url")
    public Map<String, String> getGoogleAuthUrl() {
        User user = userService.getCurrentUser();
        return Map.of("url", googleMeetService.getAuthorizationUrl(user));
    }

    @GetMapping("/callback")
    public org.springframework.http.ResponseEntity<Void> googleCallback(@RequestParam String code, @RequestParam String state) throws IOException {
        // State is the user ID in this implementation
        User user = userService.findById(java.util.UUID.fromString(state));
        googleMeetService.storeTokens(code, user);
        
        return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.FOUND)
                .location(java.net.URI.create("http://localhost:5173/instructor?google_linked=true"))
                .build();
    }
}
