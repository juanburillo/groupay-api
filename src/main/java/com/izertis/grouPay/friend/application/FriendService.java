package com.izertis.grouPay.friend.application;

import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.friend.domain.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    private final FriendRepository friendRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public List<Friend> getFriends() {
        return friendRepository.findAll();
    }

    public Friend getFriend(Long id) {
        return friendRepository.findById(id);
    }

    public void createFriend(Friend friend) {
        friendRepository.save(friend);
    }

    public void updateFriend(Long id, String name) {
        friendRepository.update(id, name);
    }

    public void deleteFriend(Long id) {
        friendRepository.deleteById(id);
    }

}
