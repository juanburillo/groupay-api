package com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest;

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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIT {

    private final FriendService friendService;
    private final ExpenseService expenseService;

    @Autowired
    public TransactionControllerIT(FriendService friendService, ExpenseService expenseService) {
        this.friendService = friendService;
        this.expenseService = expenseService;
    }

    @LocalServerPort
    private Integer port;

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
    void shouldGetAllTransactions() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/transaction")
                .then()
                .statusCode(200)
                .body(".", hasSize(1));
    }

}
