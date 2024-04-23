package com.izertis.grouPay.expense.infrastructure;

import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.ExpenseRequest;
import com.izertis.grouPay.expense.infrastructure.primaryadapter.rest.ExpenseResponse;
import com.izertis.grouPay.expense.infrastructure.secondaryadapter.database.ExpenseEntity;
import com.izertis.grouPay.friend.domain.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(source = "friendId", target = "friend")
    Expense toModel(ExpenseRequest expenseRequest);

    @Mapping(source = "friendId", target = "friend")
    Expense toModel(ExpenseEntity expenseEntity);

    @Mapping(source = "friendId", target = "friend")
    List<Expense> toModelList(List<ExpenseEntity> expenseEntities);

    ExpenseResponse toDto(Expense expense);

    List<ExpenseResponse> toDtoList(List<Expense> expenses);

    default Friend map(Long friendId) {
        return new Friend(friendId);
    }

}
