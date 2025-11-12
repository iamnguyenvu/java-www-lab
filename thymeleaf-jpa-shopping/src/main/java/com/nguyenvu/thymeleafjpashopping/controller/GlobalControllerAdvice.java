package com.nguyenvu.thymeleafjpashopping.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

/**
 * Global controller advice to add common model attributes to all views
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    
    private static final String CART_SESSION_KEY = "shopping_cart";
    
    /**
     * Add cart item count to all views
     * Returns 0 for guest users or users with empty cart
     */
    @ModelAttribute("cartItemCount")
    public int getCartItemCount(HttpSession session, Authentication authentication) {
        // Guest users (not authenticated) have no cart
        if (authentication == null || !authentication.isAuthenticated() || 
            "anonymousUser".equals(authentication.getPrincipal())) {
            return 0;
        }
        
        // Get cart from session
        @SuppressWarnings("unchecked")
        Map<Long, CartController.CartItem> cart = 
            (Map<Long, CartController.CartItem>) session.getAttribute(CART_SESSION_KEY);
        
        if (cart == null || cart.isEmpty()) {
            return 0;
        }
        
        // Calculate total quantity of all items in cart
        return cart.values().stream()
                .mapToInt(CartController.CartItem::getQuantity)
                .sum();
    }
}
