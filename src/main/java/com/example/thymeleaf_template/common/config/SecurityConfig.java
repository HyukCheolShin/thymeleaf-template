package com.example.thymeleaf_template.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // 필요하면 CSRF는 상황에 맞게 설정 (지금은 데모용으로 disable)
            .csrf(csrf -> csrf.disable())
            // 1) URL별 권한 설정
            .authorizeHttpRequests(auth -> auth
                // 정적 리소스, 로그인 페이지는 누구나 접근 허용
                .requestMatchers(
                    "/login.do",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()
                // 나머지는 인증 필요
                .anyRequest().authenticated()
            )
            // 2) 기본 로그인 페이지 대신, 내가 만든 /login 페이지 사용
            .formLogin(form -> form
                .loginPage("/login.do")          // GET /login -> 내가 만든 컨트롤러/뷰
                .loginProcessingUrl("/login.do") // POST /login -> 시큐리티가 로그인 처리
                .defaultSuccessUrl("/home.do", true) // 로그인 성공 후 이동 URL
                .failureUrl("/login.do?error")   // 실패 시 URL
                .permitAll()
            )
            // 3) 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/logout.do")
                .logoutSuccessUrl("/login.do?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }

    /**
     * 데모용 사용자 계정 (메모리 기반).
     * 나중에 DB/MyBatis 연동하면 제거하고 UserDetailsService 구현체로 교체하면 됨.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
