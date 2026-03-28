package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.Enrollment;
import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    List<Enrollment> findAll();
    Enrollment findById(UUID id);
    Enrollment save(Enrollment enrollment);
    void deleteById(UUID id);
    List<Enrollment> findByInstructorId(UUID instructorId);
    List<Enrollment> findByUserId(UUID userId);
}
