package com.ease_splitBackend.ease_splitBackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateExpenseRequest {

    @NotNull(message = "Payer user ID is required")
    private Long paidByUserId;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    public CreateExpenseRequest() {
    }

    public CreateExpenseRequest(Long paidByUserId, String description, BigDecimal amount) {
        this.paidByUserId = paidByUserId;
        this.description = description;
        this.amount = amount;
    }

    public Long getPaidByUserId() {
        return paidByUserId;
    }

    public void setPaidByUserId(Long paidByUserId) {
        this.paidByUserId = paidByUserId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
