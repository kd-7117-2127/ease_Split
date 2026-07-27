package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.EventResponse;
import com.ease_splitBackend.ease_splitBackend.entity.Event;
import com.ease_splitBackend.ease_splitBackend.exception.ResourceNotFoundException;
import com.ease_splitBackend.ease_splitBackend.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public EventResponse createEvent(Event event) {
        Event saved = eventRepository.save(event);
        return EventResponse.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public EventResponse getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));
        return EventResponse.fromEntity(event);
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(EventResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
