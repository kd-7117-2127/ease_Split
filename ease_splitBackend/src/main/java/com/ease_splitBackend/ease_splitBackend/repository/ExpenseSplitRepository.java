package com.ease_splitBackend.ease_splitBackend.repository;

import com.ease_splitBackend.ease_splitBackend.entity.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseSplitRepository
        extends JpaRepository<ExpenseSplit, Long> {

    List<ExpenseSplit> findByExpenseId(Long expenseId);

    List<ExpenseSplit> findByExpenseEventId(Long eventId);
}
