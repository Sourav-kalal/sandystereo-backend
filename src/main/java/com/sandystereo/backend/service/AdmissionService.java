package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.Admission;
import java.util.List;
import java.util.UUID;

public interface AdmissionService {
    List<Admission> findAll();
    Admission findById(UUID id);
    Admission save(Admission admission);
    void deleteById(UUID id);
}
