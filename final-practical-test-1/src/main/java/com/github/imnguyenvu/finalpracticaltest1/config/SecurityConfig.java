package com.github.imnguyenvu.finalpracticaltest1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails employee = User.builder()
                .username("employee")
                .password(passwordEncoder.encode("123"))
                .roles("EMPLOYEE")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("123"))
                .roles("ADMIN", "EMPLOYEE")
                .build();

        return new InMemoryUserDetailsManager(employee, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/login", "/logout").permitAll()
                // Employee và Admin đều có thể xem danh sách và chi tiết
                .requestMatchers("/departments", "/departments/{id}").hasAnyRole("EMPLOYEE", "ADMIN")
                .requestMatchers("/employees", "/employees/{id}").hasAnyRole("EMPLOYEE", "ADMIN")
                // Chỉ Admin mới được thêm/sửa/xóa
                .requestMatchers("/departments/new", "/departments/*/edit", "/departments/*/delete").hasRole("ADMIN")
                .requestMatchers("/employees/new", "/employees/*/edit", "/employees/*/delete").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/departments", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
