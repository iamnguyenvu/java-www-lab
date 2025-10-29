package com.nguyenvu.thymeleafjpashopping.service;

import com.nguyenvu.thymeleafjpashopping.dto.CustomerDTO;
import com.nguyenvu.thymeleafjpashopping.model.Customer;
import com.nguyenvu.thymeleafjpashopping.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        return convertToDTO(customer);
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found with username: " + username));
        return convertToDTO(customer);
    }

    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        // Validate username and email uniqueness
        if (customerRepository.existsByUsername(customerDTO.getUsername())) {
            throw new RuntimeException("Username already exists: " + customerDTO.getUsername());
        }
        if (customerDTO.getEmail() != null && customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + customerDTO.getEmail());
        }
        
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(passwordEncoder.encode(customerDTO.getNewPassword()));
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setCustomerSince(new Date());
        customer.setRole("CUSTOMER");
        customer.setEnabled(true);
        
        return convertToDTO(customerRepository.save(customer));
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(passwordEncoder.encode(customerDTO.getNewPassword()));
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setCustomerSince(customerDTO.getCustomerSince() != null ? customerDTO.getCustomerSince() : new Date());
        customer.setRole(customerDTO.getRole() != null ? customerDTO.getRole() : "CUSTOMER");
        customer.setEnabled(customerDTO.getEnabled() != null ? customerDTO.getEnabled() : true);
        
        return convertToDTO(customerRepository.save(customer));
    }

    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        
        // Only admin can update role and enabled status
        if (customerDTO.getRole() != null) {
            customer.setRole(customerDTO.getRole());
        }
        if (customerDTO.getEnabled() != null) {
            customer.setEnabled(customerDTO.getEnabled());
        }
        
        // Update password if provided
        if (customerDTO.getNewPassword() != null && !customerDTO.getNewPassword().isEmpty()) {
            customer.setPassword(passwordEncoder.encode(customerDTO.getNewPassword()));
        }
        
        return convertToDTO(customerRepository.save(customer));
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        customerRepository.delete(customer);
    }

    public void changePassword(Long customerId, String oldPassword, String newPassword) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        
        if (!passwordEncoder.matches(oldPassword, customer.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        return CustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .username(customer.getUsername())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .customerSince(customer.getCustomerSince())
                .role(customer.getRole())
                .enabled(customer.getEnabled())
                .orderCount(customer.getOrders() != null ? customer.getOrders().size() : 0)
                .commentCount(customer.getComments() != null ? customer.getComments().size() : 0)
                .build();
    }
}
