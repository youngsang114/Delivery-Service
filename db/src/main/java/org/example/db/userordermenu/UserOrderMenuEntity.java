package org.example.db.userordermenu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.db.BaseEntity;
import org.example.db.storemenu.StoreMenuEntity;
import org.example.db.userordermenu.enums.UserOrderMenuStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "user_order_menu")
@Entity
public class UserOrderMenuEntity extends BaseEntity {

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "user_order_id")
    private Long userOrderId; // 1:N

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "store_menu_id")
    private StoreMenuEntity storeMenuEntity; // 1:N

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private UserOrderMenuStatus status;
}
