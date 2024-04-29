package com.izertis.grouPay.transaction.application;

import com.izertis.grouPay.balance.application.BalanceService;
import com.izertis.grouPay.balance.domain.Balance;
import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.transaction.domain.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class TransactionServiceTest {

    private final BalanceService balanceService = Mockito.mock(BalanceService.class);

    private final TransactionService sut = new TransactionService(balanceService);

    @Test
    void shouldGetAllTransactionsAndReturnThem() {
        // Given
        List<Friend> friends = Arrays.asList(
                new Friend(1L, "Juan"),
                new Friend(2L, "María"),
                new Friend(3L, "Belén"),
                new Friend(4L, "Adrián")
        );

        List<Balance> balances = Arrays.asList(
                new Balance(friends.get(0), 59.15),
                new Balance(friends.get(1), 22.55),
                new Balance(friends.get(2), -40.85),
                new Balance(friends.get(3), -40.85)
        );

        List<Transaction> expectedTransactions = Arrays.asList(
                new Transaction(friends.get(2), friends.get(0), 40.85),
                new Transaction(friends.get(3), friends.get(0), 18.3),
                new Transaction(friends.get(3), friends.get(1), 22.55)
        );

        // When
        Mockito.when(balanceService.getBalances()).thenReturn(balances);

        List<Transaction> returnedTransactions = sut.getTransactions();

        // Then
        Assertions.assertThat(returnedTransactions).isEqualTo(expectedTransactions);
    }

}
