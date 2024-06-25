package org.example.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.example.api.common.annotation.Business;
import org.example.api.common.error.ErrorCode;
import org.example.api.common.exception.ApiException;
import org.example.api.domain.token.controller.model.TokenResponse;
import org.example.api.domain.token.converter.TokenConverter;
import org.example.api.domain.token.model.TokenDto;
import org.example.api.domain.token.service.TokenService;
import org.example.db.user.UserEntity;

import javax.swing.text.html.Option;
import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {
    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    /**
     * 1. userEntity에서 userId를 추출
     * 2. aceess,refresh token 발생
     * 3. converter -> token response로 변경
     * @return
     */

    public TokenResponse issueToken(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(ue -> {
                    return ue.getId();
                })
                .map(userId ->{
                    TokenDto accessToken = tokenService.issueAccessToken(userId);
                    TokenDto refreshToken = tokenService.issueRefreshToken(userId);
                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "Token 생성시, userId를 찾을 수 없습니다"));
    }

    public Long validationAccessToken(String accessToken){
        Long userId = tokenService.validationToken(accessToken);
        return userId;
    }
}
