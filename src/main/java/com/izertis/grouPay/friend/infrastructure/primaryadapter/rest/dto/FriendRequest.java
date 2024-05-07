package com.izertis.grouPay.friend.infrastructure.primaryadapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class FriendRequest {

    private Long id;
    private String name;

}
