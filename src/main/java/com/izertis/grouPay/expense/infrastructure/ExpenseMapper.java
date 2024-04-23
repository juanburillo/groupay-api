package com.izertis.grouPay.expense.infrastructure;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.ExpenseRequest;
import com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.ExpenseResponse;
import com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    Expense toModel(ExpenseRequest expenseRequest);
    Expense toModel(ExpenseEntity expenseEntity);
    List<Expense> toModelList(List<ExpenseEntity> expenseEntities);
    ExpenseResponse toDto(Expense expense);
    List<ExpenseResponse> toDtoList(List<Expense> expenses);

}
