package com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.repository;

import com.izertis.grouPay.expense.application.ExpenseMapper;
import com.izertis.grouPay.expense.domain.model.Expense;
import com.izertis.grouPay.expense.domain.repository.ExpenseRepository;
import com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.entity.ExpenseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Repository
public class JpaExpenseRepositoryAdapter implements ExpenseRepository {

    private final JpaExpenseRepository jpaRepository;

    @Autowired
    public JpaExpenseRepositoryAdapter(JpaExpenseRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Expense> findAll() {
        return jpaRepository.findAll().stream()
                .map(ExpenseMapper.INSTANCE::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Expense findById(Long id) {
        Optional<ExpenseEntity> expenseEntityOptional = jpaRepository.findById(id);
        Expense expense = expenseEntityOptional.map(ExpenseMapper.INSTANCE::toModel).orElse(null);
        expense.getFriend().setName(jpaRepository.findExpenseFriendNameById(id));
        return expense;
    }

    @Override
    public void save(Expense expense) {
        ExpenseEntity expenseEntity = ExpenseMapper.INSTANCE.toEntity(expense);
        jpaRepository.save(expenseEntity);
    }

    @Override
    public void update(Expense expense) {
        Optional<ExpenseEntity> expenseEntityOptional = jpaRepository.findById(expense.getId());
        expenseEntityOptional.ifPresent(entity -> {
            if (expense.getAmount() != null) entity.setAmount(expense.getAmount());
            if (expense.getDescription() != null) entity.setDescription(expense.getDescription());
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
