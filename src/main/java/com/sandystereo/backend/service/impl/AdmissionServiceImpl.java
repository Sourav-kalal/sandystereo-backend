package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.entity.Admission;
import com.sandystereo.backend.repository.AdmissionRepository;
import com.sandystereo.backend.service.AdmissionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdmissionServiceImpl implements AdmissionService {
    private final AdmissionRepository repository;
    public AdmissionServiceImpl(AdmissionRepository repository) { this.repository = repository; }

    @Override public List<Admission> findAll() { return repository.findAll(); }
    @Override public Admission findById(UUID id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Admission not found")); }
    @Override public Admission save(Admission admission) { return repository.save(admission); }
    @Override public void deleteById(UUID id) { repository.deleteById(id); }
}
