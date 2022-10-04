package com.ll.exam.app10.app.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// MethodSecurity는 우리가 기존에 사용했던 SecurityConfig 설정이 적용되지 않는다.
// MethodSecurity용 설정이 따로 필요한데 이때 사용하는것이 @EnableGlobalMethodSecurity이다.
// securedEnabled, prePostEnabled, jsr250Enabled 3개의 옵션이 존재한다.
// prePostEnabled는 @PreAuthorize, @PostAuthorize 애노테이션을 사용하여 인가 처리를 하고 싶을때 사용하는 옵션이다.
// @EnableGlobalMethodSecurity(prePostEnabled = true) 는 MethodSecurityInterceptor가 컨트롤러에 들어가기 전에  작동할 수 있도록 해준다 .
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        csrf -> csrf.disable()
                ) // postman에서 업로드 할 때 csrf토큰이 없기 때문에 막히므로 개발용은 끔.
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                .antMatchers("/**")
                                .permitAll()
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/member/login") // GET
                                .loginProcessingUrl("/member/login") // POST
                )
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                );
        return http.build();
    }

    @Bean
    // Spring Security에서는 비밀번호를 안전하게 저장할 수 있도록 비밀번호의 단방향 암호화를 지원하는 PasswordEncoder 인터페이스와 구현체들을 제공한다. 
    public PasswordEncoder passwordEncoder() {
        // BcryptPasswordEncoder : BCrypt 해시 함수를 사용해 비밀번호를 암호화
        // Argon2PasswordEncoder : Argon2 해시 함수를 사용해 비밀번호를 암호화
        // Pbkdf2PasswordEncoder : PBKDF2 해시 함수를 사용해 비밀번호를 암호화
        // SCryptPasswordEncoder : SCrypt 해시 함수를 사용해 비밀번호를 암호화
        return new BCryptPasswordEncoder();
    }
}