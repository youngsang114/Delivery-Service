package org.example.api.domain.userorder.busineses;

import lombok.RequiredArgsConstructor;
import org.example.api.common.annotation.Business;
import org.example.api.domain.storemenu.service.StoreMenuService;
import org.example.api.domain.user.model.User;
import org.example.api.domain.userorder.controller.model.UserOrderRequest;
import org.example.api.domain.userorder.controller.model.UserOrderResponse;
import org.example.api.domain.userorder.converter.UserOrderConverter;
import org.example.api.domain.userorder.service.UserOrderService;
import org.example.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.example.api.domain.userordermenu.service.UserOrderMenuService;
import org.example.db.storemenu.StoreMenuEntity;
import org.example.db.userorder.UserOrderEntity;
import org.example.db.userordermenu.UserOrderMenuEntity;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    // 1. 사용자, 매뉴 id
    // 2. userOrder 생성
    // 3, userOrderMenu 생성
    // 4. 응답 생성
    public UserOrderResponse userOrder(User user, UserOrderRequest body) {
        // 로그인 된 user -> Resolver을 통해 가져온다
        // 매뉴를 가져온다

        List<StoreMenuEntity> storeMenuEntityList = body.getStoreMenuIdList().stream()
                .map(it -> storeMenuService.getStoreMenuWithThrow(it))
                .collect(Collectors.toList());

        UserOrderEntity userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

        // 주문
        UserOrderEntity newUserOrderEntity = userOrderService.order(userOrderEntity);

        // 매핑
        List<UserOrderMenuEntity> userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> {
                    // menu + user order
                    UserOrderMenuEntity userOrderMenuEntity = userOrderMenuConverter.toEntity(newUserOrderEntity, it);
                    return userOrderMenuEntity;
                }).collect(Collectors.toList());

        // 주문 내역 기록 남기기
        userOrderMenuEntityList.forEach(it ->{
            userOrderMenuService.order(it);
        });

        // response 응답!
        return userOrderConverter.toResponse(newUserOrderEntity);
    }
}
