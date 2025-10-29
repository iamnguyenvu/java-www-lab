package com.nguyenvu.thymeleafjpashopping.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final CustomUserDetailsService customUserDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Static resources - cho phép tất cả
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        
                        // Public pages
                        .requestMatchers("/", "/login", "/register", "/access-denied").permitAll()
                        
                        // Guest: Xem danh sách và chi tiết sản phẩm, tìm kiếm
                        .requestMatchers("/products", "/products/*", "/products/search/**").permitAll()

                        // ADMIN only: CRUD products
                        .requestMatchers("/products/new", "/products/edit/**", "/products/delete/**").hasRole("ADMIN")

                        // ADMIN only: Quản lý customers
                        .requestMatchers("/customers/**").hasRole("ADMIN")

                        // Orders: ADMIN xem tất cả, CUSTOMER xem của mình
                        .requestMatchers("/orders/**").hasAnyRole("ADMIN", "CUSTOMER")
                        
                        // Cart: Authenticated users only
                        .requestMatchers("/cart/**").authenticated()

                        // Mọi request khác cần authenticated
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied"));

        return http.build();
    }
}
