package com.izertis.grouPay.expense.application;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.domain.ExpenseRepository;
import com.izertis.grouPay.friend.application.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final FriendService friendService;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, FriendService friendService) {
        this.expenseRepository = expenseRepository;
        this.friendService = friendService;
    }

    public List<Expense> getExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        for (Expense expense : expenses) {
            setExpenseFriend(expense);
        }
        return expenses;
    }

    public Expense getExpense(Long id) {
        Expense expense = expenseRepository.findById(id);
        setExpenseFriend(expense);
        return expense;
    }

    public void createExpense(Expense expense) {
        if (expense.getAmount() <= 0) throw new RuntimeException("Amount must be greater than 0");
        expense.setDate(new Timestamp(System.currentTimeMillis()));
        setExpenseFriend(expense);
        expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    private void setExpenseFriend(Expense expense) {
        expense.setFriend(friendService.getFriend(expense.getFriend().getId()));
    }

}
