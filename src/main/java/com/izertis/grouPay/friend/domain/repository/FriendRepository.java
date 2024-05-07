package com.izertis.grouPay.friend.domain.repository;

import com.izertis.grouPay.friend.domain.model.Friend;

import java.util.List;

public interface FriendRepository {

    List<Friend> findAll();
    Friend findById(Long id);
    void save(Friend friend);
    void update(Long id, String name);
    void deleteAll();
    void deleteById(Long id);
    boolean existsById(Long id);

}
