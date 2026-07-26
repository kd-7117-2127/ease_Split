package com.easesplit.ease_splitBackend.controller;

import com.easesplit.ease_splitBackend.dto.CreateExpenseRequest;
import com.easesplit.ease_splitBackend.entity.Expense;
import com.easesplit.ease_splitBackend.service.ExpenseService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(
            ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense createExpense(
            @PathVariable Long eventId,
            @RequestBody CreateExpenseRequest request) {

        return expenseService.createExpense(
                eventId,
                request
        );
    }

    @GetMapping
    public List<Expense> getExpenses(
            @PathVariable Long eventId) {

        return expenseService.getExpenses(eventId);
    }
}
