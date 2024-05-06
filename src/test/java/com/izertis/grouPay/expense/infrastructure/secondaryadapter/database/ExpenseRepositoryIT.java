package com.izertis.grouPay.expense.infrastructure.secondaryadapter.database;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.domain.ExpenseRepository;
import com.izertis.grouPay.friend.domain.Friend;
import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseRepositoryIT {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseRepositoryIT(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeAll
    static void beforeAll(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Order(1)
    void shouldGetAllExpenses() {
        // When
        List<Expense> returnedExpenses = expenseRepository.findAll();

        // Then
        Assertions.assertThat(returnedExpenses).hasSize(3);
    }

    @Test
    @Order(2)
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
    @Order(3)
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
    @Order(4)
    void shouldDeleteExpenseById() {
        // Given
        Long expenseId = 1L;

        // When
        expenseRepository.deleteById(expenseId);

        boolean expenseExists = expenseRepository.existsById(expenseId);

        // Then
        Assertions.assertThat(expenseExists).isFalse();
    }

    @Test
    @Order(5)
    void shouldDeleteAllExpenses() {
        // When
        expenseRepository.deleteAll();

        List<Expense> returnedExpenses = expenseRepository.findAll();

        // Then
        Assertions.assertThat(returnedExpenses).isEmpty();
    }

}
