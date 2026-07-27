package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.CreateExpenseRequest;
import com.ease_splitBackend.ease_splitBackend.entity.*;
import com.ease_splitBackend.ease_splitBackend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExpenseService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMemberRepository eventMemberRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;

    public ExpenseService(
            EventRepository eventRepository,
            UserRepository userRepository,
            EventMemberRepository eventMemberRepository,
            ExpenseRepository expenseRepository,
            ExpenseSplitRepository expenseSplitRepository) {

        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventMemberRepository = eventMemberRepository;
        this.expenseRepository = expenseRepository;
        this.expenseSplitRepository = expenseSplitRepository;
    }

    @Transactional
    public Expense createExpense(
            Long eventId,
            CreateExpenseRequest request) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        User payer = userRepository
                .findById(request.getPaidByUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<EventMember> members =
                eventMemberRepository.findByEventId(eventId);

        if (members.isEmpty()) {
            throw new RuntimeException(
                    "Event has no members");
        }

        Expense expense = new Expense(
                event,
                payer,
                request.getDescription(),
                request.getAmount()
        );

        expense = expenseRepository.save(expense);

        BigDecimal share = request.getAmount()
                .divide(
                        BigDecimal.valueOf(members.size()),
                        2,
                        RoundingMode.HALF_UP
                );

        for (EventMember member : members) {

            ExpenseSplit split = new ExpenseSplit(
                    expense,
                    member.getUser(),
                    share
            );

            expenseSplitRepository.save(split);
        }

        return expense;
    }

    public List<Expense> getExpenses(Long eventId) {
        return expenseRepository.findByEventId(eventId);
    }
}
