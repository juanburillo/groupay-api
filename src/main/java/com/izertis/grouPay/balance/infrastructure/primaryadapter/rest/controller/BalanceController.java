package com.izertis.grouPay.balance.infrastructure.primaryadapter.rest.controller;

import com.izertis.grouPay.balance.application.BalanceMapper;
import com.izertis.grouPay.balance.application.service.BalanceService;
import com.izertis.grouPay.balance.infrastructure.primaryadapter.rest.dto.BalanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Get all balances")
    @ApiResponse(responseCode = "200", description = "Calculated and returned all balances", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceResponse.class))
    })
    @GetMapping
    public List<BalanceResponse> getBalances() {
        return BalanceMapper.INSTANCE.toDtoList(balanceService.getBalances());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Get a balance by its friend ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculated and returned specified friend balance", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Friend not found", content = @Content)
    })
    @GetMapping("/{friendId}")
    public BalanceResponse getBalance(@Parameter(description = "ID of friend to calculate balance") @PathVariable Long friendId) {
        return BalanceMapper.INSTANCE.toDto(balanceService.getBalance(friendId));
    }

}
