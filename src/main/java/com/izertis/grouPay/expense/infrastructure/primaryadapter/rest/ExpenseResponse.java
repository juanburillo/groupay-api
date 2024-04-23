package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.izertis.grouPay.friend.domain.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {

    private Long id;
    private Double amount;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S", timezone = "Europe/Madrid")
    private Timestamp date;
    private Friend friend;

    public ExpenseResponse(Long id, Double amount, String description, Friend friend) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.friend = friend;
    }

}
