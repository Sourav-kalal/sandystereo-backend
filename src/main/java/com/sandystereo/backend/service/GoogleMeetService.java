package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.CourseClass;
import com.sandystereo.backend.entity.User;
import com.google.api.client.auth.oauth2.TokenResponse;

import java.io.IOException;

public interface GoogleMeetService {
    String getAuthorizationUrl(User user);
    void storeTokens(String code, User user) throws IOException;
    void createMeeting(CourseClass courseClass) throws IOException;
    void updateMeeting(CourseClass courseClass) throws IOException;
    void deleteMeeting(CourseClass courseClass) throws IOException;
}
