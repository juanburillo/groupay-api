package com.izertis.grouPay.friend.infrastructure;

import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.friend.infrastructure.primaryadapter.rest.FriendRequest;
import com.izertis.grouPay.friend.infrastructure.primaryadapter.rest.FriendResponse;

import java.util.ArrayList;
import java.util.List;

public class FriendMapper {

    public static Friend toFriend(FriendRequest friendRequest) {
        return new Friend(
                friendRequest.getId(),
                friendRequest.getName()
        );
    }

    public static FriendResponse toFriendResponse(Friend friend) {
        return new FriendResponse(
                friend.getId(),
                friend.getName()
        );
    }

    public static List<FriendResponse> toFriendResponses(List<Friend> friends) {
        List<FriendResponse> friendResponses = new ArrayList<>();
        for (Friend friend : friends) {
            friendResponses.add(
                    new FriendResponse(
                            friend.getId(),
                            friend.getName()
                    )
            );
        }
        return friendResponses;
    }

}
