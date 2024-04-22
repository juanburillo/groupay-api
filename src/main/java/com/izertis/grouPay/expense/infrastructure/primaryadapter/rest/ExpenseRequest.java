package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode
public class ExpenseRequest {

    private Long id;
    private Double amount;
    private String description;
    private Timestamp date;
    private Long friendId;

    public ExpenseRequest(Long id, Double amount, String description, Long friendId) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.friendId = friendId;
    }

}
