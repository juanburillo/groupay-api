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
        // Define sample expense data
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

        // Mock expenseService to return sample data
        Mockito.when(expenseService.getExpenses()).thenReturn(expenses);

        // Call the controller method
        List<ExpenseResponse> returnedExpenseResponses = sut.getExpenses();

        // Verify that the response matches expectations
        Assertions.assertThat(returnedExpenseResponses).isEqualTo(expectedExpenseResponses);
    }

    @Test
    void shouldFindExpenseByIdAndReturnExpense() {
        // Define expense ID and sample expense data
        Long expenseId = 1L;
        Expense expense = new Expense(expenseId, 1.1, "Description 1", new Friend(1L, "Juan"));
        ExpenseResponse expectedExpenseResponse = new ExpenseResponse(expenseId, 1.1, "Description 1", new Friend(1L, "Juan"));

        // Mock expenseService to return expense for the ID
        Mockito.when(expenseService.getExpense(expenseId)).thenReturn(expense);

        // Call the controller method
        ExpenseResponse returnedExpenseResponse = sut.getExpense(expenseId);

        // Verify that the response matches expectations
        Assertions.assertThat(returnedExpenseResponse).isEqualTo(expectedExpenseResponse);
    }

    @Test
    void shouldCreateExpense() {
        // Define expense data to be created
        ExpenseRequest expenseRequest = new ExpenseRequest(1L, 1.1, "Description 1", 1L);
        Expense expectedExpense = new Expense(1L, 1.1, "Description 1", new Friend(1L));

        // Mock expenseService to handle creating expense
        Mockito.doNothing().when(expenseService).createExpense(expectedExpense);

        // Call the controller method
        sut.createExpense(expenseRequest);

        // Verify Mockito captures the createExpense call with the correct data
        Mockito.verify(expenseService).createExpense(expectedExpense);
    }

    @Test
    void shouldDeleteExpenseById() {
        // Define expense ID to be deleted
        Long expenseId = 1L;

        // Mock expenseService to handle deletion
        Mockito.doNothing().when(expenseService).deleteExpense(expenseId);

        // Call the controller method
        sut.deleteExpense(expenseId);

        // Verify Mockito captures de deletedExpense call with the correct data
        Mockito.verify(expenseService).deleteExpense(expenseId);
    }

}
