package com.ease_splitBackend.ease_splitBackend.service;
import com.ease_splitBackend.ease_splitBackend.dto.AddMemberRequest;
import com.ease_splitBackend.ease_splitBackend.entity.Event;
import com.ease_splitBackend.ease_splitBackend.entity.EventMember;
import com.ease_splitBackend.ease_splitBackend.entity.User;
import com.ease_splitBackend.ease_splitBackend.repository.EventMemberRepository;
import com.ease_splitBackend.ease_splitBackend.repository.EventRepository;
import com.ease_splitBackend.ease_splitBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMemberRepository eventMemberRepository;

    public MemberService(
            EventRepository eventRepository,
            UserRepository userRepository,
            EventMemberRepository eventMemberRepository) {

        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventMemberRepository = eventMemberRepository;
    }

    public EventMember addMember(
            Long eventId,
            AddMemberRequest request) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        User user = new User(
                request.getName(),
                request.getEmail()
        );

        user = userRepository.save(user);

        EventMember eventMember =
                new EventMember(event, user);

        return eventMemberRepository.save(eventMember);
    }

    public List<EventMember> getMembers(Long eventId) {
        return eventMemberRepository.findByEventId(eventId);
    }
}
