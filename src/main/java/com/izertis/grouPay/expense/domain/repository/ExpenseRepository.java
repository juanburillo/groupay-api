package com.izertis.grouPay.expense.domain.repository;

import com.izertis.grouPay.expense.domain.model.Expense;

import java.util.List;

public interface ExpenseRepository {

    List<Expense> findAll();
    Expense findById(Long id);
    void save(Expense expense);
    void update(Expense expense);
    void deleteAll();
    void deleteById(Long id);
    boolean existsById(Long id);

}
