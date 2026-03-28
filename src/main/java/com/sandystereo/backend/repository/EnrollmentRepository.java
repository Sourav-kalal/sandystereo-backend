package com.sandystereo.backend.repository;

import com.sandystereo.backend.entity.Enrollment;
import com.sandystereo.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    @Query("SELECT e FROM Enrollment e JOIN InstructorCourse ic ON e.course.id = ic.course.id WHERE ic.instructor.id = :instructorId")
    List<Enrollment> findByInstructorId(@Param("instructorId") UUID instructorId);
    
    List<Enrollment> findByUser(User user);
}
