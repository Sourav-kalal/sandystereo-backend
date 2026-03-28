package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.User;
import com.sandystereo.backend.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.sandystereo.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('admin')")
public class UserController {
    private final UserService service;
    public UserController(UserService service){this.service=service;}
    @GetMapping public List<User> list(){return service.findAll();}
    @GetMapping("/{id}") public User get(@PathVariable UUID id){return service.findById(id);}
    @DeleteMapping("/{id}") public void delete(@PathVariable UUID id){service.deleteById(id);}    
}
