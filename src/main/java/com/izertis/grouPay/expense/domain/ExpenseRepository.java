package com.izertis.grouPay.expense.domain;

import java.util.List;

public interface ExpenseRepository {

    List<Expense> findAll();
    Expense findById(Long id);
    void save(Expense expense);
    void deleteById(Long id);

}
