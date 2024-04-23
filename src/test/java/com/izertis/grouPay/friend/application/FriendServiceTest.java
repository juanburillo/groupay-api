package com.izertis.grouPay.friend.application;

import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.friend.domain.FriendRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class FriendServiceTest {

    private final FriendRepository friendRepository = Mockito.mock(FriendRepository.class);

    private final FriendService sut = new FriendService(friendRepository);

    @Test
    void shouldFindAllFriendsAndReturnFriends() {
        // Define sample friend data
        List<Friend> expectedFriends = Arrays.asList(
                new Friend(1L, "Juan"),
                new Friend(2L, "María"),
                new Friend(3L, "Belén")
        );

        // Mock friendRepository to return sample data
        Mockito.when(friendRepository.findAll()).thenReturn(expectedFriends);

        // Call the service method
        List<Friend> returnedFriends = sut.getFriends();

        // Verify that the response matches expectations
        Assertions.assertThat(returnedFriends).isEqualTo(expectedFriends);
    }

    @Test
    void shouldFindFriendByIdAndReturnFriend() {
        // Define friend ID and sample friend data
        Long friendId = 1L;
        Friend expectedFriend = new Friend(friendId, "Juan");

        // Mock friendRepository to return friend for the ID
        Mockito.when(friendRepository.findById(friendId)).thenReturn(expectedFriend);

        // Call the service method
        Friend returnedFriend = sut.getFriend(friendId);

        // Verify that the response matches expectations
        Assertions.assertThat(returnedFriend).isEqualTo(expectedFriend);
    }

    @Test
    void shouldCreateFriend() {
        // Define friend data to be created
        Friend expectedFriend = new Friend(1L, "Juan");

        // Call the service method
        sut.createFriend(expectedFriend);

        // Verify Mockito captures the save call with the correct data
        Mockito.verify(friendRepository).save(expectedFriend);
    }

    @Test
    void shouldUpdateFriend() {
        // Define friend data for update
        Long friendId = 1L;
        String nameToUpdate = "Juan (Updated)";

        // Call the service method
        sut.updateFriend(friendId, nameToUpdate);

        // Verify Mockito captures the update call with the correct data
        Mockito.verify(friendRepository).update(friendId, nameToUpdate);
    }

    @Test
    void shouldDeleteFriendById() {
        // Define friend ID to be deleted
        Long friendId = 1L;

        // Call the controller method
        sut.deleteFriend(friendId);

        // Verify Mockito captures the deleteById call with the correct data
        Mockito.verify(friendRepository).deleteById(friendId);
    }

}
