package com.izertis.grouPay.balance.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.friend.domain.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class BalanceResponse {

    private Friend friend;
    private Double amount;

}
