package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.expense.application.ExpenseService;
import com.izertis.grouPay.expense.infrastructure.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<ExpenseResponse> getExpenses() {
        return ExpenseMapper.toDtoList(expenseService.getExpenses());
    }

    @GetMapping("/{id}")
    public ExpenseResponse getExpense(@PathVariable Long id) {
        return ExpenseMapper.toDto(expenseService.getExpense(id));
    }

    @PostMapping
    public void createExpense(@RequestBody ExpenseRequest expenseRequest) {
        expenseService.createExpense(ExpenseMapper.toModel(expenseRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

}
