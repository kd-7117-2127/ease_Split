package com.ease_splitBackend.ease_splitBackend.controller;

import com.ease_splitBackend.ease_splitBackend.dto.EventResponse;
import com.ease_splitBackend.ease_splitBackend.entity.Event;
import com.ease_splitBackend.ease_splitBackend.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(@Valid @RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{eventId}")
    public EventResponse getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }
}
