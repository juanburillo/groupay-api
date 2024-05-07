package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class UpdateExpenseRequest {

    private Long id;
    private Double amount;
    private String description;

}
