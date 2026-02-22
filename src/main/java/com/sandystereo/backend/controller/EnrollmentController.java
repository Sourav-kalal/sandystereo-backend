package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.Enrollment;
import com.sandystereo.backend.repository.EnrollmentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentRepository repository;
    public EnrollmentController(EnrollmentRepository repository){this.repository=repository;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<Enrollment> list(){return repository.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public Enrollment get(@PathVariable UUID id){return repository.findById(id).orElseThrow();}
    @PostMapping @PreAuthorize("hasRole('admin')") public Enrollment create(@RequestBody Enrollment e){return repository.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public Enrollment update(@PathVariable UUID id,@RequestBody Enrollment e){e.setId(id);return repository.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){repository.deleteById(id);}    
}
