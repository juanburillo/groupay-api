package com.izertis.grouPay.balance.infrastructure.primaryadapter.rest.dto;

import com.izertis.grouPay.friend.domain.model.Friend;
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
