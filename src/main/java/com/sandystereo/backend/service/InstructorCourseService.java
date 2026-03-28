package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.InstructorCourse;
import java.util.List;
import java.util.UUID;

public interface InstructorCourseService {
    List<InstructorCourse> findAll();
    InstructorCourse findById(UUID id);
    InstructorCourse save(InstructorCourse instructorCourse);
    void deleteById(UUID id);
    List<InstructorCourse> findByInstructorId(UUID instructorId);
    List<InstructorCourse> findByCourseId(UUID courseId);
}
