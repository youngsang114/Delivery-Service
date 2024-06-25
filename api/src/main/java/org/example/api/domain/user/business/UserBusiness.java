package org.example.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.example.api.common.annotation.Business;
import org.example.api.common.error.ErrorCode;
import org.example.api.common.exception.ApiException;
import org.example.api.domain.user.controller.model.UserLoginRequest;
import org.example.api.domain.user.controller.model.UserRegisterRequest;
import org.example.api.domain.user.controller.model.UserResponse;
import org.example.api.domain.user.converter.UserConverter;
import org.example.api.domain.user.service.UserService;
import org.example.db.user.UserEntity;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;

    /**
     * 사용자에 대한 가입 처리 로직
     * 1. request -> entity
     * 2. entity -> save
     * 3. save entity -> response
     * 4. response return
     */

    public UserResponse register(UserRegisterRequest request) {

        /*UserEntity entity = userConverter.toEntity(request);
        UserEntity newEntity = userService.register(entity);
        UserResponse response = userConverter.toResponse(newEntity);
        return response;*/
        return Optional.ofNullable(request)
                .map(it -> userConverter.toEntity(it))
                .map(it -> userService.register(it))
                .map(it -> userConverter.toResponse(it))
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"request null"));
    }

    /**
     * 1. email, password를 가지고 사용자 체크
     * 2. user entity 로그인 확인
     * 3. token 생성
     * 4. token response
     */
    public UserResponse login(UserLoginRequest request) {

        UserEntity userEntity = userService.login(request.getEmail(), request.getPassword());

        // 사용자가 없으면 throw

        // TODO 토큰 생성
        return userConverter.toResponse(userEntity);

    }
}
