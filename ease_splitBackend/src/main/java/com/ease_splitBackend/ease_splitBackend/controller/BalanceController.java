package com.ease_splitBackend.ease_splitBackend.controller;

import com.ease_splitBackend.ease_splitBackend.dto.BalanceResponse;
import com.ease_splitBackend.ease_splitBackend.service.BalanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/balances")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(
            BalanceService balanceService) {

        this.balanceService = balanceService;
    }

    @GetMapping
    public List<BalanceResponse> getBalances(
            @PathVariable Long eventId) {

        return balanceService.calculateBalances(eventId);
    }
}
