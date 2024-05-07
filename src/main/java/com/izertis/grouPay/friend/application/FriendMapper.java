package com.izertis.grouPay.friend.application;

import com.izertis.grouPay.friend.domain.model.Friend;
import com.izertis.grouPay.friend.infrastructure.primaryadapter.rest.dto.FriendRequest;
import com.izertis.grouPay.friend.infrastructure.primaryadapter.rest.dto.FriendResponse;
import com.izertis.grouPay.friend.infrastructure.secondaryadapter.database.entity.FriendEntity;
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
