package com.ease_splitBackend.ease_splitBackend.dto;

import com.ease_splitBackend.ease_splitBackend.entity.Event;

import java.time.LocalDateTime;

public class EventResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public EventResponse() {
    }

    public EventResponse(Long id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static EventResponse fromEntity(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
