package com.izertis.grouPay.balance.application;

import com.izertis.grouPay.balance.domain.Balance;
import com.izertis.grouPay.expense.application.ExpenseService;
import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.friend.application.FriendService;
import com.izertis.grouPay.friend.domain.Friend;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class BalanceServiceTest {

    private final FriendService friendService = Mockito.mock(FriendService.class);
    private final ExpenseService expenseService = Mockito.mock(ExpenseService.class);

    private final BalanceService sut = new BalanceService(friendService, expenseService);

    @Test
    void shouldCalculateAllBalancesAndReturnBalances() {
        // Given
        List<Friend> friends = Arrays.asList(
                new Friend(1L, "Juan"),
                new Friend(2L, "María"),
                new Friend(3L, "Belén")
        );

        List<Expense> expenses = Arrays.asList(
                new Expense(1L, 1.1, "Description 1", friends.get(0)),
                new Expense(2L, 2.2, "Description 2", friends.get(1)),
                new Expense(3L, 3.3, "Description 3", friends.get(2))
        );

        List<Balance> expectedBalances = Arrays.asList(
                new Balance(friends.get(0), -1.1),
                new Balance(friends.get(1), 0.0),
                new Balance(friends.get(2), 1.1)
        );

        // When
        Mockito.when(friendService.getFriends()).thenReturn(friends);
        Mockito.when(friendService.getFriend(1L)).thenReturn(friends.get(0));
        Mockito.when(friendService.getFriend(2L)).thenReturn(friends.get(1));
        Mockito.when(friendService.getFriend(3L)).thenReturn(friends.get(2));
        Mockito.when(expenseService.getExpenses()).thenReturn(expenses);

        List<Balance> returnedBalances = sut.getBalances();

        // Then
        Assertions.assertThat(returnedBalances).isEqualTo(expectedBalances);
    }

    @Test
    void shouldCalculateBalanceByFriendIdAndReturnBalance() {
        // Given
        Long friendId = 1L;
        List<Friend> friends = Arrays.asList(
                new Friend(1L, "Juan"),
                new Friend(2L, "María"),
                new Friend(3L, "Belén")
        );

        List<Expense> expenses = Arrays.asList(
                new Expense(1L, 1.1, "Description 1", friends.get(0)),
                new Expense(2L, 2.2, "Description 2", friends.get(1)),
                new Expense(3L, 3.3, "Description 3", friends.get(2))
        );

        Balance expectedBalance = new Balance(friends.get(0), -1.1);

        // When
        Mockito.when(friendService.getFriends()).thenReturn(friends);
        Mockito.when(friendService.getFriend(friendId)).thenReturn(friends.get(0));
        Mockito.when(expenseService.getExpenses()).thenReturn(expenses);

        Balance returnedBalance = sut.getBalance(friendId);

        // Then
        Assertions.assertThat(returnedBalance).isEqualTo(expectedBalance);
    }

}
