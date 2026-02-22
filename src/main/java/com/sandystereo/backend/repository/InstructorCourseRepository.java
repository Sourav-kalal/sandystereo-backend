package com.sandystereo.backend.repository;

import com.sandystereo.backend.entity.InstructorCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstructorCourseRepository extends JpaRepository<InstructorCourse, UUID> {
}
