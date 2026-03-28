package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.Event;
import com.sandystereo.backend.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.sandystereo.backend.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;
    public EventController(EventService service){this.service=service;}
    @GetMapping public List<Event> list(){return service.findAll();}
    @GetMapping("/{id}") public Event get(@PathVariable UUID id){return service.findById(id);}
    @PostMapping @PreAuthorize("hasRole('admin')") public Event create(@RequestBody Event e){return service.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public Event update(@PathVariable UUID id,@RequestBody Event e){
        return service.update(id, e);
    }    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){service.deleteById(id);}    
}
