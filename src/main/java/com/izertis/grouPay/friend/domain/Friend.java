package com.izertis.grouPay.friend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    private Long id;
    private String name;

    public Friend(Long id) {
        this.id = id;
    }

    public Friend(String name) {
        this.name = name;
    }

}
