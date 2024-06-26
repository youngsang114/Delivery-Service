package org.example.api.domain.store.converter;

import org.example.api.common.annotation.Converter;
import org.example.api.common.error.ErrorCode;
import org.example.api.common.exception.ApiException;
import org.example.api.domain.store.controller.model.StoreRegisterRequest;
import org.example.api.domain.store.controller.model.StoreResponse;
import org.example.db.store.StoreEntity;

import java.util.Optional;

@Converter
public class StoreConverter {

    public StoreEntity toEntity(
            StoreRegisterRequest request
    ){

        return Optional.ofNullable(request)
                .map(it ->{
                    return StoreEntity.builder()
                            .name(request.getName())
                            .address(request.getAddress())
                            .category(request.getStoreCategory())
                            .minimumAmount(request.getMinimumAmount())
                            .minimumDeliveryAmount(request.getMinimumDeliveryAmount())
                            .thumbnailUrl(request.getThumbnailUrl())
                            .phoneNumber(request.getPhoneNumber())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "StoreEntity 생성 중 StoreRegisterRequest null"));

    }

    public StoreResponse toResponse(
            StoreEntity storeEntity
    ){
        return Optional.ofNullable(storeEntity)
                .map(it ->{
                    return StoreResponse.builder()
                            .id(storeEntity.getId())
                            .name(storeEntity.getName())
                            .status(storeEntity.getStatus())
                            .category(storeEntity.getCategory())
                            .address(storeEntity.getAddress())
                            .minimumAmount(storeEntity.getMinimumAmount())
                            .minimumDeliveryAmount(storeEntity.getMinimumDeliveryAmount())
                            .thumbnailUrl(storeEntity.getThumbnailUrl())
                            .phoneNumber(storeEntity.getPhoneNumber())
                            .star(storeEntity.getStar())
                            .build();

                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "Response 생성 중 storeEntity null"));

    }
}
