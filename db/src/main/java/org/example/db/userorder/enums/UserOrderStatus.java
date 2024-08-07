package org.example.db.userorder.enums;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;

@AllArgsConstructor
public enum UserOrderStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    // 주문
    ORDER("주문"),
    // 확인
    ACCEPT("확인"),
    // 요리중
    COOKING("요리중"),
    // 배달 중
    DELIVERY("배달중"),
    // 완료
    RECEIVE("완료")
    ;

    private String descriptions;
}
