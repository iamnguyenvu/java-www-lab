package com.nguyenvu.thymeleafjpashopping.service;

import com.nguyenvu.thymeleafjpashopping.dto.CustomerDTO;
import com.nguyenvu.thymeleafjpashopping.model.Customer;
import com.nguyenvu.thymeleafjpashopping.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = Customer.builder()
                .name(customerDTO.getName())
                .customerSince(customerDTO.getCustomerSince() != null ? customerDTO.getCustomerSince() : Calendar.getInstance())
                .build();
        return convertToDTO(customerRepository.save(customer));
    }

    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getId()).orElse(null);
        if (customer == null) return null;
        
        customer.setName(customerDTO.getName());
        if (customerDTO.getCustomerSince() != null) {
            customer.setCustomerSince(customerDTO.getCustomerSince());
        }
        
        return convertToDTO(customerRepository.save(customer));
    }

    public void deleteCustomer(Integer id) {
        customerRepository.findById(id).ifPresent(customerRepository::delete);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .customerSince(customer.getCustomerSince())
                .orderCount(customer.getOrders() != null ? customer.getOrders().size() : 0)
                .build();
    }
}
