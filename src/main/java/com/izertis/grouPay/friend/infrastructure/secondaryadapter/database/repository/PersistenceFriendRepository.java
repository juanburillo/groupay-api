package com.izertis.grouPay.friend.infrastructure.secondaryadapter.database.repository;

import com.izertis.grouPay.friend.domain.model.Friend;
import com.izertis.grouPay.friend.domain.repository.FriendRepository;
import com.izertis.grouPay.friend.application.FriendMapper;
import com.izertis.grouPay.friend.infrastructure.secondaryadapter.database.entity.FriendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersistenceFriendRepository implements FriendRepository {

    private static final String SELECT_FRIENDS = "SELECT * FROM friend";
    private static final String SELECT_FRIEND = "SELECT * FROM friend WHERE id = ?";
    private static final String INSERT_FRIEND = "INSERT INTO friend (name) VALUES (?)";
    private static final String INSERT_FRIEND_WITH_ID = "INSERT INTO friend (id, name) VALUES (?,?)";
    private static final String UPDATE_FRIEND = "UPDATE friend SET name = ? WHERE id = ?";
    private static final String DELETE_FRIENDS = "DELETE FROM friend";
    private static final String DELETE_FRIEND = "DELETE FROM friend WHERE id = ?";
    private static final String FRIEND_EXISTS = "SELECT COUNT(*) FROM friend WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersistenceFriendRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Friend> findAll() {
        List<FriendEntity> friendEntities = jdbcTemplate.query(SELECT_FRIENDS, (rs, rowNum) -> {
            FriendEntity friendEntity = new FriendEntity();
            friendEntity.setId(rs.getLong(1));
            friendEntity.setName(rs.getString(2));
            return friendEntity;
        });

        return FriendMapper.INSTANCE.toModelList(friendEntities);
    }

    @Override
    public Friend findById(Long id) {
        FriendEntity friendEntity = jdbcTemplate.queryForObject(SELECT_FRIEND, new Object[]{id},
                (rs, rowNum) -> new FriendEntity(rs.getLong(1), rs.getString(2)));

        return FriendMapper.INSTANCE.toModel(friendEntity);
    }

    @Override
    public void save(Friend friend) {
        FriendEntity friendEntity = FriendMapper.INSTANCE.toEntity(friend);
        if (friendEntity.getId() == null) {
            jdbcTemplate.update(INSERT_FRIEND, friendEntity.getName());
        } else {
            jdbcTemplate.update(INSERT_FRIEND_WITH_ID, friendEntity.getId(), friendEntity.getName());
        }
    }

    @Override
    public void update(Long id, String name) {
        jdbcTemplate.update(UPDATE_FRIEND, name, id);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_FRIENDS);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_FRIEND, id);
    }

    @Override
    public boolean existsById(Long id) {
        return jdbcTemplate.queryForObject(FRIEND_EXISTS, new Object[]{id}, Integer.class) > 0;
    }

}
