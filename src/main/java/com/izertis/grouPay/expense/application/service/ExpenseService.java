package com.izertis.grouPay.expense.application.service;

import com.izertis.grouPay.expense.application.exception.ExpenseNotFoundException;
import com.izertis.grouPay.expense.domain.model.Expense;
import com.izertis.grouPay.expense.domain.repository.ExpenseRepository;
import com.izertis.grouPay.friend.application.exception.FriendNotFoundException;
import com.izertis.grouPay.friend.domain.model.Friend;
import com.izertis.grouPay.friend.domain.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final FriendRepository friendRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, FriendRepository friendRepository) {
        this.expenseRepository = expenseRepository;
        this.friendRepository = friendRepository;
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
        if (!friendRepository.existsById(expense.getFriend().getId())) throw new FriendNotFoundException("Friend not found");
        expense.setDate(new Timestamp(System.currentTimeMillis()));
        expenseRepository.save(expense);
    }

    public void updateExpense(Expense expense) {
        if (expense.getAmount() <= 0) throw new IllegalArgumentException("Amount must be greater than 0");
        expense.setFriend(new Friend(0L, ""));
        expenseRepository.update(expense);
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
