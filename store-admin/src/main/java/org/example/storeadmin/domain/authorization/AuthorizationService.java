package org.example.storeadmin.domain.authorization;

import lombok.RequiredArgsConstructor;
import org.example.db.store.StoreEntity;
import org.example.db.store.StoreRepository;
import org.example.db.store.enums.StoreStatus;
import org.example.db.storeuser.StoreUserEntity;
import org.example.storeadmin.domain.authorization.model.UserSession;
import org.example.storeadmin.domain.user.service.StoreUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 창의 username이 이쪽으로 들어온다
        // 정상적인 user가 있으면 -> return ture

        Optional<StoreUserEntity> storeUserEntity = storeUserService.getRegisterUser(username);
        Optional<StoreEntity> storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeUserEntity.get().getId(), StoreStatus.REGISTERED);

        return storeUserEntity.map(it ->{

                    UserSession userSession = UserSession.builder()
                            .userId(it.getId())
                            .email(it.getEmail())
                            .status(it.getStatus())
                            .role(it.getRole())
                            .registeredAt(it.getRegisteredAt())
                            .unregisteredAt(it.getUnregisteredAt())
                            .lastLoginAt(it.getLastLoginAt())
                            .storeId(storeEntity.get().getId())
                            .storeName(storeEntity.get().getName())
                            .build();

                    return userSession;
        })
                .orElseThrow(() -> new UsernameNotFoundException(username));

    }
}
