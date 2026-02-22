package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.User;
import com.sandystereo.backend.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('admin')")
public class UserController {
    private final UserRepository repository;
    public UserController(UserRepository repository){this.repository=repository;}
    @GetMapping public List<User> list(){return repository.findAll();}
    @GetMapping("/{id}") public User get(@PathVariable UUID id){return repository.findById(id).orElseThrow();}
    @DeleteMapping("/{id}") public void delete(@PathVariable UUID id){repository.deleteById(id);}    
}
