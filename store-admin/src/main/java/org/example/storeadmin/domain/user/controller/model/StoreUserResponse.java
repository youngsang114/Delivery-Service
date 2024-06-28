package org.example.storeadmin.domain.user.controller.model;

import lombok.*;
import org.example.db.storeuser.enums.StoreUserRole;
import org.example.db.storeuser.enums.StoreUserStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreUserResponse {

    private UserResponse user;
    private StoreResponse store;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse{

        private Long id;
        private String email;
        private StoreUserStatus status;
        private StoreUserRole role;
        private LocalDateTime registeredAt;
        private LocalDateTime unregisteredAt;
        private LocalDateTime lastLoginAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StoreResponse{
        private Long id;
        private String name;

    }

}
