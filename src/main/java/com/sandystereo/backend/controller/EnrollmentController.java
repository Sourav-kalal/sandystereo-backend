package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.Enrollment;
import com.sandystereo.backend.service.EnrollmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.sandystereo.backend.service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService service;
    public EnrollmentController(EnrollmentService service){this.service=service;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<Enrollment> list(){return service.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public Enrollment get(@PathVariable UUID id){return service.findById(id);}
    @PostMapping @PreAuthorize("hasRole('admin')") public Enrollment create(@RequestBody Enrollment e){return service.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public Enrollment update(@PathVariable UUID id,@RequestBody Enrollment e){e.setId(id);return service.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){service.deleteById(id);}    

    @GetMapping("/my-students")
    @PreAuthorize("hasAnyRole('instructor')")
    public List<Enrollment> getMyStudents(Authentication authentication) {
        return service.findByInstructorId(UUID.fromString(authentication.getName()));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('student')")
    public List<Enrollment> getMyEnrollments(Authentication authentication) {
        return service.findByUserId(UUID.fromString(authentication.getName()));
    }
}
