package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.entity.CourseClass;
import com.sandystereo.backend.repository.CourseClassRepository;
import com.sandystereo.backend.service.CourseClassService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseClassServiceImpl implements CourseClassService {
    private final CourseClassRepository repository;
    private final com.sandystereo.backend.service.GoogleMeetService googleMeetService;
    private final com.sandystereo.backend.service.EnrollmentService enrollmentService;

    public CourseClassServiceImpl(CourseClassRepository repository, 
                                 com.sandystereo.backend.service.GoogleMeetService googleMeetService,
                                 com.sandystereo.backend.service.EnrollmentService enrollmentService) { 
        this.repository = repository; 
        this.googleMeetService = googleMeetService;
        this.enrollmentService = enrollmentService;
    }

    @Override public List<CourseClass> findAll() { return repository.findAll(); }
    @Override public CourseClass findById(UUID id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Class not found")); }

    @Override
    public List<CourseClass> findByStudentId(UUID studentId) {
        List<com.sandystereo.backend.entity.Enrollment> enrollments = enrollmentService.findByUserId(studentId);
        List<com.sandystereo.backend.entity.Course> courses = enrollments.stream()
                .map(com.sandystereo.backend.entity.Enrollment::getCourse)
                .toList();
        if (courses.isEmpty()) return List.of();
        return repository.findByCourseIn(courses);
    }

    @Override 
    public CourseClass save(CourseClass courseClass) { 
        boolean isNew = courseClass.getId() == null;
        CourseClass saved = repository.save(courseClass);
        try {
            if (isNew) {
                googleMeetService.createMeeting(saved);
            } else {
                googleMeetService.updateMeeting(saved);
            }
            return repository.save(saved); // Update with googleEventId/meetLink
        } catch (Exception e) {
            // Log error but don't fail the transaction if Google sync fails
            // In a production app, we might want more robust error handling or retry logic
            System.err.println("Failed to sync with Google Calendar: " + e.getMessage());
            return saved;
        }
    }

    @Override 
    public void deleteById(UUID id) { 
        repository.findById(id).ifPresent(cls -> {
            try {
                googleMeetService.deleteMeeting(cls);
            } catch (Exception e) {
                System.err.println("Failed to delete Google Calendar event: " + e.getMessage());
            }
            repository.deleteById(id);
        });
    }

    @Override public List<CourseClass> findByInstructorId(UUID instructorId) { return repository.findByInstructorId(instructorId); }
}
