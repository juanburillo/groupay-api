package com.izertis.grouPay.friend.infrastructure.secondaryadapter.database;

import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.friend.domain.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersistenceFriendRepository implements FriendRepository {

    private static final String SELECT_FRIENDS = "SELECT * FROM friend";
    private static final String SELECT_FRIEND = "SELECT * FROM friend WHERE id = ?";
    private static final String INSERT_FRIEND = "INSERT INTO friend (name) VALUES (?)";
    private static final String UPDATE_FRIEND = "UPDATE friend SET name = ? WHERE id = ?";
    private static final String DELETE_FRIEND = "DELETE FROM friend WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersistenceFriendRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Friend> findAll() {
        return jdbcTemplate.query(SELECT_FRIENDS, (rs, rowNum) -> {
            Friend friend = new Friend();
            friend.setId(rs.getLong(1));
            friend.setName(rs.getString(2));
            return friend;
        });
    }

    @Override
    public Friend findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_FRIEND, new Object[]{id},
                (rs, rowNum) -> new Friend(rs.getLong(1), rs.getString(2)));
    }

    @Override
    public void save(Friend friend) {
        jdbcTemplate.update(INSERT_FRIEND, friend.getName());
    }

    @Override
    public void update(Long id, String name) {
        jdbcTemplate.update(UPDATE_FRIEND, name, id);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_FRIEND, id);
    }

}
