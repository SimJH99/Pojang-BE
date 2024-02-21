package com.sns.pojang.global.config.security;

import com.sns.pojang.global.config.security.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 메소드 호출 이전 이후에 권한을 확인할 수 있다.
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                // xss와 csrf의 차이 정리 필요
                // 쿠키와 세션을 사용하는 MVC패턴에서 csrf 공격에 취약하나, REST API는 덜 취약하여 비활성화
                .csrf().disable()
                .cors() // CORS 활성화(특정 도메인만 접속할 수 있도록 허용)
                .and()
                .httpBasic().disable()
                .authorizeRequests()
                // Authentication 객체 없어도 실행되는 URL 패턴
                .antMatchers(
                        "/api/members/sign-up/*",
                        "/api/members/login",
                        "/api/members/send-sms",
                        "/api/members/confirm-sms",
                        "/api/mail/*",
                        "/api/stores",
                        "/api/stores/*/menus/*/image",
                        "/api/stores/*/menus",
                        "/api/stores/*/menus/*",
                        "/api/stores/*/image",
                        "/api/stores/*/reviews",
                        "/api/orders/*/reviews/*",
                        "/api/stores/*/details",
                        "/api/stores/*/rating",
                        "/api/stores/*/favorites"
                        )
                .permitAll()
                .anyRequest().authenticated()
                .and()
                // 세션을 사용하지 않는 설정 추가
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Custom Filter 추가 (UsernamePasswordAuthenticationFilter 실행 전에 jwtAuthFilter를 실행)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
