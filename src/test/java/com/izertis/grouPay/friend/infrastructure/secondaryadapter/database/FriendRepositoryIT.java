package com.izertis.grouPay.friend.infrastructure.secondaryadapter.database;

import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.friend.domain.FriendRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FriendRepositoryIT {

    private final FriendRepository friendRepository;

    @Autowired
    public FriendRepositoryIT(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Test
    @Order(1)
    void shouldSaveFriendInDatabase() {
        Long friendId = 1L;
        Friend expectedFriend = new Friend(friendId, "Juan");

        friendRepository.save(expectedFriend);

        Assertions.assertThat(friendRepository.findById(friendId)).isEqualTo(expectedFriend);
    }

    @Test
    @Order(2)
    void shouldGetAllFriendsFromDatabase() {
        Assertions.assertThat(friendRepository.findAll()).hasSizeGreaterThan(0);
    }

    @Test
    @Order(3)
    void shouldGetFriendByIdFromDatabase() {
        Long friendId = 1L;
        Friend expectedFriend = new Friend(friendId, "Juan");

        Assertions.assertThat(friendRepository.findById(friendId)).isEqualTo(expectedFriend);
    }

    @Test
    @Order(4)
    void shouldUpdateFriendInDatabase() {
        Long friendId = 1L;
        String nameToUpdate = "Updated Name";

        Friend expectedFriend = new Friend(friendId, nameToUpdate);

        friendRepository.update(friendId, nameToUpdate);

        Assertions.assertThat(friendRepository.findById(friendId)).isEqualTo(expectedFriend);
    }

    @Test
    @Order(5)
    void shouldDeleteFriendFromDatabase() {
        Long friendId = 1L;

        friendRepository.deleteById(friendId);

        Assertions.assertThat(friendRepository.existsById(friendId)).isEqualTo(false);
    }

}
