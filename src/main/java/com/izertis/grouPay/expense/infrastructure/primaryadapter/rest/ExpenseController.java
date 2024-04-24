package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.expense.application.ExpenseService;
import com.izertis.grouPay.expense.infrastructure.ExpenseMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(summary = "Get all expenses")
    @GetMapping
    public List<ExpenseResponse> getExpenses() {
        return ExpenseMapper.INSTANCE.toDtoList(expenseService.getExpenses());
    }

    @Operation(summary = "Get an expense by its ID")
    @GetMapping("/{id}")
    public ExpenseResponse getExpense(@PathVariable Long id) {
        return ExpenseMapper.INSTANCE.toDto(expenseService.getExpense(id));
    }

    @Operation(summary = "Create an expense in the database")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createExpense(@RequestBody ExpenseRequest expenseRequest) {
        expenseService.createExpense(ExpenseMapper.INSTANCE.toModel(expenseRequest));
    }

    @Operation(summary = "Delete an expense by its ID")
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

}
