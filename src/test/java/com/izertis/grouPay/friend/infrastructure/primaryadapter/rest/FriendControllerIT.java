package com.izertis.grouPay.friend.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.friend.domain.Friend;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
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
public class FriendControllerIT {

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
    void shouldGetAllFriends() {
        // All friends are found
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/friend")
                .then()
                .statusCode(200)
                .body(".", hasSize(3));
    }

    @Test
    @Order(2)
    void shouldGetFriendById() {
        // Friend is found
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .when()
                .get("/api/friend/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Juan"));
    }

    @Test
    @Order(3)
    void shouldCreateFriend() {
        Long friendId = 4L;
        Friend friend = new Friend(friendId, "Adrián");

        // Friend is created and status code 201 is returned
        given()
                .contentType(ContentType.JSON)
                .body(friend)
                .when()
                .post("/api/friend")
                .then()
                .statusCode(201);

        // Friend is found in the database
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/friend/" + friendId)
                .then()
                .statusCode(200)
                .body("id", equalTo(4))
                .body("name", equalTo("Adrián"));
    }

    @Test
    @Order(4)
    void shouldUpdateFriend() {
        Long friendId = 1L;

        // Friend is updated and 200 status code is returned
        given()
                .pathParam("id", friendId)
                .queryParam("name", "Eric")
                .when()
                .put("/api/friend/{id}")
                .then()
                .statusCode(200);

        // Updated friend is found in the database
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/friend/" + friendId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Eric"));
    }

    @Test
    @Order(5)
    void shouldDeleteFriendById() {
        Long friendId = 1L;

        // Friend is deleted
        given()
                .pathParam("id", friendId)
                .when()
                .delete("/api/friend/{id}")
                .then()
                .statusCode(200);

        // Friend is not found
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/friend/" + friendId)
                .then()
                .statusCode(404);
    }

    @Test
    @Order(6)
    void shouldDeleteAllFriends() {
        // All friends are deleted
        when()
                .delete("/api/friend")
                .then()
                .statusCode(200);

        // No friends are not found
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/friend")
                .then()
                .body(".", hasSize(0));
    }

    @Test
    @Order(7)
    void shouldFailToGetNonExistentFriend() {
        // Friend is not found
        given()
                .pathParam("id", 9)
                .when()
                .get("/api/friend/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(8)
    void shouldFailToUpdateNonExistentFriend() {
        // Friend is not found
        given()
                .pathParam("id", 9)
                .queryParam("name", "Eric")
                .when()
                .put("/api/friend/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(9)
    void shouldFailToDeleteNonExistentFriend() {
        // Friend is not found
        given()
                .pathParam("id", 9)
                .when()
                .delete("/api/friend/{id}")
                .then()
                .statusCode(404);
    }

}
