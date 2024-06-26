package org.example.api.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.common.api.Api;
import org.example.api.domain.store.business.StoreBusiness;
import org.example.api.domain.store.controller.model.StoreResponse;
import org.example.db.store.enums.StoreCategory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreApiController {

    private final StoreBusiness storeBusiness;

    @GetMapping("/search")
    public Api<List<StoreResponse>> search(
            @RequestParam(required = false)
            StoreCategory storeCategory
    ){
        List<StoreResponse> storeResponses = storeBusiness.searchCategory(storeCategory);
        return Api.OK(storeResponses);
    }
}
