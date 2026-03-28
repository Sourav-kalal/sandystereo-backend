package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.entity.Course;
import com.sandystereo.backend.repository.CourseRepository;
import com.sandystereo.backend.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    private final com.sandystereo.backend.repository.InstructorCourseRepository instructorCourseRepository;

    public CourseServiceImpl(CourseRepository repository, 
                             com.sandystereo.backend.repository.InstructorCourseRepository instructorCourseRepository) { 
        this.repository = repository; 
        this.instructorCourseRepository = instructorCourseRepository;
    }

    @Override public List<Course> findAll() { return repository.findAll(); }
    @Override public Course findById(UUID id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found")); }
    @Override public Course save(Course course) { return repository.save(course); }
    @Override public Course update(UUID id, Course e) {
        Course existing = findById(id);
        if (e.getTitle() != null) existing.setTitle(e.getTitle());
        if (e.getDescription() != null) existing.setDescription(e.getDescription());
        if (e.getImageUrl() != null) existing.setImageUrl(e.getImageUrl());
        if (e.getLevel() != null) existing.setLevel(e.getLevel());
        if (e.getDuration() != null) existing.setDuration(e.getDuration());
        if (e.getIsActive() != null) existing.setIsActive(e.getIsActive());
        if (e.getGoogleFormLink() != null) existing.setGoogleFormLink(e.getGoogleFormLink());
        if (e.getWhatsappNumber() != null) existing.setWhatsappNumber(e.getWhatsappNumber());
        if (e.getUpiPrice() != null) existing.setUpiPrice(e.getUpiPrice());
        return repository.save(existing);
    }
    @Override public void deleteById(UUID id) { repository.deleteById(id); }

    @Override
    public List<Course> findByInstructorId(UUID instructorId) {
        return instructorCourseRepository.findByInstructorId(instructorId).stream()
                .map(com.sandystereo.backend.entity.InstructorCourse::getCourse)
                .toList();
    }
}
