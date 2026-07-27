package com.ease_splitBackend.ease_splitBackend.controller;

import com.ease_splitBackend.ease_splitBackend.dto.AddMemberRequest;
import com.ease_splitBackend.ease_splitBackend.dto.MemberResponse;
import com.ease_splitBackend.ease_splitBackend.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse addMember(
            @PathVariable Long eventId,
            @Valid @RequestBody AddMemberRequest request) {

        return memberService.addMember(eventId, request);
    }

    @GetMapping
    public List<MemberResponse> getMembers(
            @PathVariable Long eventId) {

        return memberService.getMembers(eventId);
    }
}
