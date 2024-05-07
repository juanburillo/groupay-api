package com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest.controller;

import com.izertis.grouPay.transaction.application.TransactionMapper;
import com.izertis.grouPay.transaction.application.service.TransactionService;
import com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest.dto.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Get the minimum amount of payment transactions every friend has to perform to clear the group debt")
    @ApiResponse(responseCode = "200", description = "Calculated and returned all transactions", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))
    })
    @GetMapping
    public List<TransactionResponse> getTransactions() {
        return TransactionMapper.INSTANCE.toDtoList(transactionService.getTransactions());
    }

}
