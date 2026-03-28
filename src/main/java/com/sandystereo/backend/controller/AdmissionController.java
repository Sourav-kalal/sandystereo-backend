package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.Admission;
import com.sandystereo.backend.service.AdmissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.sandystereo.backend.service.AdmissionService;

@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {
    private final AdmissionService service;
    public AdmissionController(AdmissionService service){this.service=service;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<Admission> list(){return service.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public Admission get(@PathVariable UUID id){return service.findById(id);}
    @PostMapping public Admission create(@RequestBody Admission e){return service.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public Admission update(@PathVariable UUID id,@RequestBody Admission e){e.setId(id);return service.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){service.deleteById(id);}    
}
