package com.izertis.grouPay.friend.application.service;

import com.izertis.grouPay.friend.application.exception.FriendNotFoundException;
import com.izertis.grouPay.friend.domain.model.Friend;
import com.izertis.grouPay.friend.domain.repository.FriendRepository;
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
        if (friendRepository.existsById(id)) {
            return friendRepository.findById(id);
        } else {
            throw new FriendNotFoundException("Friend not found");
        }
    }

    public void createFriend(Friend friend) {
        friendRepository.save(friend);
    }

    public void updateFriend(Long id, String name) {
        if (friendRepository.existsById(id)) {
            friendRepository.update(id, name);
        } else {
            throw new FriendNotFoundException("Friend not found");
        }
    }

    public void deleteFriends() {
        friendRepository.deleteAll();
    }

    public void deleteFriend(Long id) {
        if (friendRepository.existsById(id)) {
            friendRepository.deleteById(id);
        } else {
            throw new FriendNotFoundException("Friend not found");
        }
    }

}
