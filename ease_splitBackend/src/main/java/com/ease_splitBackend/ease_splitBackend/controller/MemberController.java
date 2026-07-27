package com.ease_splitBackend.ease_splitBackend.controller;

import com.ease_splitBackend.ease_splitBackend.dto.AddMemberRequest;
import com.ease_splitBackend.ease_splitBackend.entity.EventMember;
import com.ease_splitBackend.ease_splitBackend.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public EventMember addMember(
            @PathVariable Long eventId,
            @RequestBody AddMemberRequest request) {

        return memberService.addMember(eventId, request);
    }

    @GetMapping
    public List<EventMember> getMembers(
            @PathVariable Long eventId) {

        return memberService.getMembers(eventId);
    }
}
