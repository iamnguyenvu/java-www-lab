package com.nguyenvu.thymeleafjpashopping.controller;

import com.nguyenvu.thymeleafjpashopping.dto.OrderDTO;
import com.nguyenvu.thymeleafjpashopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/orders";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        return "order/order";
    }

    @GetMapping("/customer/{customerId}")
    public String listOrdersByCustomerId(@PathVariable Long customerId, Model model) {
        model.addAttribute("orders", orderService.getOrdersByCustomerId(customerId));
        return "order/orders";
    }

    @GetMapping("/status/{status}")
    public String listOrdersByStatus(@PathVariable String status, Model model) {
        model.addAttribute("orders", orderService.getOrdersByStatus(status));
        return "order/orders";
    }

    @GetMapping("/range")
    public String listOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            Model model) {
        model.addAttribute("orders", orderService.getOrdersByDateRange(startDate, endDate));
        return "order/orders";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);
        return "redirect:/orders";
    }

    @PostMapping("/{id}/updateStatus")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/orders/" + id;
    }

    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return "redirect:/orders/" + id;
    }
}
