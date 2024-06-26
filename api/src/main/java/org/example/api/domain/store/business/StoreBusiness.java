package org.example.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.example.api.common.annotation.Business;
import org.example.api.domain.store.controller.model.StoreRegisterRequest;
import org.example.api.domain.store.controller.model.StoreResponse;
import org.example.api.domain.store.converter.StoreConverter;
import org.example.api.domain.store.service.StoreService;
import org.example.db.store.StoreEntity;
import org.example.db.store.enums.StoreCategory;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreBusiness {
    private final StoreService storeService;
    private final StoreConverter storeConverter;

    /**
     * 가게 등록 로직
     * request -> entity -> response
     */
    public StoreResponse register(
            StoreRegisterRequest storeRegisterRequest
    ){
        StoreEntity entity = storeConverter.toEntity(storeRegisterRequest);
        StoreEntity register = storeService.register(entity);
        StoreResponse response = storeConverter.toResponse(register);
        return response;
    }

    /**
     * 특정 catetory store 반환 로직
     * entity list -> resposne list
     */
    public List<StoreResponse> searchCategory(
            StoreCategory storeCategory
    ){
        List<StoreEntity> storeList = storeService.searchByCategory(storeCategory);

        List<StoreResponse> responseList = storeList.stream()
                .map(it -> {
                    return storeConverter.toResponse(it);
                })
                .collect(Collectors.toList());

        return responseList;
    }
}
