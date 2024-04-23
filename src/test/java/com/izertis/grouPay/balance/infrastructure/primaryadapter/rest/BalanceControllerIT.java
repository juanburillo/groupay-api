package com.izertis.grouPay.balance.infrastructure.primaryadapter.rest;

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
public class BalanceControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldGetBalancesAndReturnStatus200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/balance")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].friend").exists())
                .andExpect(jsonPath("$[*].friend.id").exists())
                .andExpect(jsonPath("$[*].friend.name").exists())
                .andExpect(jsonPath("$[*].amount").exists());
    }

    @Test
    void shouldGetBalanceByFriendIdAndReturnStatus200() throws Exception {
        Long friendId = 1L;

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/balance/{friendId}", friendId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.friend").exists())
                .andExpect(jsonPath("$.friend.id").exists())
                .andExpect(jsonPath("$.friend.name").exists())
                .andExpect(jsonPath("$.amount").exists());
    }

}
