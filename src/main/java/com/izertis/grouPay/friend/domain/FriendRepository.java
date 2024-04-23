package com.izertis.grouPay.friend.domain;

import java.util.List;

public interface FriendRepository {

    List<Friend> findAll();
    Friend findById(Long id);
    void save(Friend friend);
    void update(Long id, String name);
    void deleteById(Long id);
    boolean existsById(Long id);

}
