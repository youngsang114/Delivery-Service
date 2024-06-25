package org.example.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.api.common.error.ErrorCode;
import org.example.api.common.error.UserErrorCode;
import org.example.api.common.exception.ApiException;
import org.example.db.user.UserEntity;
import org.example.db.user.UserRepository;
import org.example.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User 도메인 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity register(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it ->{
                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity Null"));
    }

    public UserEntity login(
        String email,
        String password
    ){
        UserEntity entity = getUserWithThrow(email, password);
        return entity;
    }

    public UserEntity getUserWithThrow(
            String email,
            String password
    ){
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
                email,
                password,
                UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "email,password로 user을 찾을 수 없습니다"));
    }

}