package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.entity.User;
import com.sandystereo.backend.repository.UserRepository;
import com.sandystereo.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    public UserServiceImpl(UserRepository repository) { this.repository = repository; }

    @Override public List<User> findAll() { return repository.findAll(); }
    @Override public User findById(UUID id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found")); }
    @Override public User save(User user) { return repository.save(user); }
    @Override public void deleteById(UUID id) { repository.deleteById(id); }

    @Override
    public User getCurrentUser() {
        String userId = (String) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findById(UUID.fromString(userId));
    }
}
