package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.expense.application.ExpenseService;
import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.friend.domain.Friend;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class ExpenseControllerTest {

    private final ExpenseService expenseService = Mockito.mock(ExpenseService.class);

    private final ExpenseController sut = new ExpenseController(expenseService);

    @Test
    void shouldFindAllExpensesAndReturnExpenses() {
        // Given
        List<Expense> expenses = Arrays.asList(
                new Expense(1L, 1.1, "Description 1", new Friend(1L, "Juan")),
                new Expense(2L, 2.2, "Description 2", new Friend(2L, "María")),
                new Expense(3L, 3.3, "Description 3", new Friend(3L, "Belén"))
        );

        List<ExpenseResponse> expectedExpenseResponses = Arrays.asList(
                new ExpenseResponse(1L, 1.1, "Description 1", new Friend(1L, "Juan")),
                new ExpenseResponse(2L, 2.2, "Description 2", new Friend(2L, "María")),
                new ExpenseResponse(3L, 3.3, "Description 3", new Friend(3L, "Belén"))
        );

        // When
        Mockito.when(expenseService.getExpenses()).thenReturn(expenses);

        List<ExpenseResponse> returnedExpenseResponses = sut.getExpenses();

        // Then
        Assertions.assertThat(returnedExpenseResponses).isEqualTo(expectedExpenseResponses);
    }

    @Test
    void shouldFindExpenseByIdAndReturnExpense() {
        // Given
        Long expenseId = 1L;
        Expense expense = new Expense(expenseId, 1.1, "Description 1", new Friend(1L, "Juan"));
        ExpenseResponse expectedExpenseResponse = new ExpenseResponse(expenseId, 1.1, "Description 1", new Friend(1L, "Juan"));

        // When
        Mockito.when(expenseService.getExpense(expenseId)).thenReturn(expense);

        ExpenseResponse returnedExpenseResponse = sut.getExpense(expenseId);

        // Then
        Assertions.assertThat(returnedExpenseResponse).isEqualTo(expectedExpenseResponse);
    }

    @Test
    void shouldCreateExpense() {
        // Given
        ExpenseRequest expenseRequest = new ExpenseRequest(1L, 1.1, "Description 1", 1L);
        Expense expectedExpense = new Expense(1L, 1.1, "Description 1", new Friend(1L));

        // When
        sut.createExpense(expenseRequest);

        // Then
        Mockito.verify(expenseService).createExpense(expectedExpense);
    }

    @Test
    void shouldDeleteExpenseById() {
        // Given
        Long expenseId = 1L;

        // When
        sut.deleteExpense(expenseId);

        // Then
        Mockito.verify(expenseService).deleteExpense(expenseId);
    }

}
