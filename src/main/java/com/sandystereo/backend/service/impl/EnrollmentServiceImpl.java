package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.entity.Enrollment;
import com.sandystereo.backend.repository.EnrollmentRepository;
import com.sandystereo.backend.service.EnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository repository;
    public EnrollmentServiceImpl(EnrollmentRepository repository) { this.repository = repository; }

    @Override public List<Enrollment> findAll() { return repository.findAll(); }
    @Override public Enrollment findById(UUID id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Enrollment not found")); }
    @Override public Enrollment save(Enrollment enrollment) { return repository.save(enrollment); }
    @Override public void deleteById(UUID id) { repository.deleteById(id); }
    @Override public List<Enrollment> findByInstructorId(UUID instructorId) { return repository.findByInstructorId(instructorId); }

    @Override
    public List<Enrollment> findByUserId(UUID userId) {
        com.sandystereo.backend.entity.User user = new com.sandystereo.backend.entity.User();
        user.setId(userId);
        return repository.findByUser(user);
    }
}
