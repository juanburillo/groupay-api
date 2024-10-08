package com.izertis.grouPay.balance.application;

import com.izertis.grouPay.balance.domain.Balance;
import com.izertis.grouPay.balance.infrastructure.primaryadapter.rest.dto.BalanceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BalanceMapper {

    BalanceMapper INSTANCE = Mappers.getMapper(BalanceMapper.class);

    BalanceResponse toDto(Balance balance);

    List<BalanceResponse> toDtoList(List<Balance> balances);

}
