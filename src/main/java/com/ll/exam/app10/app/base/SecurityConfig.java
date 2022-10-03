package com.ll.exam.app10.app.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
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