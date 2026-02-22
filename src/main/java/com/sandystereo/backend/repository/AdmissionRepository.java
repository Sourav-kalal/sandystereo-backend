package com.sandystereo.backend.repository;

import com.sandystereo.backend.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdmissionRepository extends JpaRepository<Admission, UUID> {
}
