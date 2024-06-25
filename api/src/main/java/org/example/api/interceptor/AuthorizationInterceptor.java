package org.example.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.common.error.ErrorCode;
import org.example.api.common.error.TokenErrorCode;
import org.example.api.common.exception.ApiException;
import org.example.api.domain.token.business.TokenBusiness;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        // web, chrome 의 경우 GET, POST OPTIONS -> 해당 메서드를 지원하는지 체크하는 옵션 [통과]
        if (HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }
        // js, html, png resources를 요청하는 경우 = pass
        if (handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        // TODO header 검증
        String accessToken = request.getHeader("authorization-token");
        if (accessToken == null){
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        Long userId = tokenBusiness.validationAccessToken(accessToken);

        if (userId != null){
            // 로컬 쓰레드(한가지 요청에 대해서 global하게 저장 가능)
            RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
            requestContext.setAttribute("userId",userId, RequestAttributes.SCOPE_REQUEST); // 범위는 이번 요청 동안만(scope_request)
            return true;
        }
        throw new ApiException(ErrorCode.BAD_REQUEST, "인증 실패");
    }
}
