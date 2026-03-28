package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.Profile;
import java.util.List;
import java.util.UUID;

public interface ProfileService {
    List<Profile> findAll();
    Profile findById(UUID id);
    Profile save(Profile profile);
    Profile update(UUID id, Profile profile);
    void deleteById(UUID id);
}
