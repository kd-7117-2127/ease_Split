package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.BalanceResponse;
import com.ease_splitBackend.ease_splitBackend.dto.SettlementResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    @Mock
    private BalanceService balanceService;

    @InjectMocks
    private SettlementService settlementService;

    @Test
    void calculateSettlements_ShouldMatchDebtorToCreditor() {
        List<BalanceResponse> balances = List.of(
                new BalanceResponse(1L, "Krish", new BigDecimal("150.00")),
                new BalanceResponse(2L, "Aman", new BigDecimal("0.00")),
                new BalanceResponse(3L, "Rahul", new BigDecimal("-150.00"))
        );

        when(balanceService.calculateBalances(1L)).thenReturn(balances);

        List<SettlementResponse> settlements = settlementService.calculateSettlements(1L);

        assertEquals(1, settlements.size());
        SettlementResponse s = settlements.get(0);
        assertEquals("Rahul", s.getFromUserName());
        assertEquals("Krish", s.getToUserName());
        assertEquals(new BigDecimal("150.00"), s.getAmount());
    }
}
