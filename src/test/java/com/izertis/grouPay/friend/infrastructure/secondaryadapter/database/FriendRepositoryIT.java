package com.izertis.grouPay.friend.infrastructure.secondaryadapter.database;

import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.friend.domain.FriendRepository;
import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class FriendRepositoryIT {

    private final FriendRepository friendRepository;

    @Autowired
    public FriendRepositoryIT(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
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
    void shouldGetAllFriends() {
        // When
        List<Friend> returnedFriends = friendRepository.findAll();

        // Then
        Assertions.assertThat(returnedFriends).hasSize(3);
    }

    @Test
    @Order(2)
    void shouldGetFriendById() {
        // Given
        Long friendId = 1L;
        Friend expectedFriend = new Friend(friendId, "Juan");

        // When
        Friend returnedFriend = friendRepository.findById(friendId);

        // Then
        Assertions.assertThat(returnedFriend).isEqualTo(expectedFriend);
    }

    @Test
    @Order(3)
    void shouldCreateFriend() {
        // Given
        Long friendId = 4L;
        Friend friend = new Friend(friendId, "Adrián");

        // When
        friendRepository.save(friend);

        Friend returnedFriend = friendRepository.findById(friendId);

        // Then
        Assertions.assertThat(returnedFriend).isEqualTo(friend);
    }

    @Test
    @Order(4)
    void shouldUpdateFriend() {
        // Given
        Long friendId = 1L;
        String updatedName = "Eric";
        Friend expectedFriend = new Friend(friendId, updatedName);

        // When
        friendRepository.update(friendId, updatedName);

        Friend returnedFriend = friendRepository.findById(friendId);

        // Then
        Assertions.assertThat(returnedFriend).isEqualTo(expectedFriend);
    }

    @Test
    @Order(5)
    void shouldDeleteFriendById() {
        // Given
        Long friendId = 1L;

        // When
        friendRepository.deleteById(friendId);

        boolean friendExists = friendRepository.existsById(friendId);

        // Then
        Assertions.assertThat(friendExists).isFalse();
    }

    @Test
    @Order(6)
    void shouldDeleteAllFriends() {
        // When
        friendRepository.deleteAll();

        List<Friend> returnedFriends = friendRepository.findAll();

        // Then
        Assertions.assertThat(returnedFriends).isEmpty();
    }

}
