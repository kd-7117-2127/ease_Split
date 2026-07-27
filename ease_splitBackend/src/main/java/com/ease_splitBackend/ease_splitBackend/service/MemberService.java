package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.AddMemberRequest;
import com.ease_splitBackend.ease_splitBackend.dto.MemberResponse;
import com.ease_splitBackend.ease_splitBackend.entity.Event;
import com.ease_splitBackend.ease_splitBackend.entity.EventMember;
import com.ease_splitBackend.ease_splitBackend.entity.User;
import com.ease_splitBackend.ease_splitBackend.exception.BadRequestException;
import com.ease_splitBackend.ease_splitBackend.exception.ResourceNotFoundException;
import com.ease_splitBackend.ease_splitBackend.repository.EventMemberRepository;
import com.ease_splitBackend.ease_splitBackend.repository.EventRepository;
import com.ease_splitBackend.ease_splitBackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    public MemberResponse addMember(Long eventId, AddMemberRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        User user;
        String email = request.getEmail() != null ? request.getEmail().trim().toLowerCase() : null;

        if (email != null && !email.isEmpty()) {
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent()) {
                user = existingUser.get();
                if (eventMemberRepository.existsByEventIdAndUserId(eventId, user.getId())) {
                    throw new BadRequestException("User with email '" + email + "' is already a member of this event");
                }
            } else {
                user = userRepository.save(new User(request.getName().trim(), email));
            }
        } else {
            user = userRepository.save(new User(request.getName().trim(), null));
        }

        EventMember eventMember = new EventMember(event, user);
        EventMember saved = eventMemberRepository.save(eventMember);
        return MemberResponse.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found with ID: " + eventId);
        }
        return eventMemberRepository.findByEventId(eventId).stream()
                .map(MemberResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
