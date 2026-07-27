package com.ease_splitBackend.ease_splitBackend.dto;

import com.ease_splitBackend.ease_splitBackend.entity.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenseResponse {
    private Long id;
    private Long eventId;
    private Long paidByUserId;
    private String paidByName;
    private String description;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    public ExpenseResponse() {
    }

    public ExpenseResponse(Long id, Long eventId, Long paidByUserId, String paidByName, String description, BigDecimal amount, LocalDateTime createdAt) {
        this.id = id;
        this.eventId = eventId;
        this.paidByUserId = paidByUserId;
        this.paidByName = paidByName;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public static ExpenseResponse fromEntity(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getEvent().getId(),
                expense.getPaidBy().getId(),
                expense.getPaidBy().getName(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getPaidByUserId() {
        return paidByUserId;
    }

    public String getPaidByName() {
        return paidByName;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
