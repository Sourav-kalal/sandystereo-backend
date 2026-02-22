package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.Event;
import com.sandystereo.backend.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventRepository repository;
    public EventController(EventRepository repository){this.repository=repository;}
    @GetMapping public List<Event> list(){return repository.findAll();}
    @GetMapping("/{id}") public Event get(@PathVariable UUID id){return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found"));}
    @PostMapping @PreAuthorize("hasRole('admin')") public Event create(@RequestBody Event e){return repository.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public Event update(@PathVariable UUID id,@RequestBody Event e){e.setId(id);return repository.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){repository.deleteById(id);}    
}
