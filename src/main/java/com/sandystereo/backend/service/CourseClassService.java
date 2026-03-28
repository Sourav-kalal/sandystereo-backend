package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.CourseClass;
import java.util.List;
import java.util.UUID;

public interface CourseClassService {
    List<CourseClass> findAll();
    CourseClass findById(UUID id);
    CourseClass save(CourseClass courseClass);
    void deleteById(UUID id);
    List<CourseClass> findByInstructorId(UUID instructorId);
    List<CourseClass> findByStudentId(UUID studentId);
}
