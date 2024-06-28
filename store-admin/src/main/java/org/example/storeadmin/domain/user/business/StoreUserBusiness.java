package org.example.storeadmin.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.example.db.store.StoreEntity;
import org.example.db.store.StoreRepository;
import org.example.db.store.enums.StoreStatus;
import org.example.db.storeuser.StoreUserEntity;
import org.example.storeadmin.config.common.annotation.Business;
import org.example.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.example.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.example.storeadmin.domain.user.converter.StoreUserConverter;
import org.example.storeadmin.domain.user.service.StoreUserService;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository; // TODO Service로 변경하기
    public StoreUserResponse register(
            StoreUserRegisterRequest request
    ){
        Optional<StoreEntity> storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(request.getStoreName(), StoreStatus.REGISTERED);
        StoreUserEntity entity = storeUserConverter.toEntity(request, storeEntity.get()); // TODO null point check
        StoreUserEntity newEntity = storeUserService.register(entity);
        StoreUserResponse response = storeUserConverter.toResponse(newEntity, storeEntity.get());

        return response;

    }
}
