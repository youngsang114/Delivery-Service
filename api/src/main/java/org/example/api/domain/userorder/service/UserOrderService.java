package org.example.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.example.api.common.error.ErrorCode;
import org.example.api.common.exception.ApiException;
import org.example.db.userorder.UserOrderEntity;
import org.example.db.userorder.UserOrderRepository;
import org.example.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    /**
     * 주문 id, 주문 등록 상태,유저 id를 입력받아서 -> UserOrderEntity를 반환하는 메서드
     */
    public UserOrderEntity getUserOrderWithThrow(
        Long id,
        Long userId
    ){
        return userOrderRepository.findAllByIdAndStatusAndUserEntity_Id(id, UserOrderStatus.REGISTERED, userId)
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

    /**
     * 주문 등록 상태,유저 id를 입력받아서 -> 해당 유저의 모든 주문들을 반환받는 메서드
     */
    public List<UserOrderEntity> getUserOrderList(Long userId){
        return userOrderRepository.findAllByUserEntity_IdAndStatusOrderByIdDesc(userId, UserOrderStatus.REGISTERED);
    }
    public List<UserOrderEntity> getUserOrderList(Long userId,List<UserOrderStatus> statusList){
        return userOrderRepository.findAllByUserEntity_IdAndStatusInOrderByIdDesc(userId, statusList);
    }

    // 현재 진행중인 내역
    public List<UserOrderEntity> current(Long userId){
        return getUserOrderList(userId,
                List.of(
                        UserOrderStatus.ORDER,
                        UserOrderStatus.COOKING,
                        UserOrderStatus.DELIVERY,
                        UserOrderStatus.ACCEPT

                )
        );
    }
    // 과거에 주문한 내역
    public List<UserOrderEntity> history(Long userId){
        return getUserOrderList(userId,
                List.of(
                        UserOrderStatus.RECEIVE
                )
        );
    }

    // 주문 하기(create)
    public UserOrderEntity order(
            UserOrderEntity userOrderEntity
    ){
        return Optional.ofNullable(userOrderEntity)
                .map(it -> {
                    it.setStatus(UserOrderStatus.ORDER);
                    it.setOrderedAt(LocalDateTime.now());
                    return userOrderRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "주문시 UserOrderEntity 가 Null"));
    }

    // 상태 변경 메서드
    public UserOrderEntity setStatus(UserOrderEntity userOrderEntity, UserOrderStatus status){
        userOrderEntity.setStatus(status);
        return userOrderRepository.save(userOrderEntity);
    }

    // 주문 확인
    public UserOrderEntity accept(UserOrderEntity userOrderEntity){
        userOrderEntity.setAcceptedAt(LocalDateTime.now());
        return setStatus(userOrderEntity,UserOrderStatus.ACCEPT);
    }

    // 조리 시작
    public UserOrderEntity cooking(UserOrderEntity userOrderEntity){
        userOrderEntity.setCookingStartedAt(LocalDateTime.now());
        return setStatus(userOrderEntity,UserOrderStatus.COOKING);
    }

    // 배달 시작
    public UserOrderEntity delivery(UserOrderEntity userOrderEntity){
        userOrderEntity.setDeliveryStartedAt(LocalDateTime.now());
        return setStatus(userOrderEntity,UserOrderStatus.DELIVERY);
    }

    // 배달 완료
    public UserOrderEntity receive(UserOrderEntity userOrderEntity){
        userOrderEntity.setReceivedAt(LocalDateTime.now());
        return setStatus(userOrderEntity,UserOrderStatus.RECEIVE);
    }
}

