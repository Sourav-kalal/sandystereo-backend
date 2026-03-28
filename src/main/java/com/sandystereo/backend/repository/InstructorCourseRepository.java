package com.sandystereo.backend.repository;

import com.sandystereo.backend.entity.InstructorCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface InstructorCourseRepository extends JpaRepository<InstructorCourse, UUID> {
    List<InstructorCourse> findByInstructorId(UUID instructorId);
    List<InstructorCourse> findByCourseId(UUID courseId);
}
