package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.entity.Event;
import com.sandystereo.backend.repository.EventRepository;
import com.sandystereo.backend.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
    public EventServiceImpl(EventRepository repository) { this.repository = repository; }

    @Override public List<Event> findAll() { return repository.findAll(); }
    @Override public Event findById(UUID id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found")); }
    @Override public Event save(Event event) { return repository.save(event); }
    @Override public Event update(UUID id, Event e) {
        Event existing = findById(id);
        if (e.getName() != null) existing.setName(e.getName());
        if (e.getDescription() != null) existing.setDescription(e.getDescription());
        if (e.getEventDate() != null) existing.setEventDate(e.getEventDate());
        if (e.getBannerUrl() != null) existing.setBannerUrl(e.getBannerUrl());
        if (e.getGoogleFormLink() != null) existing.setGoogleFormLink(e.getGoogleFormLink());
        if (e.getIsActive() != null) existing.setIsActive(e.getIsActive());
        return repository.save(existing);
    }
    @Override public void deleteById(UUID id) { repository.deleteById(id); }
}
