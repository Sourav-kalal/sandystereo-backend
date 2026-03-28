package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.Course;
import com.sandystereo.backend.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.sandystereo.backend.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService service;
    public CourseController(CourseService service){this.service=service;}
    @GetMapping public List<Course> list(){return service.findAll();}
    @GetMapping("/{id}") public Course get(@PathVariable UUID id){return service.findById(id);}
    @PostMapping @PreAuthorize("hasRole('admin')") public Course create(@RequestBody Course e){return service.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public Course update(@PathVariable UUID id,@RequestBody Course e){
        return service.update(id, e);
    }    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){service.deleteById(id);}    

    @GetMapping("/my")
    @PreAuthorize("hasRole('instructor')")
    public List<Course> getMyCourses(org.springframework.security.core.Authentication authentication) {
        return service.findByInstructorId(UUID.fromString(authentication.getName()));
    }
}
