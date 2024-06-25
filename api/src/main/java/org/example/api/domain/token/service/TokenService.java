package org.example.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.example.api.common.error.ErrorCode;
import org.example.api.common.exception.ApiException;
import org.example.api.domain.token.ifs.TokenHelperIfs;
import org.example.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * token 에 대한 도메인 지식
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public TokenDto issueAccessToken(Long userId){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }
    public TokenDto issueRefreshToken(Long userId){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public Long validationToken(String token){
        Map<String, Object> map = tokenHelperIfs.validationTokenWithThrow(token);

        Object userId = map.get("userId");

        Objects.requireNonNull(userId, ()-> {
            throw new ApiException(ErrorCode.NULL_POINT, "token 검증 중 userId를 찾을 수 없습니다");
        });

        return Long.parseLong(userId.toString());
    }
}
