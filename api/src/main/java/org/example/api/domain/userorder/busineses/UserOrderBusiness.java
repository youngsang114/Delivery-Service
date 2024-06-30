package org.example.api.domain.userorder.busineses;

import lombok.RequiredArgsConstructor;
import org.example.api.common.annotation.Business;
import org.example.api.domain.store.converter.StoreConverter;
import org.example.api.domain.store.service.StoreService;
import org.example.api.domain.storemenu.converter.StoreMenuConverter;
import org.example.api.domain.storemenu.service.StoreMenuService;
import org.example.api.domain.user.model.User;
import org.example.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.example.api.domain.userorder.controller.model.UserOrderRequest;
import org.example.api.domain.userorder.controller.model.UserOrderResponse;
import org.example.api.domain.userorder.converter.UserOrderConverter;
import org.example.api.domain.userorder.producer.UserOrderProducer;
import org.example.api.domain.userorder.service.UserOrderService;
import org.example.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.example.api.domain.userordermenu.service.UserOrderMenuService;
import org.example.db.store.StoreEntity;
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
    private final StoreMenuConverter storeMenuConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final StoreConverter storeConverter;
    private final UserOrderProducer userOrderProducer;

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

        // 비동기로 가맹점에 주문 알리기
        userOrderProducer.sendOrder(newUserOrderEntity);

        // response 응답!
        return userOrderConverter.toResponse(newUserOrderEntity);
    }

    public List<UserOrderDetailResponse> current(User user) {
        List<UserOrderEntity> userOrderEntityList = userOrderService.current(user.getId());

        // 주문 1건씩 처리
        List<UserOrderDetailResponse> uerOrderDetailResponseList = userOrderEntityList.stream()
                .map(it -> {


                    // 사용자가 주문한 매뉴
                    List<UserOrderMenuEntity> userOrderMenuList = userOrderMenuService.getUserOrderMenu(it.getId());

                    List<StoreMenuEntity> storeMenuEntityList = userOrderMenuList.stream()
                            .map(userOrderMenuEntity -> {
                                StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuEntity().getId());

                                return storeMenuEntity;
                            })
                            .collect(Collectors.toList());

                    // 사용자가 주문한 스토어 TODO 리팩터링 필요
                    StoreEntity storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreEntity().getId());

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeMenuResposeList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();
                }).collect(Collectors.toList());

        return uerOrderDetailResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {

        List<UserOrderEntity> userOrderEntityList = userOrderService.history(user.getId());

        // 주문 1건씩 처리
        List<UserOrderDetailResponse> uerOrderDetailResponseList = userOrderEntityList.stream()
                .map(it -> {


                    // 사용자가 주문한 매뉴
                    List<UserOrderMenuEntity> userOrderMenuList = userOrderMenuService.getUserOrderMenu(it.getId());

                    List<StoreMenuEntity> storeMenuEntityList = userOrderMenuList.stream()
                            .map(userOrderMenuEntity -> {
                                StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuEntity().getId());

                                return storeMenuEntity;
                            })
                            .collect(Collectors.toList());

                    // 사용자가 주문한 스토어 TODO 리팩터링 필요
                    StoreEntity storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreEntity().getId());

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeMenuResposeList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();
                }).collect(Collectors.toList());

        return uerOrderDetailResponseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {

        UserOrderEntity userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user.getId());

        // 사용자가 주문한 매뉴
        List<UserOrderMenuEntity> userOrderMenuList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

        List<StoreMenuEntity> storeMenuEntityList = userOrderMenuList.stream()
                .map(userOrderMenuEntity -> {
                    StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuEntity().getId());

                    return storeMenuEntity;
                })
                .collect(Collectors.toList());

        // 사용자가 주문한 스토어 TODO 리팩터링 필요
        StoreEntity storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreEntity().getId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResposeList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }
}
