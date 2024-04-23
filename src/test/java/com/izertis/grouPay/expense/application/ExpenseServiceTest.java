package com.izertis.grouPay.expense.application;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.domain.ExpenseRepository;
import com.izertis.grouPay.friend.application.FriendService;
import com.izertis.grouPay.friend.domain.Friend;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class ExpenseServiceTest {

    private final ExpenseRepository expenseRepository = Mockito.mock(ExpenseRepository.class);
    private final FriendService friendService = Mockito.mock(FriendService.class);

    private final ExpenseService sut = new ExpenseService(expenseRepository, friendService);

    @Test
    void shouldFindAllExpensesAndReturnExpenses() {
        // Given
        List<Expense> expectedExpenses = Arrays.asList(
                new Expense(1L, 1.1, "Description 1", new Friend(1L, "Juan")),
                new Expense(2L, 2.2, "Description 2", new Friend(2L, "María")),
                new Expense(3L, 3.3, "Description 3", new Friend(3L, "Belén"))
        );

        // When
        Mockito.when(expenseRepository.findAll()).thenReturn(expectedExpenses);

        List<Expense> returnedExpenses = sut.getExpenses();

        // Then
        Assertions.assertThat(returnedExpenses).isEqualTo(expectedExpenses);
    }

    @Test
    void shouldFindExpenseByIdAndReturnExpense() {
        // Given
        Long expenseId = 1L;
        Expense expectedExpense = new Expense(1L, 1.1, "Description 1", new Friend(1L, "Juan"));

        // When
        Mockito.when(expenseRepository.findById(expenseId)).thenReturn(expectedExpense);

        Expense returnedExpense = sut.getExpense(expenseId);

        // Then
        Assertions.assertThat(returnedExpense).isEqualTo(expectedExpense);
    }

    @Test
    void shouldCreateExpense() {
        // Given
        Expense expectedExpense = new Expense(1L, 1.1, "Description 1", new Friend(1L, "Juan"));

        // When
        sut.createExpense(expectedExpense);

        // Then
        Mockito.verify(expenseRepository).save(expectedExpense);
    }

    @Test
    void shouldDeleteExpenseById() {
        // Given
        Long expenseId = 1L;

        // When
        sut.deleteExpense(expenseId);

        // Then
        Mockito.verify(expenseRepository).deleteById(expenseId);
    }

}
