package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.entity.CourseClass;
import com.sandystereo.backend.entity.InstructorGoogleCredential;
import com.sandystereo.backend.entity.User;
import com.sandystereo.backend.repository.InstructorGoogleCredentialRepository;
import com.sandystereo.backend.repository.SiteSettingRepository;
import com.sandystereo.backend.service.GoogleMeetService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class GoogleMeetServiceImpl implements GoogleMeetService {

    private final InstructorGoogleCredentialRepository credentialRepository;
    private final SiteSettingRepository siteSettingRepository;
    private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    private final List<String> scopes = Collections.singletonList(CalendarScopes.CALENDAR);

    @Value("${google.client-id}")
    private String defaultClientId;

    @Value("${google.client-secret}")
    private String defaultClientSecret;

    @Value("${google.redirect-uri}")
    private String defaultRedirectUri;

    public GoogleMeetServiceImpl(InstructorGoogleCredentialRepository credentialRepository, SiteSettingRepository siteSettingRepository) {
        this.credentialRepository = credentialRepository;
        this.siteSettingRepository = siteSettingRepository;
    }

    private String getSetting(String key, String def) {
        return siteSettingRepository.findBySettingKey(key)
                .map(com.sandystereo.backend.entity.SiteSetting::getSettingValue)
                .filter(val -> val != null && !val.trim().isEmpty())
                .orElse(def);
    }

    private GoogleAuthorizationCodeFlow getFlow() throws IOException {
        String cid = getSetting("google_client_id", defaultClientId);
        String csecret = getSetting("google_client_secret", defaultClientSecret);
        return new GoogleAuthorizationCodeFlow.Builder(
                getTransport(), jsonFactory, cid, csecret, scopes)
                .setAccessType("offline")
                .build();
    }

    private HttpTransport getTransport() {
        try {
            return GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Could not initialize Google Net Http Transport", e);
        }
    }

    @Override
    public String getAuthorizationUrl(User user) {
        try {
            String rUri = getSetting("google_redirect_uri", defaultRedirectUri);
            return getFlow().newAuthorizationUrl()
                    .setRedirectUri(rUri)
                    .setState(user.getId().toString())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Error generating Google authorization URL", e);
        }
    }

    @Override
    public void storeTokens(String code, User user) throws IOException {
        String rUri = getSetting("google_redirect_uri", defaultRedirectUri);
        TokenResponse response = getFlow().newTokenRequest(code)
                .setRedirectUri(rUri)
                .execute();

        InstructorGoogleCredential credential = credentialRepository.findByUser(user)
                .orElse(new InstructorGoogleCredential());
        
        credential.setUser(user);
        credential.setAccessToken(response.getAccessToken());
        if (response.getRefreshToken() != null) {
            credential.setRefreshToken(response.getRefreshToken());
        }
        credential.setExpiresAt(OffsetDateTime.now().plusSeconds(response.getExpiresInSeconds()));
        credentialRepository.save(credential);
    }

    private Calendar getCalendarService(User user) throws IOException {
        InstructorGoogleCredential credentials = credentialRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Instructor has not linked Google account"));

        String cid = getSetting("google_client_id", defaultClientId);
        String csecret = getSetting("google_client_secret", defaultClientSecret);

        Credential credential = new Credential.Builder(com.google.api.client.auth.oauth2.BearerToken.authorizationHeaderAccessMethod())
                .setTransport(getTransport())
                .setJsonFactory(jsonFactory)
                .setTokenServerEncodedUrl("https://oauth2.googleapis.com/token")
                .setClientAuthentication(new com.google.api.client.auth.oauth2.ClientParametersAuthentication(cid, csecret))
                .build();

        credential.setAccessToken(credentials.getAccessToken());
        credential.setRefreshToken(credentials.getRefreshToken());

        // Refresh if expired
        Long expiresIn = credentials.getExpiresAt().toEpochSecond() - OffsetDateTime.now().toEpochSecond();
        if (expiresIn < 60) {
            credential.refreshToken();
            credentials.setAccessToken(credential.getAccessToken());
            credentials.setExpiresAt(OffsetDateTime.now().plusSeconds(credential.getExpiresInSeconds()));
            credentialRepository.save(credentials);
        }

        return new Calendar.Builder(getTransport(), jsonFactory, credential)
                .setApplicationName("Sangeet Academy")
                .build();
    }

    @Override
    public void createMeeting(CourseClass courseClass) throws IOException {
        Calendar service = getCalendarService(courseClass.getInstructor());

        Event event = new Event()
                .setSummary(courseClass.getTitle())
                .setDescription("Class for " + courseClass.getCourse().getTitle());

        DateTime startDateTime = new DateTime(courseClass.getScheduledAt().toInstant().toEpochMilli());
        EventDateTime start = new EventDateTime().setDateTime(startDateTime);
        event.setStart(start);

        // Assume 1 hour duration if not specified
        DateTime endDateTime = new DateTime(courseClass.getScheduledAt().plusHours(1).toInstant().toEpochMilli());
        EventDateTime end = new EventDateTime().setDateTime(endDateTime);
        event.setEnd(end);

        // Add Google Meet
        ConferenceData conferenceData = new ConferenceData()
                .setCreateRequest(new CreateConferenceRequest()
                        .setRequestId(UUID.randomUUID().toString())
                        .setConferenceSolutionKey(new ConferenceSolutionKey().setType("hangoutsMeet")));
        event.setConferenceData(conferenceData);

        event = service.events().insert("primary", event)
                .setConferenceDataVersion(1)
                .execute();

        courseClass.setGoogleEventId(event.getId());
        courseClass.setMeetLink(event.getHangoutLink());
    }

    @Override
    public void updateMeeting(CourseClass courseClass) throws IOException {
        if (courseClass.getGoogleEventId() == null) {
            createMeeting(courseClass);
            return;
        }

        Calendar service = getCalendarService(courseClass.getInstructor());
        Event event = service.events().get("primary", courseClass.getGoogleEventId()).execute();

        event.setSummary(courseClass.getTitle());
        DateTime startDateTime = new DateTime(courseClass.getScheduledAt().toInstant().toEpochMilli());
        event.setStart(new EventDateTime().setDateTime(startDateTime));
        DateTime endDateTime = new DateTime(courseClass.getScheduledAt().plusHours(1).toInstant().toEpochMilli());
        event.setEnd(new EventDateTime().setDateTime(endDateTime));

        service.events().update("primary", event.getId(), event).execute();
    }

    @Override
    public void deleteMeeting(CourseClass courseClass) throws IOException {
        if (courseClass.getGoogleEventId() == null) return;

        Calendar service = getCalendarService(courseClass.getInstructor());
        service.events().delete("primary", courseClass.getGoogleEventId()).execute();
    }
}
