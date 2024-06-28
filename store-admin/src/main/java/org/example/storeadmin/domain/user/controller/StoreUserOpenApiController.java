package org.example.storeadmin.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.storeadmin.domain.user.business.StoreUserBusiness;
import org.example.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.example.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/store-user")
public class StoreUserOpenApiController {

    public final StoreUserBusiness storeUserBusiness;

    @PostMapping("")
    public StoreUserResponse register(
            @Valid
            @RequestBody StoreUserRegisterRequest request
            ){
        StoreUserResponse response = storeUserBusiness.register(request);
        return response;
    }
}
