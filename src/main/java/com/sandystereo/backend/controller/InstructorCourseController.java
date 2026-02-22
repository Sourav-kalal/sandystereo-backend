package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.InstructorCourse;
import com.sandystereo.backend.repository.InstructorCourseRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/instructorcourses")
public class InstructorCourseController {
    private final InstructorCourseRepository repository;
    public InstructorCourseController(InstructorCourseRepository repository){this.repository=repository;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<InstructorCourse> list(){return repository.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public InstructorCourse get(@PathVariable UUID id){return repository.findById(id).orElseThrow();}
    @PostMapping @PreAuthorize("hasRole('admin')") public InstructorCourse create(@RequestBody InstructorCourse e){return repository.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public InstructorCourse update(@PathVariable UUID id,@RequestBody InstructorCourse e){e.setId(id);return repository.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){repository.deleteById(id);}    
}
