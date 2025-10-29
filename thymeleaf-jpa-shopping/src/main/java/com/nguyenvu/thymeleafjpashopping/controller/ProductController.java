package com.nguyenvu.thymeleafjpashopping.controller;

import com.nguyenvu.thymeleafjpashopping.dto.ProductDTO;
import com.nguyenvu.thymeleafjpashopping.model.Product;
import com.nguyenvu.thymeleafjpashopping.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("product", new Product());
        return "product/product-form";
    }

    @PostMapping("/new")
    public String createProduct(@ModelAttribute ProductDTO productDTO) {
        productService.createProduct(productDTO);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product/product-form";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
        return "redirect:/products";
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
