package com.nguyenvu.thymeleafjpashopping.controller;

import com.nguyenvu.thymeleafjpashopping.dto.OrderDTO;
import com.nguyenvu.thymeleafjpashopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    @GetMapping
    public String listOrders(Model model, Authentication authentication) {
        List<OrderDTO> orders;
        
        // ADMIN: Xem tất cả orders
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            orders = orderService.getAllOrders();
        } 
        // CUSTOMER: Chỉ xem orders của chính mình
        else {
            String username = authentication.getName();
            orders = orderService.getOrdersByCustomerUsername(username);
        }
        
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model, Authentication authentication) {
        OrderDTO order = orderService.getOrderById(id);
        
        // CUSTOMER chỉ xem được order của chính mình
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            String username = authentication.getName();
            if (!order.getCustomerUsername().equals(username)) {
                return "redirect:/access-denied";
            }
        }
        
        model.addAttribute("order", order);
        return "order/order";
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String listOrdersByCustomerId(@PathVariable Long customerId, Model model) {
        model.addAttribute("orders", orderService.getOrdersByCustomerId(customerId));
        return "order/orders";
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public String listOrdersByStatus(@PathVariable String status, Model model) {
        model.addAttribute("orders", orderService.getOrdersByStatus(status));
        return "order/orders";
    }

    @GetMapping("/range")
    @PreAuthorize("hasRole('ADMIN')")
    public String listOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            Model model) {
        model.addAttribute("orders", orderService.getOrdersByDateRange(startDate, endDate));
        return "order/orders";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute OrderDTO orderDTO, Authentication authentication) {
        // CUSTOMER tự động gán username của họ
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            orderDTO.setCustomerUsername(authentication.getName());
        }
        
        orderService.createOrder(orderDTO);
        return "redirect:/orders";
    }

    @PostMapping("/{id}/updateStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/orders/" + id;
    }

    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id, Authentication authentication) {
        OrderDTO order = orderService.getOrderById(id);
        
        // CUSTOMER chỉ cancel được order của chính mình
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            String username = authentication.getName();
            if (!order.getCustomerUsername().equals(username)) {
                return "redirect:/access-denied";
            }
        }
        
        orderService.cancelOrder(id);
        return "redirect:/orders/" + id;
    }
}
