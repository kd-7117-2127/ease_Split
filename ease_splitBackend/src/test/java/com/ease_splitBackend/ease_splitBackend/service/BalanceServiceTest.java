package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.BalanceResponse;
import com.ease_splitBackend.ease_splitBackend.entity.*;
import com.ease_splitBackend.ease_splitBackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
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
class BalanceServiceTest {

    @Mock private EventRepository eventRepository;
    @Mock private EventMemberRepository eventMemberRepository;
    @Mock private ExpenseRepository expenseRepository;
    @Mock private ExpenseSplitRepository expenseSplitRepository;

    @InjectMocks private BalanceService balanceService;

    private Event event;
    private User krish, aman, rahul;
    private EventMember m1, m2, m3;

    @BeforeEach
    void setUp() {
        event = new Event("Goa Trip", "Trip");
        krish = new User(1L, "Krish", "krish@example.com");
        aman = new User(2L, "Aman", "aman@example.com");
        rahul = new User(3L, "Rahul", "rahul@example.com");

        m1 = new EventMember(event, krish);
        m2 = new EventMember(event, aman);
        m3 = new EventMember(event, rahul);
    }

    @Test
    void calculateBalances_ShouldComputeCorrectNetBalances() {
        when(eventRepository.existsById(1L)).thenReturn(true);
        when(eventMemberRepository.findByEventId(1L)).thenReturn(List.of(m1, m2, m3));

        Expense exp1 = new Expense(event, krish, "Hotel", new BigDecimal("300.00"));
        Expense exp2 = new Expense(event, aman, "Dinner", new BigDecimal("150.00"));
        when(expenseRepository.findByEventId(1L)).thenReturn(List.of(exp1, exp2));

        ExpenseSplit s1 = new ExpenseSplit(exp1, krish, new BigDecimal("100.00"));
        ExpenseSplit s2 = new ExpenseSplit(exp1, aman, new BigDecimal("100.00"));
        ExpenseSplit s3 = new ExpenseSplit(exp1, rahul, new BigDecimal("100.00"));

        ExpenseSplit s4 = new ExpenseSplit(exp2, krish, new BigDecimal("50.00"));
        ExpenseSplit s5 = new ExpenseSplit(exp2, aman, new BigDecimal("50.00"));
        ExpenseSplit s6 = new ExpenseSplit(exp2, rahul, new BigDecimal("50.00"));

        when(expenseSplitRepository.findByExpenseEventId(1L)).thenReturn(List.of(s1, s2, s3, s4, s5, s6));

        List<BalanceResponse> balances = balanceService.calculateBalances(1L);

        assertEquals(3, balances.size());
        BalanceResponse krishBal = balances.stream().filter(b -> b.getName().equals("Krish")).findFirst().orElseThrow();
        BalanceResponse amanBal = balances.stream().filter(b -> b.getName().equals("Aman")).findFirst().orElseThrow();
        BalanceResponse rahulBal = balances.stream().filter(b -> b.getName().equals("Rahul")).findFirst().orElseThrow();

        // Krish: +300 paid - 150 share = +150
        assertEquals(new BigDecimal("150.00"), krishBal.getBalance());
        // Aman: +150 paid - 150 share = 0
        assertEquals(new BigDecimal("0.00"), amanBal.getBalance());
        // Rahul: +0 paid - 150 share = -150
        assertEquals(new BigDecimal("-150.00"), rahulBal.getBalance());
    }
}
