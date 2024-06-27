package org.example.api.domain.userorder.converter;

import lombok.AllArgsConstructor;
import org.example.api.common.annotation.Converter;
import org.example.api.common.error.ErrorCode;
import org.example.api.common.exception.ApiException;
import org.example.api.domain.user.model.User;
import org.example.api.domain.userorder.controller.model.UserOrderResponse;
import org.example.db.storemenu.StoreMenuEntity;
import org.example.db.user.UserRepository;
import org.example.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;

@Converter
@AllArgsConstructor
public class UserOrderConverter {

    private final UserRepository userRepository;


    public UserOrderEntity toEntity(
            User user,
            List<StoreMenuEntity> storeMenuEntityList
    ) {
        BigDecimal totalAmount = storeMenuEntityList.stream()
                .map(it -> it.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return UserOrderEntity.builder()
                .userEntity(userRepository.findById(user.getId()).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT)))
                .amount(totalAmount)
                .build();
    }

    public UserOrderResponse toResponse(
            UserOrderEntity userOrderEntity
    ){
        return UserOrderResponse.builder()
                .id(userOrderEntity.getId())
                .status(userOrderEntity.getStatus())
                .amount(userOrderEntity.getAmount())
                .orderedAt(userOrderEntity.getOrderedAt())
                .acceptedAt(userOrderEntity.getAcceptedAt())
                .cookingStartedAt(userOrderEntity.getCookingStartedAt())
                .deliveryStartedAt(userOrderEntity.getDeliveryStartedAt())
                .receivedAt(userOrderEntity.getReceivedAt())
                .build();
    }

}
