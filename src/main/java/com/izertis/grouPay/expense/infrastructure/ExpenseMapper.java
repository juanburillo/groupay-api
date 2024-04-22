package com.izertis.grouPay.expense.infrastructure;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.ExpenseRequest;
import com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.ExpenseResponse;
import com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.ExpenseEntity;
import com.izertis.grouPay.friend.domain.Friend;

import java.util.ArrayList;
import java.util.List;

public class ExpenseMapper {

    public static Expense toModel(ExpenseRequest expenseRequest) {
        return new Expense(
                expenseRequest.getId(),
                expenseRequest.getAmount(),
                expenseRequest.getDescription(),
                expenseRequest.getDate(),
                new Friend(expenseRequest.getFriendId())
        );
    }

    public static Expense toModel(ExpenseEntity expenseEntity) {
        return new Expense(
                expenseEntity.getId(),
                expenseEntity.getAmount(),
                expenseEntity.getDescription(),
                expenseEntity.getDate(),
                new Friend(expenseEntity.getFriendId())
        );
    }

    public static List<Expense> toModelList(List<ExpenseEntity> expenseEntities) {
        List<Expense> expenseList = new ArrayList<>();
        for (ExpenseEntity expenseEntity : expenseEntities) {
            expenseList.add(toModel(expenseEntity));
        }
        return expenseList;
    }

    public static ExpenseResponse toDto(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getDate(),
                expense.getFriend()
        );
    }

    public static List<ExpenseResponse> toDtoList(List<Expense> expenses) {
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
