package com.izertis.grouPay.expense.infrastructure.secondaryadapter.database;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.domain.ExpenseRepository;
import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.friend.domain.FriendRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

@SpringBootTest
public class ExpenseRepositoryIT {

    private final ExpenseRepository expenseRepository;
    private final FriendRepository friendRepository;

    @Autowired
    public ExpenseRepositoryIT(ExpenseRepository expenseRepository, FriendRepository friendRepository) {
        this.expenseRepository = expenseRepository;
        this.friendRepository = friendRepository;
    }

    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @BeforeAll
    static void beforeAll() {
        mysql.start();
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeEach
    void setUp() {
        friendRepository.deleteAll();
        friendRepository.save(new Friend(1L, "Juan"));
        friendRepository.save(new Friend(2L, "María"));
        friendRepository.save(new Friend(3L, "Belén"));
        expenseRepository.deleteAll();
        expenseRepository.save(new Expense(1L, 10.0, "Description 1", new Friend(1L, "Juan")));
        expenseRepository.save(new Expense(2L, 20.0, "Description 2", new Friend(2L, "María")));
        expenseRepository.save(new Expense(3L, 30.0, "Description 3", new Friend(3L, "Belén")));
    }

    @Test
    void shouldGetAllExpenses() {
        // When
        List<Expense> returnedExpenses = expenseRepository.findAll();

        // Then
        Assertions.assertThat(returnedExpenses).hasSize(3);
    }

    @Test
    void shouldGetExpenseById() {
        // Given
        Long expenseId = 1L;
        Expense expectedExpense = new Expense(expenseId, 10.0, "Description 1", new Friend(1L, "Juan"));

        // When
        Expense returnedExpense = expenseRepository.findById(expenseId);

        // Then
        Assertions.assertThat(returnedExpense).isEqualTo(expectedExpense);
    }

    @Test
    void shouldCreateExpense() {
        // Given
        Long expenseId = 4L;
        Expense expense = new Expense(expenseId, 40.0, "Description 4", new Friend(1L, "Juan"));

        // When
        expenseRepository.save(expense);

        Expense returnedExpense = expenseRepository.findById(expenseId);

        // Then
        Assertions.assertThat(returnedExpense).isEqualTo(expense);
    }

    @Test
    void shouldDeleteAllExpenses() {
        // When
        expenseRepository.deleteAll();

        List<Expense> returnedExpenses = expenseRepository.findAll();

        // Then
        Assertions.assertThat(returnedExpenses).isEmpty();
    }

    @Test
    void shouldDeleteExpenseById() {
        // Given
        Long expenseId = 1L;

        // When
        expenseRepository.deleteById(expenseId);

        boolean expenseExists = expenseRepository.existsById(expenseId);

        // Then
        Assertions.assertThat(expenseExists).isFalse();
    }

}
