package com.expensetracker.expenseTracker.repository;

import com.expensetracker.expenseTracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  expenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByExpenseCategory(String category);

    List<Expense> findByUserId(Long userId);
}
