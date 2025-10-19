package com.nguyenvu.thymeleafjpashopping.service;

import com.nguyenvu.thymeleafjpashopping.dto.CommentDTO;
import com.nguyenvu.thymeleafjpashopping.dto.OrderLineDTO;
import com.nguyenvu.thymeleafjpashopping.dto.ProductDTO;
import com.nguyenvu.thymeleafjpashopping.model.Comment;
import com.nguyenvu.thymeleafjpashopping.model.OrderLine;
import com.nguyenvu.thymeleafjpashopping.model.Product;
import com.nguyenvu.thymeleafjpashopping.repository.OrderLineRepository;
import com.nguyenvu.thymeleafjpashopping.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderLineRepository orderLineRepository;

    public ProductService(ProductRepository productRepository, OrderLineRepository orderLineRepository) {
        this.productRepository = productRepository;
        this.orderLineRepository = orderLineRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return convertToDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .inStock(productDTO.isInStock())
                .build();

        if(productDTO.getComments() != null) {
            productDTO.getComments().stream()
                    .filter(Objects::nonNull)
                    .map(this::convertToEntity)
                    .forEach(product::addComment);
        } else product.setComments(null);

        if(productDTO.getOrderLines() != null) {
            productDTO.getOrderLines().stream()
                    .filter(Objects::nonNull)
                    .map(olDto -> convertToEntity(olDto, product))
                    .forEach(product::addOrderLine);
        } else product.setOrderLines(null);

        return convertToDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId()).orElse(null);
        if(product == null) return null;

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setInStock(productDTO.isInStock());

        return convertToDTO(productRepository.save(product));
    }

    public void deleteProduct(Integer id) {
        productRepository.findById(id).ifPresent(productRepository::delete);
    }

    public List<ProductDTO> getProductsByPriceBetween(BigDecimal low, BigDecimal high) {
        return productRepository.findByPriceBetween(low, high).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ProductDTO> getProductsByPriceGreaterThan(BigDecimal low) {
        return productRepository.findByPriceGreaterThan(low).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ProductDTO> getProductsPriceLessThan(BigDecimal low) {
        return productRepository.findByPriceLessThan(low).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ProductDTO> getProductsByNameContaining(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .toList();
    }

//    Convert to DTO helper
    private ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .inStock(product.isInStock())
                .commentCount(product.getComments() != null ? product.getComments().size() : 0)
                .comments(product.getComments() != null ? product.getComments().stream()
                        .map(this::convertToDTO)
                        .toList() : null)
                .orderLineCount(product.getOrderLines() != null ? product.getOrderLines().size() : 0)
                .orderLines(product.getOrderLines() != null ? product.getOrderLines().stream()
                        .map(this::convertToDTO).collect(Collectors.toSet()) : null)
                .build();
    }

    private CommentDTO convertToDTO(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getId())
                .text(comment.getText())
                .productId(comment.getProduct().getId())
                .productName(comment.getProduct().getName())
                .build();
    }

    private OrderLineDTO convertToDTO(OrderLine orderLine) {
        return OrderLineDTO.builder()
                .orderLineId(orderLine.getId())
                .amount(orderLine.getAmount())
                .purchasePrice(orderLine.getPurchasePrice())
                .orderId(orderLine.getOrder().getId())
                .orderDate(orderLine.getOrder().getDate())
                .productId(orderLine.getProduct().getId())
                .productName(orderLine.getProduct().getName())
                .build();
    }

    private OrderLine convertToEntity(OrderLineDTO orderLineDTO, Product product) {
        return OrderLine.builder()
                .amount(orderLineDTO.getAmount())
                .purchasePrice(orderLineDTO.getPurchasePrice())
                .product(product)
                .build();
    }

    private Comment convertToEntity(CommentDTO commentDTO) {
        return Comment.builder()
                .text(commentDTO.getText())
                .product(commentDTO.getProductId() != null ?
                        productRepository.getReferenceById(commentDTO.getProductId())
                        : null)
                .build();
    }
}
