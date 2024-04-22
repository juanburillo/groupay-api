package com.izertis.grouPay.balance.domain;

import com.izertis.grouPay.friend.domain.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Balance {

    private Friend friend;
    private Double amount;

}
