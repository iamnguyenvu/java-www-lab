package com.nguyenvu.thymeleafjpashopping.controller;

import com.nguyenvu.thymeleafjpashopping.dto.ProductDTO;
import com.nguyenvu.thymeleafjpashopping.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping({"/products"})
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/products";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product/product";
    }

    @GetMapping("/new")
    public String showProductForm(Model model) {
        ProductDTO product = ProductDTO.builder()
                .inStock(true)
                .stock(0)
                .build();
        model.addAttribute("product", product);
        return "product/product-form";
    }

    @PostMapping("/new")
    public String createProduct(@Valid @ModelAttribute("product") ProductDTO productDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/product-form";
        }
        
        try {
            productService.createProduct(productDTO);
            redirectAttributes.addFlashAttribute("success", "Tạo sản phẩm thành công!");
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi: " + e.getMessage());
            return "product/product-form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product/product-form";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, 
                               @Valid @ModelAttribute("product") ProductDTO productDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            productDTO.setProductId(id);
            return "product/product-form";
        }
        
        try {
            productService.updateProduct(id, productDTO);
            redirectAttributes.addFlashAttribute("success", "Cập nhật sản phẩm thành công!");
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi: " + e.getMessage());
            productDTO.setProductId(id);
            return "product/product-form";
        }
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String searchProductsByName(@RequestParam String name, Model model) {
        model.addAttribute("products", productService.getProductsByNameContaining(name));
        return "product/products";
    }

    @GetMapping("/priceRange")
    public String listProductsByPriceBetween(@RequestParam BigDecimal low,
                                             @RequestParam BigDecimal high,
                                             Model model) {
        model.addAttribute("products", productService.getProductsByPriceBetween(low, high));
        return "product/products";
    }

    @GetMapping("/greater")
    public String listProductsByPriceGreaterThan(@RequestParam BigDecimal low, Model model) {
        model.addAttribute("products", productService.getProductsByPriceGreaterThan(low));
        return "product/products";
    }

    @GetMapping("/less")
    public String listProductsByPriceLessThan(@RequestParam BigDecimal low, Model model) {
        model.addAttribute("products", productService.getProductsPriceLessThan(low));
        return "product/products";
    }
}
