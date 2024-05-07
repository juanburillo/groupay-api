package com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.friend.domain.model.Friend;
import com.izertis.grouPay.transaction.application.service.TransactionService;
import com.izertis.grouPay.transaction.domain.Transaction;
import com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest.controller.TransactionController;
import com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest.dto.TransactionResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class TransactionControllerTest {

    private final TransactionService transactionService = Mockito.mock(TransactionService.class);

    private final TransactionController sut = new TransactionController(transactionService);

    @Test
    void shouldGetAllTransactionsAndReturnAllTransactionResponses() {
        // Given
        List<Friend> friends = Arrays.asList(
                new Friend(1L, "Juan"),
                new Friend(2L, "María"),
                new Friend(3L, "Belén")
        );

        List<Transaction> transactions = Arrays.asList(
                new Transaction(friends.get(0), friends.get(1), 10.0),
                new Transaction(friends.get(1), friends.get(2), 20.0),
                new Transaction(friends.get(2), friends.get(0), 30.0)
        );

        List<TransactionResponse> expectedTransactionResponses = Arrays.asList(
                new TransactionResponse(friends.get(0), friends.get(1), 10.0),
                new TransactionResponse(friends.get(1), friends.get(2), 20.0),
                new TransactionResponse(friends.get(2), friends.get(0), 30.0)
        );

        // When
        Mockito.when(transactionService.getTransactions()).thenReturn(transactions);

        List<TransactionResponse> returnedTransactionResponses = sut.getTransactions();

        // Then
        Assertions.assertThat(returnedTransactionResponses).isEqualTo(expectedTransactionResponses);
    }

}
