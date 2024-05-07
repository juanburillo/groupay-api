package com.izertis.grouPay.expense.domain;

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
