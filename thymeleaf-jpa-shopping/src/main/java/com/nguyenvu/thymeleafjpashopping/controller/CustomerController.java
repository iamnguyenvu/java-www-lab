package com.nguyenvu.thymeleafjpashopping.controller;

import com.nguyenvu.thymeleafjpashopping.dto.CustomerDTO;
import com.nguyenvu.thymeleafjpashopping.model.Customer;
import com.nguyenvu.thymeleafjpashopping.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer/customers";
    }

    @GetMapping("/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customer/customer";
    }

    @GetMapping("/new")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/customer-form";
    }

    @PostMapping("/new")
    public String createCustomer(@ModelAttribute CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customer/customer-form";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute CustomerDTO customerDTO) {
        customerDTO.setCustomerId(id);
        customerService.updateCustomer(id);
        return "redirect:/customers";
    }

    @PostMapping("/delete")
    public String deleteCustomer(@RequestParam Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
