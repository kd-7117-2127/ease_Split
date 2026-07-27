package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.CreateExpenseRequest;
import com.ease_splitBackend.ease_splitBackend.dto.ExpenseResponse;
import com.ease_splitBackend.ease_splitBackend.entity.*;
import com.ease_splitBackend.ease_splitBackend.exception.BadRequestException;
import com.ease_splitBackend.ease_splitBackend.exception.ResourceNotFoundException;
import com.ease_splitBackend.ease_splitBackend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

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
    public ExpenseResponse createExpense(Long eventId, CreateExpenseRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        User payer = userRepository.findById(request.getPaidByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getPaidByUserId()));

        if (!eventMemberRepository.existsByEventIdAndUserId(eventId, payer.getId())) {
            throw new BadRequestException("User '" + payer.getName() + "' is not a member of this event");
        }

        List<EventMember> members = eventMemberRepository.findByEventId(eventId);
        if (members.isEmpty()) {
            throw new BadRequestException("Event has no members to split expenses with");
        }

        BigDecimal amount = request.getAmount().setScale(2, RoundingMode.HALF_UP);
        Expense expense = new Expense(event, payer, request.getDescription().trim(), amount);
        expense = expenseRepository.save(expense);

        // Remainder-aware equal split calculation
        int memberCount = members.size();
        BigDecimal baseShare = amount.divide(BigDecimal.valueOf(memberCount), 2, RoundingMode.FLOOR);
        BigDecimal allocatedTotal = baseShare.multiply(BigDecimal.valueOf(memberCount));
        BigDecimal remainder = amount.subtract(allocatedTotal);
        int extraCents = remainder.multiply(BigDecimal.valueOf(100)).intValue();

        for (int i = 0; i < memberCount; i++) {
            EventMember member = members.get(i);
            BigDecimal share = baseShare;
            if (i < extraCents) {
                share = share.add(new BigDecimal("0.01"));
            }

            ExpenseSplit split = new ExpenseSplit(expense, member.getUser(), share);
            expenseSplitRepository.save(split);
        }

        return ExpenseResponse.fromEntity(expense);
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpenses(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found with ID: " + eventId);
        }
        return expenseRepository.findByEventId(eventId).stream()
                .map(ExpenseResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
