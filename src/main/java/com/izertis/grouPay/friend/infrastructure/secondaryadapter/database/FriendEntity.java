package com.izertis.grouPay.friend.infrastructure.secondaryadapter.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "friend")
public class FriendEntity {

    @Id
    private Long id;
    private String name;

}
