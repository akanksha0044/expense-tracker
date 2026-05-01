package com.expensetracker.expenseTracker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseDto {
    //@NotBlank
    private String expenseName;

    //@NotBlank
    private String expenseCategory;

    //@NotNull
    private BigDecimal expenseAmount;
}
