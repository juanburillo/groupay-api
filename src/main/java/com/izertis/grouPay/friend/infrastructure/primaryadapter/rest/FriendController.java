package com.izertis.grouPay.friend.infrastructure.primaryadapter.rest;

import com.izertis.grouPay.friend.application.FriendService;
import com.izertis.grouPay.friend.infrastructure.FriendMapper;
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

    @GetMapping
    public List<FriendResponse> getFriends() {
        return FriendMapper.INSTANCE.toDtoList(friendService.getFriends());
    }

    @GetMapping("/{id}")
    public FriendResponse getFriend(@PathVariable Long id) {
        return FriendMapper.INSTANCE.toDto(friendService.getFriend(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createFriend(@RequestBody FriendRequest friendRequest) {
        friendService.createFriend(FriendMapper.INSTANCE.toModel(friendRequest));
    }

    @PutMapping("/{id}")
    public void updateFriend(@PathVariable Long id,
                             @RequestParam String name) {
        friendService.updateFriend(id, name);
    }

    @DeleteMapping("/{id}")
    public void deleteFriend(@PathVariable Long id) {
        friendService.deleteFriend(id);
    }

}
