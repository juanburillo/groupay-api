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
        // Define sample friend data
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

        // Mock friendService to return sample data
        Mockito.when(friendService.getFriends()).thenReturn(friends);

        // Call the controller method
        List<FriendResponse> returnedFriendResponses = sut.getFriends();

        // Verify that the response matches expectations
        Assertions.assertThat(returnedFriendResponses).isEqualTo(expectedFriendResponses);
    }

    @Test
    void shouldFindFriendByIdAndReturnFriend() {
        // Define friend ID and sample friend data
        Long friendId = 1L;
        Friend friend = new Friend(friendId, "Juan");
        FriendResponse expectedFriendResponse = new FriendResponse(friendId, "Juan");

        // Mock friendService to return friend for the ID
        Mockito.when(friendService.getFriend(friendId)).thenReturn(friend);

        // Call the controller method
        FriendResponse returnedFriendResponse = sut.getFriend(friendId);

        // Verify that the response matches expectations
        Assertions.assertThat(returnedFriendResponse).isEqualTo(expectedFriendResponse);
    }

    @Test
    void shouldCreateFriend() {
        // Define friend data to be created
        FriendRequest friendRequest = new FriendRequest(1L, "Juan");
        Friend expectedFriend = new Friend(1L, "Juan");

        // Mock friendService to handle creating friend
        Mockito.doNothing().when(friendService).createFriend(expectedFriend);

        // Call the controller method
        sut.createFriend(friendRequest);

        // Verify Mockito captures the createFriend call with the correct data
        Mockito.verify(friendService).createFriend(expectedFriend);
    }

    @Test
    void shouldUpdateFriend() {
        // Define friend data for update
        Long friendId = 1L;
        String nameToUpdate = "Juan (Updated)";

        // Mock friendService to handle update
        Mockito.doNothing().when(friendService).updateFriend(friendId, nameToUpdate);

        // Call the controller method
        sut.updateFriend(friendId, nameToUpdate);

        // Verify Mockito captures the updateFriend call with the correct data
        Mockito.verify(friendService).updateFriend(friendId, nameToUpdate);
    }

    @Test
    void shouldRemoveFriendById() {
        // Define friend ID to be deleted
        Long friendId = 1L;

        // Mock friendService to handle deletion
        Mockito.doNothing().when(friendService).deleteFriend(friendId);

        // Call the controller method
        sut.deleteFriend(friendId);

        // Verify Mockito captures the deleteFriend call with the correct data
        Mockito.verify(friendService).deleteFriend(friendId);
    }

}
