package org.example.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.example.api.common.annotation.Business;
import org.example.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.example.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.example.api.domain.storemenu.converter.StoreMenuConverter;
import org.example.api.domain.storemenu.service.StoreMenuService;
import org.example.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    // storeMenu 등록 메서드
    public StoreMenuResponse register(
            StoreMenuRegisterRequest request
    ){
        //req -> entity -> save -> responase
        StoreMenuEntity entity = storeMenuConverter.toEntity(request);
        StoreMenuEntity save = storeMenuService.register(entity);
        StoreMenuResponse response = storeMenuConverter.toResponse(save);
        return response;
    }

    public List<StoreMenuResponse> search(Long storeId){
        List<StoreMenuEntity> list = storeMenuService.getStoreMenuByStoreId(storeId);
        List<StoreMenuResponse> res_list = list.stream()
                .map(it -> {
                    return storeMenuConverter.toResponse(it);
                })
                .collect(Collectors.toList());
        return res_list;
    }
}
