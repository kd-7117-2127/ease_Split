package com.ease_splitBackend.ease_splitBackend.repository;

import com.ease_splitBackend.ease_splitBackend.entity.EventMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventMemberRepository
        extends JpaRepository<EventMember, Long> {

    List<EventMember> findByEventId(Long eventId);

    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    Optional<EventMember> findByEventIdAndUserId(Long eventId, Long userId);
}
