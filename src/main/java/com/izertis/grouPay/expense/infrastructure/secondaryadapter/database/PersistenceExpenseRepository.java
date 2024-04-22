package com.izertis.grouPay.expense.infrastructure.secondaryadapter.database;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.domain.ExpenseRepository;
import com.izertis.grouPay.expense.infrastructure.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceExpenseRepository implements ExpenseRepository {

    private static final String SELECT_EXPENSES = "SELECT * FROM expense";
    private static final String SELECT_EXPENSE = "SELECT * FROM expense WHERE id = ?";
    private static final String INSERT_EXPENSE = "INSERT INTO expense (amount, description, date, friend_id) VALUES (?,?,?,?)";
    private static final String DELETE_EXPENSE = "DELETE FROM expense WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersistenceExpenseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Expense> findAll() {
        List<ExpenseEntity> expenseEntities = jdbcTemplate.query(SELECT_EXPENSES, (rs, rowNum) -> {
            ExpenseEntity expenseEntity = new ExpenseEntity();
            expenseEntity.setId(rs.getLong(1));
            expenseEntity.setAmount(rs.getDouble(2));
            expenseEntity.setDescription(rs.getString(3));
            expenseEntity.setDate(rs.getTimestamp(4));
            expenseEntity.setFriendId(rs.getLong(5));
            return expenseEntity;
        });

        return ExpenseMapper.toModelList(expenseEntities);
    }

    @Override
    public Expense findById(Long id) {
        ExpenseEntity expenseEntity = jdbcTemplate.queryForObject(SELECT_EXPENSE, new Object[]{id}, (rs, rowNum) ->
                new ExpenseEntity(rs.getLong(1),
                rs.getDouble(2),
                rs.getString(3),
                rs.getTimestamp(4),
                rs.getLong(5)));

        return ExpenseMapper.toModel(expenseEntity);
    }

    @Override
    public void save(Expense expense) {
        jdbcTemplate.update(INSERT_EXPENSE, expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getFriend().getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_EXPENSE, id);
    }

}
