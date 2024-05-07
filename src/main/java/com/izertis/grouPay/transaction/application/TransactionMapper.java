package com.izertis.grouPay.transaction.application;

import com.izertis.grouPay.transaction.domain.Transaction;
import com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest.dto.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    List<TransactionResponse> toDtoList(List<Transaction> transactions);

}
