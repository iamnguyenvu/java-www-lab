package com.nguyenvu.thymeleafjpashopping.controller;

import com.nguyenvu.thymeleafjpashopping.dto.CustomerDTO;
import com.nguyenvu.thymeleafjpashopping.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final CustomerService customerService;
    
    @GetMapping({"/", "/home"})
    public String home() {
        // Redirect to products page for all users (including guests)
        return "redirect:/products";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("customerDTO", new CustomerDTO());
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute CustomerDTO customerDTO, 
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        // Check validation errors
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        // Check password match
        if (!customerDTO.getNewPassword().equals(customerDTO.getConfirmPassword())) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "register";
        }
        
        try {
            customerService.registerCustomer(customerDTO);
            redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
    
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
