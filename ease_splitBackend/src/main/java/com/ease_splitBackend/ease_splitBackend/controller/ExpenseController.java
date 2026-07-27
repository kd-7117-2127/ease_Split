package com.ease_splitBackend.ease_splitBackend.controller;

import com.ease_splitBackend.ease_splitBackend.dto.CreateExpenseRequest;
import com.ease_splitBackend.ease_splitBackend.dto.ExpenseResponse;
import com.ease_splitBackend.ease_splitBackend.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResponse createExpense(
            @PathVariable Long eventId,
            @Valid @RequestBody CreateExpenseRequest request) {

        return expenseService.createExpense(eventId, request);
    }

    @GetMapping
    public List<ExpenseResponse> getExpenses(
            @PathVariable Long eventId) {

        return expenseService.getExpenses(eventId);
    }
}
