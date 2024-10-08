package com.izertis.grouPay.expense.application;

import com.izertis.grouPay.expense.application.exception.ExpenseNotFoundException;
import com.izertis.grouPay.expense.application.service.ExpenseService;
import com.izertis.grouPay.expense.domain.model.Expense;
import com.izertis.grouPay.expense.domain.repository.ExpenseRepository;
import com.izertis.grouPay.friend.application.exception.FriendNotFoundException;
import com.izertis.grouPay.friend.domain.model.Friend;
import com.izertis.grouPay.friend.domain.repository.FriendRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class ExpenseServiceTest {

    private final ExpenseRepository expenseRepository = Mockito.mock(ExpenseRepository.class);
    private final FriendRepository friendRepository = Mockito.mock(FriendRepository.class);

    private final ExpenseService sut = new ExpenseService(expenseRepository, friendRepository);

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
        Mockito.when(expenseRepository.existsById(expenseId)).thenReturn(true);

        Expense returnedExpense = sut.getExpense(expenseId);

        // Then
        Assertions.assertThat(returnedExpense).isEqualTo(expectedExpense);
    }

    @Test
    void shouldFailToGetNonExistingExpense() {
        // Given
        Long nonExistingExpenseId = -1L;

        Class<ExpenseNotFoundException> expectedException = ExpenseNotFoundException.class;

        // When
        Mockito.when(expenseRepository.existsById(nonExistingExpenseId)).thenReturn(false);

        // Then
        Assertions.assertThatThrownBy(() -> sut.getExpense(nonExistingExpenseId))
                .isInstanceOf(expectedException)
                .hasMessageContaining("Expense not found");
    }

    @Test
    void shouldCreateExpense() {
        // Given
        Long friendId = 1L;
        Expense expectedExpense = new Expense(1L, 1.1, "Description 1", new Friend(friendId, "Juan"));

        // When
        Mockito.when(friendRepository.existsById(friendId)).thenReturn(true);

        sut.createExpense(expectedExpense);

        // Then
        Mockito.verify(expenseRepository).save(expectedExpense);
    }

    @Test
    void shouldFailToCreateExpenseWithNegativeAmount() {
        // Given
        Expense invalidExpense = new Expense(1L, -1.0, "Description 1", new Friend(1L, "Juan"));

        Class<IllegalArgumentException> expectedException = IllegalArgumentException.class;

        // When & Then
        Assertions.assertThatThrownBy(() -> sut.createExpense(invalidExpense))
                .isInstanceOf(expectedException)
                .hasMessageContaining("Amount must be greater than 0");
    }

    @Test
    void shouldFailToCreateExpenseIfFriendIsNotFound() {
        // Given
        Long friendId = 1L;
        Expense expenseWithNonExistentFriend = new Expense(1L, 1.0, "Description 1", new Friend(friendId, "Juan"));

        Class<FriendNotFoundException> expectedException = FriendNotFoundException.class;

        // When
        Mockito.when(friendRepository.existsById(friendId)).thenReturn(false);

        // Then
        Assertions.assertThatThrownBy(() -> sut.createExpense(expenseWithNonExistentFriend))
                .isInstanceOf(expectedException)
                .hasMessageContaining("Friend not found");
    }

    @Test
    void shouldUpdateExpense() {
        // Given
        Expense expectedExpense = new Expense(1L, 40.0, "Updated Expense", null);

        // When
        sut.updateExpense(expectedExpense);

        // Then
        Mockito.verify(expenseRepository).update(expectedExpense);
    }

    @Test
    void shouldFailToUpdateExpenseWithNegativeAmount() {
        // Given
        Expense invalidExpense = new Expense(1L, -1.0, "Description 1", null);

        Class<IllegalArgumentException> expectedException = IllegalArgumentException.class;

        // When & Then
        Assertions.assertThatThrownBy(() -> sut.updateExpense(invalidExpense))
                .isInstanceOf(expectedException)
                .hasMessageContaining("Amount must be greater than 0");
    }

    @Test
    void shouldDeleteAllExpenses() {
        // When
        sut.deleteExpenses();

        // Then
        Mockito.verify(expenseRepository).deleteAll();
    }

    @Test
    void shouldDeleteExpenseById() {
        // Given
        Long expenseId = 1L;

        // When
        Mockito.when(expenseRepository.existsById(expenseId)).thenReturn(true);

        sut.deleteExpense(expenseId);

        // Then
        Mockito.verify(expenseRepository).deleteById(expenseId);
    }

    @Test
    void shouldFailToDeleteNonExistingExpense() {
        // Given
        Long nonExistingExpenseId = -1L;

        Class<ExpenseNotFoundException> expectedException = ExpenseNotFoundException.class;

        // When
        Mockito.when(expenseRepository.existsById(nonExistingExpenseId)).thenReturn(false);

        // Then
        Assertions.assertThatThrownBy(() -> sut.deleteExpense(nonExistingExpenseId))
                .isInstanceOf(expectedException)
                .hasMessageContaining("Expense not found");
    }

}
