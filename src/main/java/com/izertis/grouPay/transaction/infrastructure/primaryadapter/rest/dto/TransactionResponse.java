package com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest.dto;

import com.izertis.grouPay.friend.domain.model.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {

    private Friend sender;
    private Friend recipient;
    private Double amount;

}
