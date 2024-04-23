package com.izertis.grouPay.friend.infrastructure.primaryadapter.rest;

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
public class FriendControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
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
    void shouldCreateFriendAndReturnStatus201() throws Exception {
        String newFriendJson = "{\"name\": \"Juan\"}";

        mvc.perform(MockMvcRequestBuilders
                .post("/api/friend")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newFriendJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateFriendAndReturnStatus200() throws Exception {
        Long id = 1L;
        String updatedName = "UpdatedName";

        mvc.perform(MockMvcRequestBuilders
                .put("/api/friend/{id}?name={name}", id, updatedName))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteFriendByIdAndReturnStatus200() throws Exception {
        Long id = 1L;

        mvc.perform(MockMvcRequestBuilders
                .delete("/api/friend/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
