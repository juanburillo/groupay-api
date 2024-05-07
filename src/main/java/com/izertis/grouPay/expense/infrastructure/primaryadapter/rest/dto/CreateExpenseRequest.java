package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseRequest {

    private Long id;
    private Double amount;
    private String description;
    private Timestamp date;
    private Long friendId;

    public CreateExpenseRequest(Long id, Double amount, String description, Long friendId) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.friendId = friendId;
    }

}
