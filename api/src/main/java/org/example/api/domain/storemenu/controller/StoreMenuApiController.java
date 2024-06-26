package org.example.api.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.common.api.Api;
import org.example.api.domain.storemenu.business.StoreMenuBusiness;
import org.example.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/store-menu")
@RequiredArgsConstructor
public class StoreMenuApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    @GetMapping("/search")
    public Api<List<StoreMenuResponse>> search(
            @RequestParam(value = "storeId") Long storeId
    ){
        List<StoreMenuResponse> response = storeMenuBusiness.search(storeId);
        return Api.OK(response);
    }
}
