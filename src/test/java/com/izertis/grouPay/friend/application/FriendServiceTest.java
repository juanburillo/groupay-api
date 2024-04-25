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
        // Given
        List<Friend> expectedFriends = Arrays.asList(
                new Friend(1L, "Juan"),
                new Friend(2L, "María"),
                new Friend(3L, "Belén")
        );

        // When
        Mockito.when(friendRepository.findAll()).thenReturn(expectedFriends);

        List<Friend> returnedFriends = sut.getFriends();

        // Then
        Assertions.assertThat(returnedFriends).isEqualTo(expectedFriends);
    }

    @Test
    void shouldFindFriendByIdAndReturnFriend() {
        // Given
        Long friendId = 1L;
        Friend expectedFriend = new Friend(friendId, "Juan");

        // When
        Mockito.when(friendRepository.findById(friendId)).thenReturn(expectedFriend);
        Mockito.when(friendRepository.existsById(friendId)).thenReturn(true);

        Friend returnedFriend = sut.getFriend(friendId);

        // Then
        Assertions.assertThat(returnedFriend).isEqualTo(expectedFriend);
    }

    @Test
    void shouldFailToGetNonExistingFriend() {
        // Given
        Long nonExistingFriendId = -1L;

        Class<FriendNotFoundException> expectedException = FriendNotFoundException.class;

        // When
        Mockito.when(friendRepository.existsById(nonExistingFriendId)).thenReturn(false);

        // Then
        Assertions.assertThatThrownBy(() -> sut.getFriend(nonExistingFriendId))
                .isInstanceOf(expectedException)
                .hasMessageContaining("Friend not found");
    }

    @Test
    void shouldCreateFriend() {
        // Given
        Friend expectedFriend = new Friend(1L, "Juan");

        // When
        sut.createFriend(expectedFriend);

        // Then
        Mockito.verify(friendRepository).save(expectedFriend);
    }

    @Test
    void shouldUpdateFriend() {
        // Given
        Long friendId = 1L;
        String nameToUpdate = "Juan (Updated)";

        // When
        Mockito.when(friendRepository.existsById(friendId)).thenReturn(true);

        sut.updateFriend(friendId, nameToUpdate);

        // Then
        Mockito.verify(friendRepository).update(friendId, nameToUpdate);
    }

    @Test
    void shouldFailToUpdateNonExistingFriend() {
        // Given
        Long nonExistingFriendId = -1L;
        String nameToUpdate = "Juan";

        Class<FriendNotFoundException> expectedException = FriendNotFoundException.class;

        // When
        Mockito.when(friendRepository.existsById(nonExistingFriendId)).thenReturn(false);

        // Then
        Assertions.assertThatThrownBy(() -> sut.updateFriend(nonExistingFriendId, nameToUpdate))
                .isInstanceOf(expectedException)
                .hasMessageContaining("Friend not found");
    }

    @Test
    void shouldDeleteFriendById() {
        // Given
        Long friendId = 1L;

        // When
        Mockito.when(friendRepository.existsById(friendId)).thenReturn(true);

        sut.deleteFriend(friendId);

        // Then
        Mockito.verify(friendRepository).deleteById(friendId);
    }

    @Test
    void shouldFailToDeleteNonExistingFriend() {
        // Given
        Long nonExistingFriendId = -1L;

        Class<FriendNotFoundException> expectedException = FriendNotFoundException.class;

        // When
        Mockito.when(friendRepository.existsById(nonExistingFriendId)).thenReturn(false);

        // Then
        Assertions.assertThatThrownBy(() -> sut.deleteFriend(nonExistingFriendId))
                .isInstanceOf(expectedException)
                .hasMessageContaining("Friend not found");
    }

}
