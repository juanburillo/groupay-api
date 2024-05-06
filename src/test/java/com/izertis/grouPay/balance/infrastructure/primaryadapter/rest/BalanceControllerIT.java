package com.izertis.grouPay.balance.infrastructure.primaryadapter.rest;

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
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BalanceControllerIT {

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
    void shouldGetBalances() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/balance")
                .then()
                .statusCode(200)
                .body(".", hasSize(3));
    }

    @Test
    @Order(2)
    void shouldGetBalanceByFriendId() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .when()
                .get("/api/expense/{id}")
                .then()
                .statusCode(200);
    }

}
