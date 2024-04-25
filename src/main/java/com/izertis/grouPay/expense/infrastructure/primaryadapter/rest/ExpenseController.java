package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.expense.application.ExpenseService;
import com.izertis.grouPay.expense.infrastructure.ExpenseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(summary = "Get all expenses")
    @ApiResponse(responseCode = "200", description = "Found all expenses", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseResponse.class))
    })
    @GetMapping
    public List<ExpenseResponse> getExpenses() {
        return ExpenseMapper.INSTANCE.toDtoList(expenseService.getExpenses());
    }

    @Operation(summary = "Get an expense by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found specified expense", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Expense not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ExpenseResponse getExpense(@Parameter(description = "ID of expense to be searched") @PathVariable Long id) {
        return ExpenseMapper.INSTANCE.toDto(expenseService.getExpense(id));
    }

    @Operation(summary = "Create an expense in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expense created"),
            @ApiResponse(responseCode = "404", description = "Friend not found")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createExpense(@RequestBody ExpenseRequest expenseRequest) {
        expenseService.createExpense(ExpenseMapper.INSTANCE.toModel(expenseRequest));
    }

    @Operation(summary = "Delete an expense by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense deleted"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @DeleteMapping("/{id}")
    public void deleteExpense(@Parameter(description = "ID of expense to be deleted") @PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

}
