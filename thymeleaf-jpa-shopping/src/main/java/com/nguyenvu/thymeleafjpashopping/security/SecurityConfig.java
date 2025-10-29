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
                        // Cho phép truy cập CSS, JS, images
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/access-denied", "/login").permitAll()

                        // PRODUCTS CRUD - chỉ ADMIN
                        .requestMatchers("/products/new", "/products/edit/**", "/products/delete/**").hasRole("ADMIN")

                        // ORDERS CRUD - chỉ ADMIN
                        .requestMatchers("/customers/**").hasRole("ADMIN")

                        .requestMatchers("/orders/**").hasAnyRole("ADMIN", "CUSTOMER")

                        .requestMatchers("/", "/products", "/products/**", "/products/search/**").permitAll()

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
