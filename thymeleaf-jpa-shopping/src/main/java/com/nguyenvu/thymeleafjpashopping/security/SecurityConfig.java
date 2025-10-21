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
        
        UserDetails user = User.builder()
                .username("USER")
                .password(passwordEncoder.encode("123456"))
                .roles("USER")
                .build();
        
        UserDetails customer = User.builder()
                .username("CUSTOMER")
                .password(passwordEncoder.encode("123456"))
                .roles("CUSTOMER")
                .build();
        
        return new InMemoryUserDetailsManager(admin, user, customer);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Cho phép truy cập CSS, JS, images
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // Trang login
                        .requestMatchers("/login").permitAll()
                        // Home page - tất cả authenticated users
                        .requestMatchers("/").authenticated()
                        
                        // PRODUCTS - tất cả có thể xem
                        .requestMatchers("/products", "/products/{id}", "/products/search").authenticated()
                        // PRODUCTS CRUD - chỉ ADMIN
                        .requestMatchers("/products/new", "/products/edit/**", "/products/delete").hasRole("ADMIN")
                        
                        // ORDERS - ADMIN và USER có thể xem, CUSTOMER không
                        .requestMatchers("/orders", "/orders/{id}").hasAnyRole("ADMIN", "USER")
                        // ORDERS CRUD - chỉ ADMIN
                        .requestMatchers("/orders/new", "/orders/edit/**", "/orders/delete").hasRole("ADMIN")
                        
                        // CUSTOMERS - chỉ ADMIN
                        .requestMatchers("/customers", "/customers/{id}", "/customers/new", 
                                       "/customers/edit/**", "/customers/delete").hasRole("ADMIN")
                        
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
