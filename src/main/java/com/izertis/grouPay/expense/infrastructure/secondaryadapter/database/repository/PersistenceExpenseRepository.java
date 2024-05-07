package com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.repository;

import com.izertis.grouPay.expense.domain.model.Expense;
import com.izertis.grouPay.expense.domain.repository.ExpenseRepository;
import com.izertis.grouPay.expense.application.ExpenseMapper;
import com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.entity.ExpenseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceExpenseRepository implements ExpenseRepository {

    private static final String SELECT_EXPENSES = "SELECT * FROM expense";
    private static final String SELECT_EXPENSE = "SELECT * FROM expense WHERE id = ?";
    private static final String INSERT_EXPENSE = "INSERT INTO expense (amount, description, date, friend_id) VALUES (?,?,?,?)";
    private static final String INSERT_EXPENSE_WITH_ID = "INSERT INTO expense (id, amount, description, date, friend_id) VALUES (?,?,?,?,?)";
    private static final String DELETE_EXPENSES = "DELETE FROM expense";
    private static final String DELETE_EXPENSE = "DELETE FROM expense WHERE id = ?";
    private static final String EXPENSE_EXISTS = "SELECT COUNT(*) FROM expense WHERE id = ?";
    private static final String SELECT_EXPENSE_FRIEND_NAME = "SELECT name FROM friend JOIN expense ON friend.id = friend_id WHERE expense.id = ?";

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

        List<Expense> expenses = ExpenseMapper.INSTANCE.toModelList(expenseEntities);
        for (Expense expense : expenses) {
            expense.getFriend().setName(jdbcTemplate.queryForObject(SELECT_EXPENSE_FRIEND_NAME, new Object[]{(expense.getId())}, String.class));
        }
        return expenses;
    }

    @Override
    public Expense findById(Long id) {
        ExpenseEntity expenseEntity = jdbcTemplate.queryForObject(SELECT_EXPENSE, new Object[]{id}, (rs, rowNum) ->
                new ExpenseEntity(rs.getLong(1),
                rs.getDouble(2),
                rs.getString(3),
                rs.getTimestamp(4),
                rs.getLong(5)));

        Expense expense = ExpenseMapper.INSTANCE.toModel(expenseEntity);
        expense.getFriend().setName(jdbcTemplate.queryForObject(SELECT_EXPENSE_FRIEND_NAME, new Object[]{(expense.getId())}, String.class));
        return expense;
    }

    @Override
    public void save(Expense expense) {
        ExpenseEntity expenseEntity = ExpenseMapper.INSTANCE.toEntity(expense);

        if(expenseEntity.getId() == null) {
            jdbcTemplate.update(INSERT_EXPENSE, expenseEntity.getAmount(), expenseEntity.getDescription(), expenseEntity.getDate(), expenseEntity.getFriendId());
        } else {
            jdbcTemplate.update(INSERT_EXPENSE_WITH_ID, expenseEntity.getId(), expenseEntity.getAmount(), expenseEntity.getDescription(), expenseEntity.getDate(), expenseEntity.getFriendId());
        }
    }

    @Override
    public void update(Expense expense) {
        ExpenseEntity expenseEntity = ExpenseMapper.INSTANCE.toEntity(expense);
        if (expense.getAmount() != null) {
            jdbcTemplate.update("UPDATE expense SET amount = ? WHERE id = ?", expenseEntity.getAmount(), expenseEntity.getId());
        }
        if (expense.getDescription() != null) {
            jdbcTemplate.update("UPDATE expense SET description = ? WHERE id = ?", expenseEntity.getDescription(), expenseEntity.getId());
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_EXPENSES);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_EXPENSE, id);
    }

    @Override
    public boolean existsById(Long id) {
        return jdbcTemplate.queryForObject(EXPENSE_EXISTS, new Object[]{id}, Integer.class) > 0;
    }

}
