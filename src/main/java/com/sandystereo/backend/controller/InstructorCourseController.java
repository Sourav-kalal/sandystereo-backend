package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.InstructorCourse;
import com.sandystereo.backend.service.InstructorCourseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.sandystereo.backend.service.InstructorCourseService;

@RestController
@RequestMapping("/api/instructorcourses")
public class InstructorCourseController {
    private final InstructorCourseService service;
    public InstructorCourseController(InstructorCourseService service){this.service=service;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<InstructorCourse> list(){return service.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public InstructorCourse get(@PathVariable UUID id){return service.findById(id);}
    @PostMapping @PreAuthorize("hasRole('admin')") public InstructorCourse create(@RequestBody InstructorCourse e){return service.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public InstructorCourse update(@PathVariable UUID id,@RequestBody InstructorCourse e){e.setId(id);return service.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){service.deleteById(id);}    

    @GetMapping("/my")
    @PreAuthorize("hasRole('instructor')")
    public List<InstructorCourse> getMyCourses(Authentication authentication) {
        // Assuming user ID is stored in principal name or we can get it from user service
        // Since we don't have a direct "current userId" helper yet, I'll assume 
        // the principal name IS the user ID string or email.
        // Let's check how the JWT is structured or if we have a CustomUserDetails.
        return service.findByInstructorId(UUID.fromString(authentication.getName()));
    }
}
