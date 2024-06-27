package org.example.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.api.common.annotation.UserSession;
import org.example.api.common.api.Api;
import org.example.api.domain.user.model.User;
import org.example.api.domain.userorder.busineses.UserOrderBusiness;
import org.example.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.example.api.domain.userorder.controller.model.UserOrderRequest;
import org.example.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 현재 진행 중인 주문건
    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> current(
            @Parameter(hidden = true)
            @UserSession User user
    ){
        List<UserOrderDetailResponse> response = userOrderBusiness.current(user);
        return Api.OK(response);
    }

    // 과거 주문 내역
    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> history(
            @Parameter(hidden = true)
            @UserSession User user
    ){
        List<UserOrderDetailResponse> history = userOrderBusiness.history(user);
        return Api.OK(history);
    }

    // 주문 1건에 대한 내역
    @GetMapping("/id/{orderId}")
    public Api<UserOrderDetailResponse> read(
            @Parameter(hidden = true)
            @UserSession User user,

            @PathVariable(name = "orderId") Long orderId
    ){
        UserOrderDetailResponse response = userOrderBusiness.read(user, orderId);
        return Api.OK(response);
    }

}
