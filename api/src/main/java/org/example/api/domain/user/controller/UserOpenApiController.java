package org.example.api.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.common.api.Api;
import org.example.api.domain.user.business.UserBusiness;
import org.example.api.domain.user.controller.model.UserLoginRequest;
import org.example.api.domain.user.controller.model.UserRegisterRequest;
import org.example.api.domain.user.controller.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    // 사용자 가입 처리
    @PostMapping("/register")
    public Api<UserResponse> register(
            @Valid
            @RequestBody Api<UserRegisterRequest> request
    ){
        UserResponse response = userBusiness.register(request.getBody());
        return Api.OK(response);
    }

    // 로그인 하는 부분
    @PostMapping("/login")
    public Api<UserResponse> login(
            @Valid
            @RequestBody Api<UserLoginRequest> request
    ){
        UserResponse response = userBusiness.login(request.getBody());
        return Api.OK(response);
    }
}
