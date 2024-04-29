package com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.transaction.domain.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    List<TransactionResponse> toDtoList(List<Transaction> transactions);

}
