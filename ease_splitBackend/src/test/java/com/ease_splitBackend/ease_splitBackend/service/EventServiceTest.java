package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.EventResponse;
import com.ease_splitBackend.ease_splitBackend.entity.Event;
import com.ease_splitBackend.ease_splitBackend.exception.ResourceNotFoundException;
import com.ease_splitBackend.ease_splitBackend.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        testEvent = new Event("Goa Trip", "College trip");
    }

    @Test
    void createEvent_ShouldSaveAndReturnResponse() {
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        EventResponse response = eventService.createEvent(testEvent);

        assertNotNull(response);
        assertEquals("Goa Trip", response.getName());
        assertEquals("College trip", response.getDescription());
        verify(eventRepository, times(1)).save(testEvent);
    }

    @Test
    void getEventById_WhenExists_ShouldReturnEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        EventResponse response = eventService.getEventById(1L);

        assertNotNull(response);
        assertEquals("Goa Trip", response.getName());
    }

    @Test
    void getEventById_WhenNotExists_ShouldThrowResourceNotFound() {
        when(eventRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventService.getEventById(99L));
    }

    @Test
    void getAllEvents_ShouldReturnList() {
        when(eventRepository.findAll()).thenReturn(List.of(testEvent));

        List<EventResponse> events = eventService.getAllEvents();

        assertEquals(1, events.size());
        assertEquals("Goa Trip", events.get(0).getName());
    }
}
