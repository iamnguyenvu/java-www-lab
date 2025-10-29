package com.nguyenvu.thymeleafjpashopping.security;

import com.nguyenvu.thymeleafjpashopping.model.Customer;
import com.nguyenvu.thymeleafjpashopping.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * Custom UserDetailsService để load users từ database Customer table
 * thay vì sử dụng InMemoryUserDetailsManager
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username: " + username));

        return User.builder()
                .username(customer.getUsername())
                .password(customer.getPassword()) // Đã được mã hóa BCrypt trong database
                .authorities(getAuthorities(customer.getRole()))
                .accountLocked(!customer.getEnabled())
                .disabled(!customer.getEnabled())
                .build();
    }

    /**
     * Convert role string thành Spring Security GrantedAuthority
     * Role trong database: "ADMIN", "CUSTOMER"
     * Spring Security cần format: "ROLE_ADMIN", "ROLE_CUSTOMER"
     */
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        // Nếu role đã có prefix ROLE_ thì giữ nguyên, không thì thêm vào
        String authorityName = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return Collections.singletonList(new SimpleGrantedAuthority(authorityName));
    }
}
