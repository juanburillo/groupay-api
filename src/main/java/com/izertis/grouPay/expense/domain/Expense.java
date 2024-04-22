package com.izertis.grouPay.expense.domain;

import com.izertis.grouPay.friend.domain.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Expense {

    private Long id;
    private Double amount;
    private String description;
    private Timestamp date;
    private Friend friend;

    public Expense(Long id, Double amount, String description, Friend friend) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.friend = friend;
    }

}
