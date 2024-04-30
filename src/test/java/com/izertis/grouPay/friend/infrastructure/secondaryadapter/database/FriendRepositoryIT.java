package com.izertis.grouPay.friend.infrastructure.secondaryadapter.database;

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
public class FriendRepositoryIT {

    private final FriendRepository friendRepository;

    @Autowired
    public FriendRepositoryIT(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

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
        friendRepository.deleteAll();
        friendRepository.save(new Friend(1L, "Juan"));
        friendRepository.save(new Friend(2L, "María"));
        friendRepository.save(new Friend(3L, "Belén"));
    }

    @Test
    void shouldGetAllFriends() {
        // When
        List<Friend> returnedFriends = friendRepository.findAll();

        // Then
        Assertions.assertThat(returnedFriends).hasSize(3);
    }

    @Test
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
    void shouldDeleteAllFriends() {
        // When
        friendRepository.deleteAll();

        List<Friend> returnedFriends = friendRepository.findAll();

        // Then
        Assertions.assertThat(returnedFriends).isEmpty();
    }

    @Test
    void shouldDeleteFriendById() {
        // Given
        Long friendId = 1L;

        // When
        friendRepository.deleteById(friendId);

        boolean friendExists = friendRepository.existsById(friendId);

        // Then
        Assertions.assertThat(friendExists).isFalse();
    }

}
