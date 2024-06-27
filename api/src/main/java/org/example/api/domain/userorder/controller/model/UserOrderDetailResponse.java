package org.example.api.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.api.domain.store.controller.model.StoreResponse;
import org.example.api.domain.storemenu.controller.model.StoreMenuResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderDetailResponse {

    private UserOrderResponse userOrderResponse; // 주문에 대한 응답
    private StoreResponse storeResponse; // 가게에 대한 응답
    private List<StoreMenuResponse> storeMenuResposeList; // 어떠 매뉴를 응답하는지

}
