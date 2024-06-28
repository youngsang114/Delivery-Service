package org.example.storeadmin.domain.user.converter;


import lombok.RequiredArgsConstructor;
import org.example.db.store.StoreEntity;
import org.example.db.store.StoreRepository;
import org.example.db.store.enums.StoreStatus;
import org.example.db.storeuser.StoreUserEntity;
import org.example.storeadmin.config.common.annotation.Converter;
import org.example.storeadmin.domain.authorization.model.UserSession;
import org.example.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.example.storeadmin.domain.user.controller.model.StoreUserResponse;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class StoreUserConverter {

    public StoreUserEntity toEntity(
            StoreUserRegisterRequest request,
            StoreEntity storeEntity
    ){

        return StoreUserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .storeEntity(storeEntity) // TODO NULL 일 때 -> 체크 기능 필요
                .build();
    }

    public StoreUserResponse toResponse(
            StoreUserEntity storeUserEntity,
            StoreEntity storeEntity
    ){
        return StoreUserResponse.builder()
                .user(
                        StoreUserResponse.UserResponse.builder()
                                .id(storeUserEntity.getId())
                                .email(storeUserEntity.getEmail())
                                .status(storeUserEntity.getStatus())
                                .role(storeUserEntity.getRole())
                                .registeredAt(storeUserEntity.getRegisteredAt())
                                .unregisteredAt(storeUserEntity.getUnregisteredAt())
                                .lastLoginAt(storeUserEntity.getLastLoginAt())
                                .build()

                )
                .store(
                        StoreUserResponse.StoreResponse.builder()
                                .name(storeEntity.getName())
                                .id(storeEntity.getId())
                                .build()
                )
                .build();
    }

    public StoreUserResponse toResponse(UserSession userSession){
        return StoreUserResponse.builder()
                .user(
                        StoreUserResponse.UserResponse.builder()
                                .id(userSession.getUserId())
                                .email(userSession.getEmail())
                                .status(userSession.getStatus())
                                .role(userSession.getRole())
                                .registeredAt(userSession.getRegisteredAt())
                                .unregisteredAt(userSession.getUnregisteredAt())
                                .lastLoginAt(userSession.getLastLoginAt())
                                .build()

                )
                .store(
                        StoreUserResponse.StoreResponse.builder()
                                .name(userSession.getStoreName())
                                .id(userSession.getStoreId())
                                .build()
                )
                .build();
    }
}
