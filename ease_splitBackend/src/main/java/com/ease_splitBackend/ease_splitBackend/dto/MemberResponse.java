package com.ease_splitBackend.ease_splitBackend.dto;

import com.ease_splitBackend.ease_splitBackend.entity.EventMember;

import java.time.LocalDateTime;

public class MemberResponse {
    private Long id;
    private Long eventId;
    private Long userId;
    private String name;
    private String email;
    private LocalDateTime joinedAt;

    public MemberResponse() {
    }

    public MemberResponse(Long id, Long eventId, Long userId, String name, String email, LocalDateTime joinedAt) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.joinedAt = joinedAt;
    }

    public static MemberResponse fromEntity(EventMember member) {
        return new MemberResponse(
                member.getId(),
                member.getEvent().getId(),
                member.getUser().getId(),
                member.getUser().getName(),
                member.getUser().getEmail(),
                member.getJoinedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
}
