package com.nguyenvu.thymeleafjpashopping.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("cartItemCount")
    public Integer getCartItemCount(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, CartController.CartItem> cart = (Map<Long, CartController.CartItem>) session.getAttribute("shopping_cart");
        return (cart != null) ? cart.size() : 0;
    }
}
