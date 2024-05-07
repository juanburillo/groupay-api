package com.izertis.grouPay.friend.infrastructure.secondaryadapter.database.repository;

import com.izertis.grouPay.friend.application.FriendMapper;
import com.izertis.grouPay.friend.domain.model.Friend;
import com.izertis.grouPay.friend.domain.repository.FriendRepository;
import com.izertis.grouPay.friend.infrastructure.secondaryadapter.database.entity.FriendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Repository
public class JpaFriendRepositoryAdapter implements FriendRepository {

    private final JpaFriendRepository jpaRepository;

    @Autowired
    public JpaFriendRepositoryAdapter(JpaFriendRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Friend> findAll() {
        return jpaRepository.findAll().stream()
                .map(FriendMapper.INSTANCE::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Friend findById(Long id) {
        Optional<FriendEntity> friendEntityOptional = jpaRepository.findById(id);
        return friendEntityOptional.map(FriendMapper.INSTANCE::toModel).orElse(null);
    }

    @Override
    public void save(Friend friend) {
        FriendEntity friendEntity = FriendMapper.INSTANCE.toEntity(friend);
        jpaRepository.save(friendEntity);
    }

    @Override
    public void update(Long id, String name) {
        Optional<FriendEntity> friendEntityOptional = jpaRepository.findById(id);
        friendEntityOptional.ifPresent(entity -> {
            entity.setName(name);
            jpaRepository.save(entity);
        });
    }

    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

}
