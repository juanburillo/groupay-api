package com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.transaction.application.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionResponse> getTransactions() {
        return TransactionMapper.INSTANCE.toDtoList(transactionService.getTransactions());
    }

}
