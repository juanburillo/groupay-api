package com.izertis.grouPay.balance.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.balance.domain.Balance;

import java.util.ArrayList;
import java.util.List;

public class BalanceMapper {

    public static List<BalanceResponse> toBalanceResponses(List<Balance> balances) {
        List<BalanceResponse> balanceResponses = new ArrayList<>();
        for (Balance balance : balances) {
            balanceResponses.add(
                    new BalanceResponse(
                            balance.getFriend(),
                            balance.getAmount()
                    )
            );
        }
        return balanceResponses;
    }

    public static BalanceResponse toBalanceResponse(Balance balance) {
        return new BalanceResponse(
                balance.getFriend(),
                balance.getAmount()
        );
    }

}
