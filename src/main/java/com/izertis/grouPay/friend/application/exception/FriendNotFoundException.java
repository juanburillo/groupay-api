package com.izertis.grouPay.friend.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FriendNotFoundException extends RuntimeException {

    public FriendNotFoundException(String message) {
        super(message);
    }

}
