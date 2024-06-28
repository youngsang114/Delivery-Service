package org.example.storeadmin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity // security 활성화
public class SecurityConfig {

    private final List<String> swagger = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs"
    );
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .csrf((csrf)-> csrf.disable())
                .authorizeHttpRequests(it -> {
                    it
                            // resource 의 모든 요청 허용
                            .requestMatchers(
                                    PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            // Swagger 는 인증 없이 통과
                            .requestMatchers(
                                    swagger.toArray(new String[0])).permitAll()
                            // open-api 의 uri에 대해서는 인증 없이 통과
                            .requestMatchers("/open-api/**").permitAll()
                            // 그 외의 모든 요청은 인증 사용
                            .anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                ;
        return httpSecurity.build();
    }
}
