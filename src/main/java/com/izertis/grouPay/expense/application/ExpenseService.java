package com.izertis.grouPay.expense.application;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.domain.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            return expenseRepository.findById(id);
        } else {
            throw new ExpenseNotFoundException("Expense not found");
        }
    }

    public void createExpense(Expense expense) {
        if (expense.getAmount() <= 0) throw new IllegalArgumentException("Amount must be greater than 0");
        expense.setDate(new Timestamp(System.currentTimeMillis()));
        expenseRepository.save(expense);
    }

    public void deleteExpenses() {
        expenseRepository.deleteAll();
    }

    public void deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
        } else {
            throw new ExpenseNotFoundException("Expense not found");
        }
    }

}
