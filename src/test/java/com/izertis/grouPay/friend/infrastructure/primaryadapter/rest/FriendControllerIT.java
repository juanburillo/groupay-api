package com.izertis.grouPay.friend.infrastructure.primaryadapter.rest;

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
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FriendControllerIT {

    private final FriendService friendService;

    @Autowired
    public FriendControllerIT(FriendService friendService) {
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
    }

    @Test
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
    void shouldGetFriendById() {
        // Friend is found
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/friend/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Juan"));
    }

    @Test
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

}
