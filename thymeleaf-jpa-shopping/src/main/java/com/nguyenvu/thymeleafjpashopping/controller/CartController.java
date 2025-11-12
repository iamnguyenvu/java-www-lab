package com.nguyenvu.thymeleafjpashopping.controller;

import com.nguyenvu.thymeleafjpashopping.dto.OrderDTO;
import com.nguyenvu.thymeleafjpashopping.dto.OrderLineDTO;
import com.nguyenvu.thymeleafjpashopping.dto.ProductDTO;
import com.nguyenvu.thymeleafjpashopping.service.CustomerService;
import com.nguyenvu.thymeleafjpashopping.service.OrderService;
import com.nguyenvu.thymeleafjpashopping.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    
    private final ProductService productService;
    private final OrderService orderService;
    private final CustomerService customerService;
    
    private static final String CART_SESSION_KEY = "shopping_cart";

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Map<Long, CartItem> cart = getCart(session);
        
        BigDecimal total = cart.values().stream()
                .map(item -> item.getSubtotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        model.addAttribute("cartItems", cart.values());
        model.addAttribute("total", total);
        return "cart/cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, 
                          @RequestParam(defaultValue = "1") Integer quantity,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        try {
            ProductDTO product = productService.getProductById(productId);
            
            if (!product.getInStock()) {
                redirectAttributes.addFlashAttribute("error", "Sản phẩm hiện tại hết hàng");
                return "redirect:/products/" + productId;
            }
            
            if (product.getStock() < quantity) {
                redirectAttributes.addFlashAttribute("error", "Số lượng tồn kho không đủ");
                return "redirect:/products/" + productId;
            }
            
            Map<Long, CartItem> cart = getCart(session);
            
            if (cart.containsKey(productId)) {
                CartItem item = cart.get(productId);
                int newQuantity = item.getQuantity() + quantity;
                
                if (product.getStock() < newQuantity) {
                    redirectAttributes.addFlashAttribute("error", "Số lượng vượt quá tồn kho");
                    return "redirect:/products/" + productId;
                }
                
                item.setQuantity(newQuantity);
                item.setSubtotal(product.getPrice().multiply(new BigDecimal(newQuantity)));
            } else {
                CartItem item = new CartItem();
                item.setProductId(productId);
                item.setProductName(product.getProductName());
                item.setPrice(product.getPrice());
                item.setQuantity(quantity);
                item.setSubtotal(product.getPrice().multiply(new BigDecimal(quantity)));
                item.setImageUrl(product.getImageUrl());
                cart.put(productId, item);
            }
            
            session.setAttribute(CART_SESSION_KEY, cart);
            redirectAttributes.addFlashAttribute("success", "Đã thêm sản phẩm vào giỏ hàng");
            return "redirect:/products/" + productId;
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/products";
        }
    }

    @PostMapping("/update/{productId}")
    public String updateQuantity(@PathVariable Long productId,
                                @RequestParam Integer quantity,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            if (quantity <= 0) {
                return removeFromCart(productId, session, redirectAttributes);
            }
            
            ProductDTO product = productService.getProductById(productId);
            
            if (product.getStock() < quantity) {
                redirectAttributes.addFlashAttribute("error", "Số lượng vượt quá tồn kho");
                return "redirect:/cart";
            }
            
            Map<Long, CartItem> cart = getCart(session);
            
            if (cart.containsKey(productId)) {
                CartItem item = cart.get(productId);
                item.setQuantity(quantity);
                item.setSubtotal(product.getPrice().multiply(new BigDecimal(quantity)));
                session.setAttribute(CART_SESSION_KEY, cart);
            }
            
            redirectAttributes.addFlashAttribute("success", "Đã cập nhật giỏ hàng");
            return "redirect:/cart";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/cart";
        }
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        Map<Long, CartItem> cart = getCart(session);
        cart.remove(productId);
        session.setAttribute(CART_SESSION_KEY, cart);
        
        redirectAttributes.addFlashAttribute("success", "Đã xóa sản phẩm khỏi giỏ hàng");
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute(CART_SESSION_KEY);
        redirectAttributes.addFlashAttribute("success", "Đã xóa toàn bộ giỏ hàng");
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Model model, Authentication authentication) {
        Map<Long, CartItem> cart = getCart(session);
        
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }
        
        BigDecimal total = cart.values().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        model.addAttribute("cartItems", cart.values());
        model.addAttribute("total", total);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("orderDTO", new OrderDTO());
        
        return "cart/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@Valid @ModelAttribute OrderDTO orderDTO,
                                 BindingResult bindingResult,
                                 HttpSession session,
                                 Authentication authentication,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            Map<Long, CartItem> cart = getCart(session);
            
            if (cart.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Giỏ hàng trống");
                return "redirect:/cart";
            }
            
            if (bindingResult.hasErrors()) {
                BigDecimal total = cart.values().stream()
                        .map(CartItem::getSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                model.addAttribute("cartItems", cart.values());
                model.addAttribute("total", total);
                model.addAttribute("username", authentication.getName());
                return "cart/checkout";
            }
            
            // Get customer by username
            String username = authentication.getName();
            com.nguyenvu.thymeleafjpashopping.dto.CustomerDTO customer = 
                customerService.getCustomerByUsername(username);
            
            orderDTO.setCustomerId(customer.getCustomerId());
            orderDTO.setStatus("PENDING");
            
            List<OrderLineDTO> orderLines = new ArrayList<>();
            
            for (CartItem item : cart.values()) {
                OrderLineDTO line = new OrderLineDTO();
                line.setProductId(item.getProductId());
                line.setQuantity(item.getQuantity());
                orderLines.add(line);
            }
            
            orderDTO.setOrderLines(orderLines);
            
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            
            session.removeAttribute(CART_SESSION_KEY);
            
            redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công! Mã đơn hàng: " + createdOrder.getOrderId());
            return "redirect:/orders/" + createdOrder.getOrderId();
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/cart/checkout";
        }
    }

    @SuppressWarnings("unchecked")
    private Map<Long, CartItem> getCart(HttpSession session) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public static class CartItem implements java.io.Serializable {
        private Long productId;
        private String productName;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;
        private String imageUrl;

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        
        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
        
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    }
}
