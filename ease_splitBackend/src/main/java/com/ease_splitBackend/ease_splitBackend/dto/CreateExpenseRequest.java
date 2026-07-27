package com.ease_splitBackend.ease_splitBackend.dto;
import java.math.BigDecimal;

public class CreateExpenseRequest {

    private Long paidByUserId;
    private String description;
    private BigDecimal amount;

    public CreateExpenseRequest() {
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
