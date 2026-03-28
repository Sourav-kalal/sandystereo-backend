package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.Course;
import java.util.List;
import java.util.UUID;

public interface CourseService {
    List<Course> findAll();
    Course findById(UUID id);
    Course save(Course course);
    Course update(UUID id, Course course);
    void deleteById(UUID id);
    List<Course> findByInstructorId(UUID instructorId);
}
