package com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.repository;

import com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    @Query(
            value = "SELECT name FROM friend JOIN expense ON friend.id = friend_id WHERE expense.id = :id",
            nativeQuery = true
    )
    String findExpenseFriendNameById(@Param("id") Long id);
}
