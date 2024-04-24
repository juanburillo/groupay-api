package com.izertis.grouPay.balance.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.balance.application.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Operation(summary = "Get all balances")
    @GetMapping
    public List<BalanceResponse> getBalances() {
        return BalanceMapper.INSTANCE.toDtoList(balanceService.getBalances());
    }

    @Operation(summary = "Get a balance by its friend ID")
    @GetMapping("/{friendId}")
    public BalanceResponse getBalance(@PathVariable Long friendId) {
        return BalanceMapper.INSTANCE.toDto(balanceService.getBalance(friendId));
    }

}
