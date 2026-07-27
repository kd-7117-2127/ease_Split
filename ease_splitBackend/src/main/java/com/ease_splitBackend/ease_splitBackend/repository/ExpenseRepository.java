package com.ease_splitBackend.ease_splitBackend.repository;

import com.ease_splitBackend.ease_splitBackend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    List<Expense> findByEventId(Long eventId);
}
