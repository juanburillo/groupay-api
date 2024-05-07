package com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense")
public class ExpenseEntity {

    @Id
    private Long id;
    private Double amount;
    private String description;
    private Timestamp date;
    private Long friendId;

}
