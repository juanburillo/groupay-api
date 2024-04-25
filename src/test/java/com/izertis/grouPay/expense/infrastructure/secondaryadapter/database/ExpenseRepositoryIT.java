package com.izertis.grouPay.expense.infrastructure.secondaryadapter.database;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.domain.ExpenseRepository;
import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.friend.domain.FriendRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExpenseRepositoryIT {

    private final ExpenseRepository expenseRepository;
    private final FriendRepository friendRepository;

    @Autowired
    public ExpenseRepositoryIT(ExpenseRepository expenseRepository, FriendRepository friendRepository) {
        this.expenseRepository = expenseRepository;
        this.friendRepository = friendRepository;
    }

    @BeforeAll
    public void setup() {
        if (friendRepository.existsById(1L)) {
            friendRepository.deleteById(1L);
        }
        friendRepository.save(new Friend(1L, "Juan"));
    }

    @Test
    @Order(1)
    void shouldSaveExpenseInDatabase() {
        Long expenseId = 1L;
        Friend friend = friendRepository.findById(1L);
        Expense expectedExpense = new Expense(expenseId, 1.0, "Description", friend);

        expenseRepository.save(expectedExpense);

        Expense expense = expenseRepository.findById(expenseId);
        expense.setFriend(friendRepository.findById(expenseId));

        Assertions.assertThat(expense).isEqualTo(expectedExpense);
    }

    @Test
    @Order(2)
    void shouldGetAllExpensesFromDatabase() {
        Assertions.assertThat(expenseRepository.findAll()).hasSizeGreaterThan(0);
    }

    @Test
    @Order(3)
    void shouldGetExpenseByIdFromDatabase() {
        Long expenseId = 1L;
        Expense expectedExpense = new Expense(expenseId, 1.0, "Description", new Friend(1L, "Juan"));

        Expense expense = expenseRepository.findById(expenseId);
        expense.setFriend(friendRepository.findById(expenseId));

        Assertions.assertThat(expense).isEqualTo(expectedExpense);
    }

    @Test
    @Order(4)
    void shouldDeleteExpenseFromDatabase() {
        Long expenseId = 1L;

        expenseRepository.deleteById(expenseId);

        Assertions.assertThat(expenseRepository.existsById(expenseId)).isEqualTo(false);
    }

    @AfterAll
    public void done() {
        friendRepository.deleteById(1L);
    }

}
