package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.dto.CreateExpenseRequest;
import com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.dto.UpdateExpenseRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ExpenseControllerIT {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeAll
    static void beforeAll(@Autowired Flyway flyway, @LocalServerPort Integer port) {
        RestAssured.baseURI = "http://localhost:" + port;
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Order(1)
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
    @Order(2)
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
    @Order(3)
    void shouldCreateExpense() {
        CreateExpenseRequest createExpenseRequest = new CreateExpenseRequest(4L, 40.0, "Description 4", 1L);

        // Expense is created
        given()
                .contentType(ContentType.JSON)
                .body(createExpenseRequest)
                .when()
                .post("/api/expense")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(4)
    void shouldUpdateExpense() {
        Long expenseId = 1L;
        UpdateExpenseRequest updateExpenseRequest = new UpdateExpenseRequest(expenseId, 50.0, "Updated Description");

        // Expense is updated and 200 status code is returned
        given()
                .contentType(ContentType.JSON)
                .body(updateExpenseRequest)
                .when()
                .put("/api/expense")
                .then()
                .statusCode(200);

        // Updated expense is found in the database
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/expense/" + expenseId)
                .then()
                .statusCode(200)
                .body("description", equalTo("Updated Description"));
    }

    @Test
    @Order(5)
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
    @Order(6)
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
    @Order(7)
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
    @Order(8)
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
