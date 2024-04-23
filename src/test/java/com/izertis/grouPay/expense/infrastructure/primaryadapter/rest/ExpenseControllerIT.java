package com.izertis.grouPay.expense.infrastructure.primaryadapter.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldGetAllExpensesAndReturnStatus200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/expense")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].id").exists())
                .andExpect(jsonPath("$[*].amount").exists())
                .andExpect(jsonPath("$[*].description").exists())
                .andExpect(jsonPath("$[*].date").exists())
                .andExpect(jsonPath("$[*].friend").exists())
                .andExpect(jsonPath("$[*].friend.id").exists())
                .andExpect(jsonPath("$[*].friend.name").exists());
    }

    @Test
    void shouldGetExpenseByIdAndReturnStatus200() throws Exception {
        Long id = 1L;

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/expense/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.amount").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.friend").exists())
                .andExpect(jsonPath("$.friend.id").exists())
                .andExpect(jsonPath("$.friend.name").exists());
    }

    @Test
    void shouldCreateExpenseAndReturnStatus201() throws Exception {
        String newExpenseJson = "{\"amount\": 1.0, \"description\": \"Description\", \"friendId\": 1}";

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newExpenseJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteExpenseByIdAndReturnStatus200() throws Exception {
        Long id = 1L;

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/expense/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
