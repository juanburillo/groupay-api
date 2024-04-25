package com.izertis.grouPay.friend.infrastructure;

import com.izertis.grouPay.friend.domain.Friend;
import com.izertis.grouPay.friend.infrastructure.primaryadapter.rest.FriendRequest;
import com.izertis.grouPay.friend.infrastructure.primaryadapter.rest.FriendResponse;
import com.izertis.grouPay.friend.infrastructure.secondaryadapter.database.FriendEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FriendMapper {

    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);

    Friend toModel(FriendRequest friendRequest);

    Friend toModel(FriendEntity friendEntity);

    List<Friend> toModelList(List<FriendEntity> friendEntities);

    FriendResponse toDto(Friend friend);

    List<FriendResponse> toDtoList(List<Friend> friends);

    FriendEntity toEntity(Friend friend);

}
