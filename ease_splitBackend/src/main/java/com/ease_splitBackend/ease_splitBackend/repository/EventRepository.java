package com.easesplit.ease_splitBackend.repository;

import com.easesplit.ease_splitBackend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
