package com.sandystereo.backend.repository;

import com.sandystereo.backend.entity.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseClassRepository extends JpaRepository<CourseClass, UUID> {
}
