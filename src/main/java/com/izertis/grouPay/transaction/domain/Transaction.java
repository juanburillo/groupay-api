package com.izertis.grouPay.transaction.domain;

import com.izertis.grouPay.friend.domain.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {

    private Friend sender;
    private Friend recipient;
    private Double amount;

}
