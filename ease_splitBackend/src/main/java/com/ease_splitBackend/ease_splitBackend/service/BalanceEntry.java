package com.easesplit.ease_splitBackend.service;

import java.math.BigDecimal;

public class BalanceEntry {

    private Long userId;
    private String name;
    private BigDecimal amount;

    public BalanceEntry(
            Long userId,
            String name,
            BigDecimal amount) {

        this.userId = userId;
        this.name = name;
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
