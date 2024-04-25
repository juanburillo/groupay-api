package com.izertis.grouPay.balance.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.friend.domain.FriendRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BalanceControllerIT {

    private final MockMvc mvc;
    private final FriendRepository friendRepository;

    @Autowired
    public BalanceControllerIT(MockMvc mvc, FriendRepository friendRepository) {
        this.mvc = mvc;
        this.friendRepository = friendRepository;
    }

    @BeforeAll
    public void setup() {
        if (friendRepository.existsById(1L)) {
            friendRepository.deleteById(1L);
        }
        friendRepository.save(new Friend(1L, "Juan"));
    }

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

    @AfterAll
    public void done() {
        friendRepository.deleteById(1L);
    }

}
