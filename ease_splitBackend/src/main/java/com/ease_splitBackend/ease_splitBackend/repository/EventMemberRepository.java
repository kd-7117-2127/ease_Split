package com.easesplit.ease_splitBackend.repository;

import com.easesplit.ease_splitBackend.entity.EventMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventMemberRepository
        extends JpaRepository<EventMember, Long> {

    List<EventMember> findByEventId(Long eventId);
}
