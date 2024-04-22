package com.izertis.grouPay.expense.application;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.domain.ExpenseRepository;
import com.izertis.grouPay.friend.application.FriendService;
import com.izertis.grouPay.friend.domain.Friend;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ExpenseServiceTest {

    private final ExpenseRepository expenseRepository = Mockito.mock(ExpenseRepository.class);
    private final FriendService friendService = Mockito.mock(FriendService.class);

    private final ExpenseService sut = new ExpenseService(expenseRepository, friendService);

    @Test
    void shouldFindAllExpensesAndReturnExpenses() {
        // Define sample friend data
        List<Expense> expectedExpenses = Arrays.asList(
                new Expense(1L, 1.1, "Description 1", new Friend(1L, "Juan")),
                new Expense(2L, 2.2, "Description 2", new Friend(2L, "María")),
                new Expense(3L, 3.3, "Description 3", new Friend(3L, "Belén"))
        );

        // Mock expenseRepository to return sample data
        Mockito.when(expenseRepository.findAll()).thenReturn(expectedExpenses);

        // Call the service method
        List<Expense> returnedExpenses = sut.getExpenses();

        // Verify that the response matches expectations
        Assertions.assertThat(returnedExpenses).isEqualTo(expectedExpenses);
    }

    @Test
    void shouldFindExpenseByIdAndReturnExpense() {
        // Define expense ID and sample expense data
        Long expenseId = 1L;
        Expense expectedExpense = new Expense(1L, 1.1, "Description 1", new Friend(1L, "Juan"));

        // Mock expenseService to return expense for the ID
        Mockito.when(expenseRepository.findById(expenseId)).thenReturn(expectedExpense);

        // Call the service method
        Expense returnedExpense = sut.getExpense(expenseId);

        // Verify that the response matches expectations
        Assertions.assertThat(returnedExpense).isEqualTo(expectedExpense);
    }

    @Test
    void shouldCreateExpense() {
        // Define expense data to be created
        Expense expectedExpense = new Expense(1L, 1.1, "Description 1", Timestamp.valueOf(LocalDate.now().atStartOfDay()), new Friend(1L, "Juan"));

        // Call the service method
        sut.createExpense(expectedExpense);

        // Verify Mockito captures the save call with the correct data
        Mockito.verify(expenseRepository).save(expectedExpense);
    }

    @Test
    void shouldDeleteExpenseById() {
        // Define expense ID to be deleted
        Long expenseId = 1L;

        // Call the controller method
        sut.deleteExpense(expenseId);

        // Verify Mockito captures the deleteById call with the correct data
        Mockito.verify(expenseRepository).deleteById(expenseId);
    }

}
