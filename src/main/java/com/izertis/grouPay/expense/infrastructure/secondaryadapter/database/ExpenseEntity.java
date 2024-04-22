package com.izertis.grouPay.expense.infrastructure.secondaryadapter.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseEntity {

    private Long id;
    private Double amount;
    private String description;
    private Timestamp date;
    private Long friendId;

}
