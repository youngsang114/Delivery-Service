package org.example.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * USER의 경우 1000번대 에러코드 사용
 */
@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCodeIfs{
    USER_NOT_FOUND(400,1404,"사용자를 찾을 수 없음"),

    ;

    private final Integer httpStatusCode; // 상응 하는 http error code
    private final Integer errorCode; // 인터널 error code
    private final String description;
}
