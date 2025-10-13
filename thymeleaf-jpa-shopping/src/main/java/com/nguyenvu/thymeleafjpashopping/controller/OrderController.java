package com.nguyenvu.thymeleafjpashopping.controller;

import com.nguyenvu.thymeleafjpashopping.dto.OrderDTO;
import com.nguyenvu.thymeleafjpashopping.service.CustomerService;
import com.nguyenvu.thymeleafjpashopping.service.OrderService;
import com.nguyenvu.thymeleafjpashopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@Controller
@RequestMapping({"/orders"})
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    // private final CustomerService customerService;
    // private final ProductService productService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/orders";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Integer id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        return "order/order";
    }

    // Sai logic nghiep vu 
    // @GetMapping("/new")
    // public String showOrderForm(Model model) {
    //     model.addAttribute("order", new OrderDTO());
    //     model.addAttribute("customers", customerService.getAllCustomers());
    //     model.addAttribute("products", productService.getAllProducts());
    //     return "order/order-form";
    // }

    // @PostMapping
    // public String createOrder(@ModelAttribute OrderDTO order) {
    //     orderService.createOrder(order);
    //     return "redirect:/orders";
    // }

//    Not correct business (Nghiep vu thuc te khong cho phep update hay delete order)
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Integer id, Model model) {
//        model.addAttribute("order", orderService.getOrderById(id));
//        return "order-form";
//    }
//
//    @PostMapping
//    public String updateOrder(@ModelAttribute OrderDTO order) {
//        orderService.
//    }

    @GetMapping("/customer/{customerId}")
    public String listOrdersByCustomerId(@PathVariable Integer customerId, Model model) {
        model.addAttribute("orders", orderService.getOrdersByCustomerId(customerId));
        return "order/orders";
    }

    @GetMapping("/range")
    public String listOrdersByDateRange(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar startDate,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar endDate,
                                        Model model) {
        model.addAttribute("orders", orderService.getOrdersByDateRange(startDate, endDate));
        return "order/orders";
    }

    @GetMapping("/after")
    public String listOrdersByDateGreaterThan(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar date, Model model) {
        model.addAttribute("orders", orderService.getOrdersByDateGreaterThan(date));
        return "order/orders";
    }

    @GetMapping("/before")
    public String viewOrdersByDateLessThan(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar date, Model model) {
        model.addAttribute("orders", orderService.getOrdersByDateLessThan(date));
        return "order/orders";
    }

    @GetMapping("/customers/{customerId}")
    public String viewOrdersByCustomerIdAndDateBetween(@PathVariable Integer customerId, Calendar startDate, Calendar endDate, Model model) {
        model.addAttribute("orders", orderService.getOrdersByCustomerIdAndDateBetween(customerId, startDate, endDate));
        return "order/orders";
    }

}
