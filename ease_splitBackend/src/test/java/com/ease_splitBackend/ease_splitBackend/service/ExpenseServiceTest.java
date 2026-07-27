package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.CreateExpenseRequest;
import com.ease_splitBackend.ease_splitBackend.dto.ExpenseResponse;
import com.ease_splitBackend.ease_splitBackend.entity.*;
import com.ease_splitBackend.ease_splitBackend.exception.BadRequestException;
import com.ease_splitBackend.ease_splitBackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock private EventRepository eventRepository;
    @Mock private UserRepository userRepository;
    @Mock private EventMemberRepository eventMemberRepository;
    @Mock private ExpenseRepository expenseRepository;
    @Mock private ExpenseSplitRepository expenseSplitRepository;

    @InjectMocks private ExpenseService expenseService;

    private Event event;
    private User user1, user2, user3;
    private EventMember member1, member2, member3;

    @BeforeEach
    void setUp() {
        event = new Event("Goa Trip", "Trip");
        user1 = new User(1L, "Krish", "krish@example.com");
        user2 = new User(2L, "Aman", "aman@example.com");
        user3 = new User(3L, "Rahul", "rahul@example.com");

        member1 = new EventMember(event, user1);
        member2 = new EventMember(event, user2);
        member3 = new EventMember(event, user3);
    }

    @Test
    void createExpense_ShouldSplitEquallyAndDistributeRemainder() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(eventMemberRepository.existsByEventIdAndUserId(1L, 1L)).thenReturn(true);
        when(eventMemberRepository.findByEventId(1L)).thenReturn(List.of(member1, member2, member3));

        Expense mockExpense = new Expense(event, user1, "Hotel", new BigDecimal("100.00"));
        when(expenseRepository.save(any(Expense.class))).thenReturn(mockExpense);

        CreateExpenseRequest request = new CreateExpenseRequest(1L, "Hotel", new BigDecimal("100.00"));
        ExpenseResponse response = expenseService.createExpense(1L, request);

        assertNotNull(response);
        assertEquals("Hotel", response.getDescription());

        ArgumentCaptor<ExpenseSplit> splitCaptor = ArgumentCaptor.forClass(ExpenseSplit.class);
        verify(expenseSplitRepository, times(3)).save(splitCaptor.capture());

        List<ExpenseSplit> savedSplits = splitCaptor.getAllValues();
        assertEquals(new BigDecimal("33.34"), savedSplits.get(0).getShareAmount());
        assertEquals(new BigDecimal("33.33"), savedSplits.get(1).getShareAmount());
        assertEquals(new BigDecimal("33.33"), savedSplits.get(2).getShareAmount());

        BigDecimal totalSum = savedSplits.stream()
                .map(ExpenseSplit::getShareAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(new BigDecimal("100.00"), totalSum);
    }

    @Test
    void createExpense_WhenPayerNotMember_ShouldThrowBadRequest() {
        User outsider = new User(99L, "Outsider", "out@example.com");
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(userRepository.findById(99L)).thenReturn(Optional.of(outsider));
        when(eventMemberRepository.existsByEventIdAndUserId(1L, 99L)).thenReturn(false);

        CreateExpenseRequest request = new CreateExpenseRequest(99L, "Lunch", new BigDecimal("50.00"));

        assertThrows(BadRequestException.class, () -> expenseService.createExpense(1L, request));
    }
}
