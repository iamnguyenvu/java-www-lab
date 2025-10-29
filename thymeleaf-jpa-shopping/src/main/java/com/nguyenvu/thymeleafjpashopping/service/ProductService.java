package com.nguyenvu.thymeleafjpashopping.service;

import com.nguyenvu.thymeleafjpashopping.dto.CommentDTO;
import com.nguyenvu.thymeleafjpashopping.dto.OrderLineDTO;
import com.nguyenvu.thymeleafjpashopping.dto.ProductDTO;
import com.nguyenvu.thymeleafjpashopping.model.Category;
import com.nguyenvu.thymeleafjpashopping.model.Comment;
import com.nguyenvu.thymeleafjpashopping.model.OrderLine;
import com.nguyenvu.thymeleafjpashopping.model.Product;
import com.nguyenvu.thymeleafjpashopping.repository.CategoryRepository;
import com.nguyenvu.thymeleafjpashopping.repository.OrderLineRepository;
import com.nguyenvu.thymeleafjpashopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderLineRepository orderLineRepository;
    private final CommentService commentService;

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return convertToDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDTO.getCategoryId()));
        
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setInStock(productDTO.getInStock() != null ? productDTO.getInStock() : true);
        product.setStock(productDTO.getStock() != null ? productDTO.getStock() : 0);
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(category);

        return convertToDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setInStock(productDTO.getInStock());
        product.setStock(productDTO.getStock());
        product.setImageUrl(productDTO.getImageUrl());
        
        if (productDTO.getCategoryId() != null && !product.getCategory().getCategoryId().equals(productDTO.getCategoryId())) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDTO.getCategoryId()));
            product.setCategory(category);
        }

        return convertToDTO(productRepository.save(product));
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByPriceBetween(BigDecimal low, BigDecimal high) {
        return productRepository.findByPriceBetween(low, high).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByPriceGreaterThan(BigDecimal low) {
        return productRepository.findByPriceGreaterThan(low).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsPriceLessThan(BigDecimal low) {
        return productRepository.findByPriceLessThan(low).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByNameContaining(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getInStockProducts() {
        return productRepository.findByInStockTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void updateStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        int newStock = product.getStock() - quantity;
        if (newStock < 0) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }
        
        product.setStock(newStock);
        product.setInStock(newStock > 0);
        productRepository.save(product);
    }

    private ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }
        
        ProductDTO dto = ProductDTO.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .inStock(product.getInStock())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategory().getCategoryId())
                .categoryName(product.getCategory().getName())
                .commentCount(product.getComments() != null ? product.getComments().size() : 0)
                .orderLineCount(product.getOrderLines() != null ? product.getOrderLines().size() : 0)
                .build();
        
        // Calculate average rating
        if (product.getComments() != null && !product.getComments().isEmpty()) {
            double avgRating = product.getComments().stream()
                    .mapToInt(Comment::getRating)
                    .average()
                    .orElse(0.0);
            dto.setAverageRating(avgRating);
        }
        
        return dto;
    }

    private OrderLineDTO convertToDTO(OrderLine orderLine) {
        return OrderLineDTO.builder()
                .orderLineId(orderLine.getOrderLineId())
                .quantity(orderLine.getQuantity())
                .unitPrice(orderLine.getUnitPrice())
                .subtotal(orderLine.getSubtotal())
                .orderId(orderLine.getOrder().getOrderId())
                .orderDate(orderLine.getOrder().getOrderDate())
                .orderStatus(orderLine.getOrder().getStatus())
                .productId(orderLine.getProduct().getProductId())
                .productName(orderLine.getProduct().getName())
                .productImageUrl(orderLine.getProduct().getImageUrl())
                .build();
    }
}
