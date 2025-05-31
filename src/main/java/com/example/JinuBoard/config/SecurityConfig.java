package com.example.JinuBoard.config;

import com.example.JinuBoard.repository.UsersRepository;
import com.example.JinuBoard.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 개발 단계에서는 csrf 비활성화
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/login", "/register", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // 접근 허용
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .formLogin((form) -> form
                        .loginPage("/login") // 커스텀 로그인 페이지 경로
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 리다이렉트 경로
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                );


        return http.build();
    }

    // 인증을 위한 사용자 정보 제공 -> UserDetailsService
    @Bean
    public UserDetailsService userDetailsService(UsersRepository usersRepository) {
        return new CustomUserDetailsService(usersRepository);
    }

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
