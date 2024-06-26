package org.example.api.domain.store.controller.model;

import lombok.*;
import org.example.db.store.enums.StoreCategory;
import org.example.db.store.enums.StoreStatus;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponse {

    private Long id;
    private String name;
    private String address;
    private StoreStatus status;
    private StoreCategory category;
    private double star;
    private String thumbnailUrl;
    private BigDecimal minimumAmount;
    private BigDecimal minimumDeliveryAmount;
    private String phoneNumber;
}
