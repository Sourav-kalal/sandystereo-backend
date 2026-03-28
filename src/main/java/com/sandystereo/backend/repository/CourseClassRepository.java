package com.sandystereo.backend.repository;

import com.sandystereo.backend.entity.Course;
import com.sandystereo.backend.entity.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseClassRepository extends JpaRepository<CourseClass, UUID> {
    List<CourseClass> findByInstructorId(UUID instructorId);
    List<CourseClass> findByCourseIn(List<Course> courses);
}
