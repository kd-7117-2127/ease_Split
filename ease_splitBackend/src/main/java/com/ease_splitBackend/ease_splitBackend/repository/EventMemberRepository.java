package com.ease_splitBackend.ease_splitBackend.repository;

import com.ease_splitBackend.ease_splitBackend.entity.EventMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventMemberRepository
        extends JpaRepository<EventMember, Long> {

    List<EventMember> findByEventId(Long eventId);
}
