package com.izertis.grouPay.friend.infrastructure.primaryadapter.rest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FriendControllerIT {

    private final MockMvc mvc;

    @Autowired
    public FriendControllerIT(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    @Order(1)
    void shouldCreateFriendAndReturnStatus201() throws Exception {
        String newFriendJson = "{\"id\": 1, \"name\": \"Juan\"}";

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/friend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newFriendJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void shouldGetAllFriendsAndReturnStatus200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/friend")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].id").exists())
                .andExpect(jsonPath("$[*].name").exists());
    }

    @Test
    @Order(3)
    void shouldGetFriendByIdAndReturnStatus200() throws Exception {
        Long id = 1L;

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/friend/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @Order(4)
    void shouldUpdateFriendAndReturnStatus200() throws Exception {
        Long id = 1L;
        String updatedName = "UpdatedName";

        mvc.perform(MockMvcRequestBuilders
                .put("/api/friend/{id}?name={name}", id, updatedName))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void shouldDeleteFriendByIdAndReturnStatus200() throws Exception {
        Long id = 1L;

        mvc.perform(MockMvcRequestBuilders
                .delete("/api/friend/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
