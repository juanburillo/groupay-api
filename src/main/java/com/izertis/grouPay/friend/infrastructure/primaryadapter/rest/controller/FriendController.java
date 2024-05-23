package com.izertis.grouPay.friend.infrastructure.primaryadapter.rest.controller;

import com.izertis.grouPay.friend.application.service.FriendService;
import com.izertis.grouPay.friend.application.FriendMapper;
import com.izertis.grouPay.friend.infrastructure.primaryadapter.rest.dto.FriendRequest;
import com.izertis.grouPay.friend.infrastructure.primaryadapter.rest.dto.FriendResponse;
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
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @Operation(summary = "Get all friends")
    @ApiResponse(responseCode = "200", description = "Found all friends", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FriendResponse.class))
    })
    @GetMapping
    public List<FriendResponse> getFriends() {
        return FriendMapper.INSTANCE.toDtoList(friendService.getFriends());
    }

    @Operation(summary = "Get a friend by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found specified friend", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FriendResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Friend not found", content = @Content)
    })
    @GetMapping("/{id}")
    public FriendResponse getFriend(@Parameter(description = "ID of friend to be searched") @PathVariable Long id) {
        return FriendMapper.INSTANCE.toDto(friendService.getFriend(id));
    }

    @Operation(summary = "Create a friend in the database")
    @ApiResponse(responseCode = "201", description = "Friend created")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createFriend(@RequestBody FriendRequest friendRequest) {
        friendService.createFriend(FriendMapper.INSTANCE.toModel(friendRequest));
    }

    @Operation(summary = "Update a friend")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friend updated"),
            @ApiResponse(responseCode = "404", description = "Friend not found", content = @Content)
    })
    @PutMapping("/{id}")
    public void updateFriend(@Parameter(description = "ID of friend to be updated") @PathVariable Long id,
                             @Parameter(description = "Name to be updated", example = "Juan") @RequestParam String name) {
        friendService.updateFriend(id, name);
    }

    @Operation(summary = "Delete all friends")
    @ApiResponse(responseCode = "200", description = "Friends deleted")
    @DeleteMapping()
    public void deleteFriends() {
        friendService.deleteFriends();
    }

    @Operation(summary = "Delete a friend by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friend deleted"),
            @ApiResponse(responseCode = "404", description = "Friend not found")
    })
    @DeleteMapping("/{id}")
    public void deleteFriend(@Parameter(description = "ID of friend to be deleted") @PathVariable Long id) {
        friendService.deleteFriend(id);
    }

}
