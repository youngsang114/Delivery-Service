package org.example.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * USER의 경우 2000번대 에러코드 사용
 */
@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCodeIfs{
    INVALID_TOKEN(400,2000,"유효하지 않는 토큰"),
    EXPIRED_TOKEN(400,2001,"만료된 토큰"),
    TOKEN_EXCEPTION(400,2002,"알 수 없는 토큰 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(400,2003,"인증 헤더 토큰 없음")

    ;

    private final Integer httpStatusCode; // 상응 하는 http error code
    private final Integer errorCode; // 인터널 error code
    private final String description;
}
