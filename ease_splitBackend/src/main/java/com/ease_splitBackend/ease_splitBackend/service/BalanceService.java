package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.BalanceResponse;
import com.ease_splitBackend.ease_splitBackend.entity.EventMember;
import com.ease_splitBackend.ease_splitBackend.entity.Expense;
import com.ease_splitBackend.ease_splitBackend.entity.ExpenseSplit;
import com.ease_splitBackend.ease_splitBackend.entity.User;
import com.ease_splitBackend.ease_splitBackend.repository.EventMemberRepository;
import com.ease_splitBackend.ease_splitBackend.repository.EventRepository;
import com.ease_splitBackend.ease_splitBackend.repository.ExpenseRepository;
import com.ease_splitBackend.ease_splitBackend.repository.ExpenseSplitRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public List<BalanceResponse> calculateBalances(Long eventId) {

        eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        List<EventMember> members =
                eventMemberRepository.findByEventId(eventId);

        List<Expense> expenses =
                expenseRepository.findByEventId(eventId);

        List<ExpenseSplit> splits =
                expenseSplitRepository.findByExpenseEventId(eventId);

        Map<Long, BigDecimal> balances = new HashMap<>();

        // Start every member at zero
        for (EventMember member : members) {
            balances.put(
                    member.getUser().getId(),
                    BigDecimal.ZERO
            );
        }

        // Money paid increases balance
        for (Expense expense : expenses) {

            Long payerId = expense.getPaidBy().getId();

            balances.put(
                    payerId,
                    balances.get(payerId)
                            .add(expense.getAmount())
            );
        }

        // Share owed decreases balance
        for (ExpenseSplit split : splits) {

            Long userId = split.getUser().getId();

            balances.put(
                    userId,
                    balances.get(userId)
                            .subtract(split.getShareAmount())
            );
        }

        List<BalanceResponse> result = new ArrayList<>();

        for (EventMember member : members) {

            User user = member.getUser();

            result.add(
                    new BalanceResponse(
                            user.getId(),
                            user.getName(),
                            balances.get(user.getId())
                    )
            );
        }

        return result;
    }
}
