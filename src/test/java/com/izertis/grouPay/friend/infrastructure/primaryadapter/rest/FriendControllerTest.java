package com.izertis.grouPay.friend.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.friend.application.FriendService;
import com.izertis.grouPay.friend.domain.Friend;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class FriendControllerTest {

    private final FriendService friendService = Mockito.mock(FriendService.class);

    private final FriendController sut = new FriendController(friendService);

    @Test
    void shouldFindAllFriendsAndReturnFriends() {
        // Given
        List<Friend> friends = Arrays.asList(
                new Friend(1L, "Juan"),
                new Friend(2L, "María"),
                new Friend(3L, "Belén")
        );

        List<FriendResponse> expectedFriendResponses = Arrays.asList(
                new FriendResponse(1L, "Juan"),
                new FriendResponse(2L, "María"),
                new FriendResponse(3L, "Belén")
        );

        // When
        Mockito.when(friendService.getFriends()).thenReturn(friends);

        List<FriendResponse> returnedFriendResponses = sut.getFriends();

        // Then
        Assertions.assertThat(returnedFriendResponses).isEqualTo(expectedFriendResponses);
    }

    @Test
    void shouldFindFriendByIdAndReturnFriend() {
        // Given
        Long friendId = 1L;
        Friend friend = new Friend(friendId, "Juan");
        FriendResponse expectedFriendResponse = new FriendResponse(friendId, "Juan");

        // When
        Mockito.when(friendService.getFriend(friendId)).thenReturn(friend);

        FriendResponse returnedFriendResponse = sut.getFriend(friendId);

        // Then
        Assertions.assertThat(returnedFriendResponse).isEqualTo(expectedFriendResponse);
    }

    @Test
    void shouldCreateFriend() {
        // Given
        FriendRequest friendRequest = new FriendRequest(1L, "Juan");
        Friend expectedFriend = new Friend(1L, "Juan");

        // When
        sut.createFriend(friendRequest);

        // Then
        Mockito.verify(friendService).createFriend(expectedFriend);
    }

    @Test
    void shouldUpdateFriend() {
        // Given
        Long friendId = 1L;
        String nameToUpdate = "Juan (Updated)";

        // When
        sut.updateFriend(friendId, nameToUpdate);

        // Then
        Mockito.verify(friendService).updateFriend(friendId, nameToUpdate);
    }

    @Test
    void shouldRemoveFriendById() {
        // Given
        Long friendId = 1L;

        // When
        sut.deleteFriend(friendId);

        // Then
        Mockito.verify(friendService).deleteFriend(friendId);
    }

}
