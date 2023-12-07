package com.ll.medium.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // 로그인 여부를 판별하기 위해 사용했던 @PreAuthorize 애너테이션을 사용하기 위해 반드시 필요하다.
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers("/**")
                                .permitAll()
                )
                .headers( // h2-console 화면 나올 수 있다.
                        headers ->
                                headers.frameOptions(
                                        frameOptions ->
                                                frameOptions.sameOrigin()
                                )
                )
                .csrf( // h2-console 에 접속할 수 있다.
                        csrf ->
                                csrf.ignoringRequestMatchers(
                                        "/h2-console/**"
                                )
                )
                .formLogin( // 스프링 시큐리티에서 구현한 로그인
                        formLogin -> formLogin
                                .loginPage("/member/login") // 로그인 설정을 담당하는 부분으로 로그인 페이지의 URL
                                .defaultSuccessUrl("/") // 로그인 성공시에 이동하는 디폴트 페이지는 루트 URL
                                .failureHandler(new CustomAuthenticationFailureHandler()) // 실패 시 DEBUG모드로 로그 확인
                )
                .logout( // 스프링 시큐리티에서 구현한 로그아웃
                        logout -> logout
                                .logoutUrl("/member/logout")
                                .logoutSuccessUrl("/") // 로그아웃이 성공하면 루트(/) 페이지로 이동
                                .invalidateHttpSession(true) // 로그아웃시, 생성된 사용자 세션도 삭제하도록 처리
                );

        return http.build();
    }

    // 패스워드 암호화
    @Bean
    PasswordEncoder passwordEncoder() {
        // BCrypt 해싱 함수(BCrypt hashing function)를 사용해서 비밀번호를 암호화
        return new BCryptPasswordEncoder();
    }
}
