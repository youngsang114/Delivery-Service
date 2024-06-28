package org.example.storeadmin.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.db.storeuser.StoreUserEntity;
import org.example.db.storeuser.StoreUserRepository;
import org.example.db.storeuser.enums.StoreUserStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreUserService {

    private final StoreUserRepository storeUserRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Bean으로 등록한 PasswordEncoder을 이용해 -> 암호화 저장
     * @param storeUserEntity
     * @return
     */
    public StoreUserEntity register(
            StoreUserEntity storeUserEntity
    ){
        storeUserEntity.setStatus(StoreUserStatus.REGISTERED);
        storeUserEntity.setPassword(passwordEncoder.encode(storeUserEntity.getPassword()));
        storeUserEntity.setRegisteredAt(LocalDateTime.now());

        return storeUserRepository.save(storeUserEntity);
    }

    public Optional<StoreUserEntity> getRegisterUser(String email){
        return storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email,StoreUserStatus.REGISTERED);
    }
}
