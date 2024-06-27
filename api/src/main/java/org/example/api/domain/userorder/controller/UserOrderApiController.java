package org.example.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.api.common.annotation.UserSession;
import org.example.api.common.api.Api;
import org.example.api.domain.user.model.User;
import org.example.api.domain.userorder.busineses.UserOrderBusiness;
import org.example.api.domain.userorder.controller.model.UserOrderRequest;
import org.example.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-order")
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;

    // 사용자 주문
    @PostMapping("")
    private Api<UserOrderResponse> userOrder(
            @RequestBody Api<UserOrderRequest> request,
            @Parameter(hidden = true)
            @UserSession User user
            )
    {
        UserOrderResponse response = userOrderBusiness.userOrder(user, request.getBody());
        return Api.OK(response);
    }
}
