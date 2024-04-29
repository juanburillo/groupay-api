package com.izertis.grouPay.transaction.infrastructure.primaryadapter.rest;

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
public class TransactionControllerIT {

    private final MockMvc mvc;

    @Autowired
    public TransactionControllerIT(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void shouldGetAllTransactionsAndReturnStatus200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/transaction")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].sender").exists())
                .andExpect(jsonPath("$[*].recipient").exists())
                .andExpect(jsonPath("$[*].amount").exists());
    }

}
