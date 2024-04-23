package com.izertis.grouPay.balance.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.balance.application.BalanceService;
import com.izertis.grouPay.balance.domain.Balance;
import com.izertis.grouPay.friend.domain.Friend;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class BalanceControllerTest {

    private final BalanceService balanceService = Mockito.mock(BalanceService.class);

    private final BalanceController sut = new BalanceController(balanceService);

    @Test
    void shouldGetBalancesAndReturnBalances() {
        // Given
        List<Balance> balances = Arrays.asList(
                new Balance(new Friend(1L, "Juan"), 10.0),
                new Balance(new Friend(2L, "María"), -20.0),
                new Balance(new Friend(3L, "Belén"), 30.0)
        );

        List<BalanceResponse> expectedBalanceResponses = Arrays.asList(
                new BalanceResponse(new Friend(1L, "Juan"), 10.0),
                new BalanceResponse(new Friend(2L, "María"), -20.0),
                new BalanceResponse(new Friend(3L, "Belén"), 30.0)
        );

        // When
        Mockito.when(balanceService.getBalances()).thenReturn(balances);

        List<BalanceResponse> returnedBalances = sut.getBalances();

        // Then
        Assertions.assertThat(returnedBalances).isEqualTo(expectedBalanceResponses);
    }

    @Test
    void shouldGetBalanceByFriendIdAndReturnBalance() {
        Long friendId = 1L;
        Balance balance = new Balance(new Friend(friendId, "Juan"), 10.0);
        BalanceResponse expectedBalanceResponse = new BalanceResponse(new Friend(friendId, "Juan"), 10.0);

        Mockito.when(balanceService.getBalance(friendId)).thenReturn(balance);

        BalanceResponse returnedBalanceResponse = sut.getBalance(friendId);

        Assertions.assertThat(returnedBalanceResponse).isEqualTo(expectedBalanceResponse);
    }

}
