package org.example.api.domain.storemenu.converter;

import lombok.AllArgsConstructor;
import org.example.api.common.annotation.Converter;
import org.example.api.common.error.ErrorCode;
import org.example.api.common.exception.ApiException;
import org.example.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.example.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.example.db.store.StoreRepository;
import org.example.db.storemenu.StoreMenuEntity;

import java.util.Optional;

@Converter
@AllArgsConstructor
public class StoreMenuConverter {

    private final StoreRepository storeRepository;

    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request){

        return Optional.ofNullable(request)
                .map(it ->{
                    return StoreMenuEntity.builder()
                            .storeEntity(storeRepository.findById(it.getStoreId()).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT)))
                            .name(request.getName())
                            .amount(request.getAmount())
                            .thumbnailUrl(request.getThumbnailUrl())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreMenuResponse toResponse(
            StoreMenuEntity storeMenuEntity
    ){
        return Optional.ofNullable(storeMenuEntity)
                .map(it ->{
                    return StoreMenuResponse.builder()
                            .id(storeMenuEntity.getId())
                            .name(storeMenuEntity.getName())
                            .storeId(storeMenuEntity.getStoreEntity().getId())
                            .amount(storeMenuEntity.getAmount())
                            .status(storeMenuEntity.getStatus())
                            .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
                            .likeCount(storeMenuEntity.getLikeCount())
                            .sequence(storeMenuEntity.getSequence())
                            .build()
                            ;
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }
}
