package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();
    User findById(UUID id);
    User save(User user);
    void deleteById(UUID id);
    User getCurrentUser();
}
