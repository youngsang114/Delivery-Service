package org.example.db.store;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.db.BaseEntity;
import org.example.db.store.enums.StoreCategory;
import org.example.db.store.enums.StoreStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "store")
public class StoreEntity extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private StoreStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private StoreCategory category;

    private double star;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    @Column(precision = 11,scale = 4,nullable = false)
    private BigDecimal minimumAmount;

    @Column(precision = 11,scale = 4,nullable = false)
    private BigDecimal minimumDeliveryAmount;

    @Column(length = 20)
    private String phoneNumber;
}
