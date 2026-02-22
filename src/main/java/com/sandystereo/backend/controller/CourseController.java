package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.Course;
import com.sandystereo.backend.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseRepository repository;
    public CourseController(CourseRepository repository){this.repository=repository;}
    @GetMapping public List<Course> list(){return repository.findAll();}
    @GetMapping("/{id}") public Course get(@PathVariable UUID id){return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));}
    @PostMapping @PreAuthorize("hasRole('admin')") public Course create(@RequestBody Course e){return repository.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public Course update(@PathVariable UUID id,@RequestBody Course e){e.setId(id);return repository.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){repository.deleteById(id);}    
}
