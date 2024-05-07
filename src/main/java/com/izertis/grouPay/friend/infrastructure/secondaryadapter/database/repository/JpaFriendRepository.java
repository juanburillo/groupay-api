package com.izertis.grouPay.friend.infrastructure.secondaryadapter.database.repository;

import com.izertis.grouPay.friend.infrastructure.secondaryadapter.database.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFriendRepository extends JpaRepository<FriendEntity, Long> {
}
