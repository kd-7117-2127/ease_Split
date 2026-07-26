package com.easesplit.ease_splitBackend.controller;

import com.easesplit.ease_splitBackend.dto.SettlementResponse;
import com.easesplit.ease_splitBackend.service.SettlementService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/settlements")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(
            SettlementService settlementService) {

        this.settlementService = settlementService;
    }

    @GetMapping
    public List<SettlementResponse> getSettlements(
            @PathVariable Long eventId) {

        return settlementService
                .calculateSettlements(eventId);
    }
}
