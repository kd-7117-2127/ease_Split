package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.BalanceResponse;
import com.ease_splitBackend.ease_splitBackend.entity.EventMember;
import com.ease_splitBackend.ease_splitBackend.entity.Expense;
import com.ease_splitBackend.ease_splitBackend.entity.ExpenseSplit;
import com.ease_splitBackend.ease_splitBackend.entity.User;
import com.ease_splitBackend.ease_splitBackend.exception.ResourceNotFoundException;
import com.ease_splitBackend.ease_splitBackend.repository.EventMemberRepository;
import com.ease_splitBackend.ease_splitBackend.repository.EventRepository;
import com.ease_splitBackend.ease_splitBackend.repository.ExpenseRepository;
import com.ease_splitBackend.ease_splitBackend.repository.ExpenseSplitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BalanceService {

    private final EventRepository eventRepository;
    private final EventMemberRepository eventMemberRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;

    public BalanceService(
            EventRepository eventRepository,
            EventMemberRepository eventMemberRepository,
            ExpenseRepository expenseRepository,
            ExpenseSplitRepository expenseSplitRepository) {

        this.eventRepository = eventRepository;
        this.eventMemberRepository = eventMemberRepository;
        this.expenseRepository = expenseRepository;
        this.expenseSplitRepository = expenseSplitRepository;
    }

    @Transactional(readOnly = true)
    public List<BalanceResponse> calculateBalances(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found with ID: " + eventId);
        }

        List<EventMember> members = eventMemberRepository.findByEventId(eventId);
        List<Expense> expenses = expenseRepository.findByEventId(eventId);
        List<ExpenseSplit> splits = expenseSplitRepository.findByExpenseEventId(eventId);

        Map<Long, BigDecimal> balances = new HashMap<>();

        for (EventMember member : members) {
            balances.put(member.getUser().getId(), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        }

        for (Expense expense : expenses) {
            Long payerId = expense.getPaidBy().getId();
            if (balances.containsKey(payerId)) {
                balances.put(payerId, balances.get(payerId).add(expense.getAmount()));
            }
        }

        for (ExpenseSplit split : splits) {
            Long userId = split.getUser().getId();
            if (balances.containsKey(userId)) {
                balances.put(userId, balances.get(userId).subtract(split.getShareAmount()));
            }
        }

        List<BalanceResponse> result = new ArrayList<>();
        for (EventMember member : members) {
            User user = member.getUser();
            BigDecimal netBalance = balances.getOrDefault(user.getId(), BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
            result.add(new BalanceResponse(user.getId(), user.getName(), netBalance));
        }

        return result;
    }
}
