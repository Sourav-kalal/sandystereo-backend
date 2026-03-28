package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.Event;
import java.util.List;
import java.util.UUID;

public interface EventService {
    List<Event> findAll();
    Event findById(UUID id);
    Event save(Event event);
    Event update(UUID id, Event event);
    void deleteById(UUID id);
}
