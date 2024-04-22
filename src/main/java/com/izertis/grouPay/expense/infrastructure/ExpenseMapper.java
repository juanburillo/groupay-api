package com.izertis.grouPay.expense.infrastructure;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.ExpenseRequest;
import com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.ExpenseResponse;
import com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.ExpenseEntity;
import com.izertis.grouPay.friend.domain.Friend;

import java.util.ArrayList;
import java.util.List;

public class ExpenseMapper {

    public static Expense toExpense(ExpenseRequest expenseRequest) {
        return new Expense(
                expenseRequest.getId(),
                expenseRequest.getAmount(),
                expenseRequest.getDescription(),
                expenseRequest.getDate(),
                new Friend(expenseRequest.getFriendId())
        );
    }

    public static Expense toExpense(ExpenseEntity expenseEntity) {
        return new Expense(
                expenseEntity.getId(),
                expenseEntity.getAmount(),
                expenseEntity.getDescription(),
                expenseEntity.getDate(),
                new Friend(expenseEntity.getId())
        );
    }

    public static List<Expense> toExpenses(List<ExpenseEntity> expenseEntities) {
        List<Expense> expenseList = new ArrayList<>();
        for (ExpenseEntity expenseEntity : expenseEntities) {
            expenseList.add(toExpense(expenseEntity));
        }
        return expenseList;
    }

    public static ExpenseResponse toExpenseResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getDate(),
                expense.getFriend()
        );
    }

    public static List<ExpenseResponse> toExpenseResponses(List<Expense> expenses) {
        List<ExpenseResponse> expenseResponses = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseResponses.add(
                    new ExpenseResponse(
                            expense.getId(),
                            expense.getAmount(),
                            expense.getDescription(),
                            expense.getDate(),
                            expense.getFriend()
                    )
            );
        }
        return expenseResponses;
    }



}
