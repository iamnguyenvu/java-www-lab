package com.nguyenvu.thymeleafjpashopping.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("ADMIN")
                .password(passwordEncoder.encode("123456"))
                .roles("ADMIN")
                .build();
        
        UserDetails customer = User.builder()
                .username("CUSTOMER")
                .password(passwordEncoder.encode("123456"))
                .roles("CUSTOMER")
                .build();
        
        return new InMemoryUserDetailsManager(admin, customer);
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
                        .requestMatchers("/products", "/products/*/view", "/products/search/**").permitAll()

                        // ADMIN: Toàn quyền CRUD products
                        .requestMatchers("/products/new", "/products/*/edit", "/products/delete").hasRole("ADMIN")

                        // ADMIN: Toàn quyền quản lý customers
                        .requestMatchers("/customers/**").hasRole("ADMIN")

                        // ADMIN: Xem tất cả orders
                        // CUSTOMER: Chỉ xem orders của chính mình (kiểm tra trong controller)
                        .requestMatchers("/orders/**").hasAnyRole("ADMIN", "CUSTOMER")
                        
                        // Cart: Chỉ authenticated users
                        .requestMatchers("/cart/**").authenticated()

                        // Mọi request khác cần authenticated
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied"));

        return http.build();
    }
}
