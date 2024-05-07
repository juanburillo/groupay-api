package com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.repository;

import com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
}
