package com.expensetracker.expenseTracker.service;

import com.expensetracker.expenseTracker.model.Expense;
import com.expensetracker.expenseTracker.model.ExpenseDto;
import com.expensetracker.expenseTracker.model.User;
import com.expensetracker.expenseTracker.repository.expenseRepository;
import com.expensetracker.expenseTracker.repository.userRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final expenseRepository expenseRepository;

    private final userRepository userRepo;


    public ExpenseService(expenseRepository expenseRepository, userRepository userRepo) {
        this.expenseRepository = expenseRepository;
        this.userRepo = userRepo;
    }

    public Expense addExpense(String username, ExpenseDto dto) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setExpenseName(dto.getExpenseName());
        expense.setExpenseCategory(dto.getExpenseCategory());
        expense.setExpenseAmount(dto.getExpenseAmount());
        expense.setUser(user);

        return expenseRepository.save(expense);
    }


    public List<Expense> getExpense(String username){
        User user = userRepo.findByUsername(username).orElseThrow();
        return expenseRepository.findByUserId(user.getId());
    }
}
