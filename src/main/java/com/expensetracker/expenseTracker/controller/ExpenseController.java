package com.expensetracker.expenseTracker.controller;

import com.expensetracker.expenseTracker.model.Expense;
import com.expensetracker.expenseTracker.model.ExpenseDto;
import com.expensetracker.expenseTracker.service.ExpenseService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }



    @PostMapping
    public Expense addExpense(@AuthenticationPrincipal UserDetails user, @Valid @RequestBody ExpenseDto expense) {
        System.out.println("USER: " + user);
        System.out.println("EXPENSE: " + expense);

        return service.addExpense(user.getUsername(), expense);

    }

    @GetMapping
    public List<Expense> getExpenses(@AuthenticationPrincipal UserDetails  user) {
        return service.getExpense(user.getUsername());

    }


    @GetMapping("/test")
    public String test() {
        System.out.println("HIT");
        return "OK";
    }

}
