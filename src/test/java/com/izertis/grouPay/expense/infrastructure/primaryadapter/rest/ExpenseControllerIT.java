package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.expense.application.ExpenseService;
import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.friend.application.FriendService;
import com.izertis.grouPay.friend.domain.Friend;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Timestamp;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExpenseControllerIT {

    private final ExpenseService expenseService;
    private final FriendService friendService;

    @Autowired
    public ExpenseControllerIT(ExpenseService expenseService, FriendService friendService) {
        this.expenseService = expenseService;
        this.friendService = friendService;
    }

    @LocalServerPort
    private Integer port;

    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");

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
        RestAssured.baseURI = "http://localhost:" + port;
        friendService.deleteFriends();
        friendService.createFriend(new Friend(1L, "Juan"));
        friendService.createFriend(new Friend(2L, "María"));
        friendService.createFriend(new Friend(3L, "Belén"));
        expenseService.deleteExpenses();
        expenseService.createExpense(new Expense(1L, 10.0, "Description 1", new Friend(1L, "Juan")));
        expenseService.createExpense(new Expense(2L, 20.0, "Description 2", new Friend(2L, "María")));
        expenseService.createExpense(new Expense(3L, 30.0, "Description 3", new Friend(3L, "Belén")));
    }

    @Test
    void shouldGetAllExpenses() {
        // All expenses are found
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/expense")
                .then()
                .statusCode(200)
                .body(".", hasSize(3));
    }

    @Test
    void shouldGetExpenseById() {
        // Expense is found
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .when()
                .get("/api/expense/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("amount", equalTo(10.0F));
    }

    @Test
    void shouldCreateExpense() {
        Expense expense = new Expense(4L, 40.0, "Description 4", new Friend(1L, "Juan"));

        // Expense is created
        given()
                .contentType(ContentType.JSON)
                .body(expense)
                .when()
                .post("/api/expense")
                .then()
                .statusCode(201);
    }

    @Test
    void shouldDeleteAllExpenses() {
        // All expenses are deleted
        when()
                .delete("/api/expense")
                .then()
                .statusCode(200);

        // No expenses are found
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/expense")
                .then()
                .body(".", hasSize(0));
    }

    @Test
    void shouldDeleteExpenseById() {
        Long expenseId = 1L;

        // Expense is deleted
        given()
                .pathParam("id", expenseId)
                .when()
                .delete("/api/expense/{id}")
                .then()
                .statusCode(200);

        // Expense is not found
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", expenseId)
                .when()
                .get("/api/expense/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldFailToGetNonExistentExpense() {
        Long expenseId = 9L;

        given()
                .pathParam("id", expenseId)
                .when()
                .get("/api/expense/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldFailToDeleteNonExistentExpense() {
        Long expenseId = 9L;

        given()
                .pathParam("id", expenseId)
                .when()
                .delete("/api/expense/{id}")
                .then()
                .statusCode(404);
    }

}
