package org.example.db.storeuser;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.db.BaseEntity;
import org.example.db.store.StoreEntity;
import org.example.db.storeuser.enums.StoreUserRole;
import org.example.db.storeuser.enums.StoreUserStatus;
import org.springframework.data.web.JsonPath;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "store_user")
public class StoreUserEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity storeEntity;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private StoreUserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreUserRole role;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime lastLoginAt;
}
