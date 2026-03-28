package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.entity.InstructorCourse;
import com.sandystereo.backend.repository.InstructorCourseRepository;
import com.sandystereo.backend.service.InstructorCourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InstructorCourseServiceImpl implements InstructorCourseService {
    private final InstructorCourseRepository repository;
    public InstructorCourseServiceImpl(InstructorCourseRepository repository) { this.repository = repository; }

    @Override public List<InstructorCourse> findAll() { return repository.findAll(); }
    @Override public InstructorCourse findById(UUID id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("InstructorCourse not found")); }
    @Override public InstructorCourse save(InstructorCourse instructorCourse) { return repository.save(instructorCourse); }
    @Override public void deleteById(UUID id) { repository.deleteById(id); }
    @Override public List<InstructorCourse> findByInstructorId(UUID instructorId) { return repository.findByInstructorId(instructorId); }
    @Override public List<InstructorCourse> findByCourseId(UUID courseId) { return repository.findByCourseId(courseId); }
}
