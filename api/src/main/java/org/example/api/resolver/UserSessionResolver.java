package org.example.api.resolver;

import lombok.RequiredArgsConstructor;
import org.example.api.common.annotation.UserSession;
import org.example.api.domain.user.model.User;
import org.example.api.domain.user.service.UserService;
import org.example.db.user.UserEntity;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크

        // 1. 어노테이션이 있는지
        boolean annotation = parameter.hasParameterAnnotation(UserSession.class);
        // 2. 파라미터 타입
        boolean parameterType = parameter.getParameterType().equals(User.class);

        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // support parameter에서 true 반환시 resolveArgument 실행

        //request context holder에서 찾아오기
        RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();

        Object userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        UserEntity userEntity = userService.getUserWithThrow(Long.parseLong(userId.toString()));

        // 사용자 정보 세팅
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .status(userEntity.getStatus())
                .password(userEntity.getPassword())
                .address(userEntity.getAddress())
                .registeredAt(userEntity.getRegisteredAt())
                .unregisteredAt(userEntity.getUnregisteredAt())
                .lastLoginAt(userEntity.getLastLoginAt())
                .build();

    }
}
