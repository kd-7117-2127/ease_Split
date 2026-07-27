package com.ease_splitBackend.ease_splitBackend.dto;
import java.math.BigDecimal;

public class BalanceResponse {

    private Long userId;
    private String name;
    private BigDecimal balance;

    public BalanceResponse(
            Long userId,
            String name,
            BigDecimal balance) {

        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
