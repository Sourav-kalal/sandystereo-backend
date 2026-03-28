package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.CourseClass;
import com.sandystereo.backend.service.CourseClassService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.sandystereo.backend.service.CourseClassService;

@RestController
@RequestMapping("/api/classes")
public class CourseClassController {
    private final CourseClassService service;
    public CourseClassController(CourseClassService service){this.service=service;}
    @GetMapping public List<CourseClass> list(){return service.findAll();}
    @GetMapping("/{id}") public CourseClass get(@PathVariable UUID id){return service.findById(id);}
    
    @GetMapping("/my")
    @PreAuthorize("hasRole('instructor')")
    public List<CourseClass> getMyClasses(Authentication authentication) {
        return service.findByInstructorId(UUID.fromString(authentication.getName()));
    }

    @GetMapping("/student/my")
    @PreAuthorize("hasRole('student')")
    public List<CourseClass> getMyStudentClasses(Authentication authentication) {
        return service.findByStudentId(UUID.fromString(authentication.getName()));
    }

    @PostMapping 
    @PreAuthorize("hasAnyRole('admin','instructor')") 
    public CourseClass create(@RequestBody CourseClass e, Authentication authentication){
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_instructor"))) {
            com.sandystereo.backend.entity.User instructor = new com.sandystereo.backend.entity.User();
            instructor.setId(UUID.fromString(authentication.getName()));
            e.setInstructor(instructor);
        }
        return service.save(e);
    }    

    @PutMapping("/{id}") 
    @PreAuthorize("hasAnyRole('admin','instructor')") 
    public CourseClass update(@PathVariable UUID id,@RequestBody CourseClass e, Authentication authentication){
        CourseClass existing = service.findById(id);
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_instructor"))) {
            if (existing.getInstructor() == null || !existing.getInstructor().getId().equals(UUID.fromString(authentication.getName()))) {
                throw new RuntimeException("Unauthorized to update this class");
            }
        }
        e.setId(id);
        return service.save(e);
    }    

    @DeleteMapping("/{id}") 
    @PreAuthorize("hasAnyRole('admin','instructor')") 
    public void delete(@PathVariable UUID id, Authentication authentication){
        CourseClass existing = service.findById(id);
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_instructor"))) {
            if (existing.getInstructor() == null || !existing.getInstructor().getId().equals(UUID.fromString(authentication.getName()))) {
                throw new RuntimeException("Unauthorized to delete this class");
            }
        }
        service.deleteById(id);
    }    
}
