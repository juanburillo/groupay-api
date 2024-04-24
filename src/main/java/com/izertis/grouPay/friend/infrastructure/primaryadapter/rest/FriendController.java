package com.izertis.grouPay.friend.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.friend.application.FriendService;
import com.izertis.grouPay.friend.infrastructure.FriendMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @Operation(summary = "Get all friends")
    @GetMapping
    public List<FriendResponse> getFriends() {
        return FriendMapper.INSTANCE.toDtoList(friendService.getFriends());
    }

    @Operation(summary = "Get a friend by its ID")
    @GetMapping("/{id}")
    public FriendResponse getFriend(@PathVariable Long id) {
        return FriendMapper.INSTANCE.toDto(friendService.getFriend(id));
    }

    @Operation(summary = "Create a friend in the database")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createFriend(@RequestBody FriendRequest friendRequest) {
        friendService.createFriend(FriendMapper.INSTANCE.toModel(friendRequest));
    }

    @Operation(summary = "Update a friend")
    @PutMapping("/{id}")
    public void updateFriend(@PathVariable Long id,
                             @RequestParam String name) {
        friendService.updateFriend(id, name);
    }

    @Operation(summary = "Delete a friend by its ID")
    @DeleteMapping("/{id}")
    public void deleteFriend(@PathVariable Long id) {
        friendService.deleteFriend(id);
    }

}
