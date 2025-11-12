package com.nguyenvu.thymeleafjpashopping.service;

import com.nguyenvu.thymeleafjpashopping.model.Category;
import com.nguyenvu.thymeleafjpashopping.model.Product;
import com.nguyenvu.thymeleafjpashopping.repository.CategoryRepository;
import com.nguyenvu.thymeleafjpashopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatbotDataService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    /**
     * Lấy thông tin về tất cả sản phẩm để chatbot có thể tư vấn
     */
    @Transactional(readOnly = true)
    public String getProductKnowledge() {
        List<Product> products = productRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        
        StringBuilder knowledge = new StringBuilder();
        knowledge.append("=== DANH SÁCH SẢN PHẨM CỬA HÀNG ===\n\n");
        
        // Thông tin danh mục
        knowledge.append("Danh mục sản phẩm:\n");
        for (Category category : categories) {
            knowledge.append(String.format("- %s\n", category.getName()));
        }
        knowledge.append("\n");
        
        // Thông tin sản phẩm theo từng danh mục
        for (Category category : categories) {
            List<Product> categoryProducts = products.stream()
                .filter(p -> p.getCategory().getCategoryId().equals(category.getCategoryId()))
                .collect(Collectors.toList());
            
            if (!categoryProducts.isEmpty()) {
                knowledge.append(String.format("== %s ==\n", category.getName()));
                for (Product product : categoryProducts) {
                    knowledge.append(String.format(
                        "• %s - Giá: %,.0f VNĐ - %s%s%s\n",
                        product.getName(),
                        product.getPrice(),
                        product.getInStock() ? "Còn hàng (" + product.getStock() + " sản phẩm)" : "Hết hàng",
                        product.getDescription() != null && !product.getDescription().isEmpty() 
                            ? " - " + product.getDescription() : "",
                        product.getImageUrl() != null && !product.getImageUrl().isEmpty()
                            ? " [Có hình ảnh]" : ""
                    ));
                }
                knowledge.append("\n");
            }
        }
        
        knowledge.append("\n=== CHÍNH SÁCH CỬA HÀNG ===\n");
        knowledge.append("- Giao hàng toàn quốc, miễn phí với đơn hàng trên 500.000 VNĐ\n");
        knowledge.append("- Bảo hành chính hãng theo quy định của nhà sản xuất\n");
        knowledge.append("- Đổi trả trong vòng 7 ngày nếu sản phẩm có lỗi\n");
        knowledge.append("- Thanh toán: Tiền mặt, chuyển khoản, ví điện tử\n");
        knowledge.append("- Hỗ trợ khách hàng 24/7\n\n");
        
        return knowledge.toString();
    }
    
    /**
     * Tìm sản phẩm theo tên (dùng cho chatbot search)
     */
    @Transactional(readOnly = true)
    public String searchProducts(String keyword) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        
        if (products.isEmpty()) {
            return "Không tìm thấy sản phẩm nào với từ khóa: " + keyword;
        }
        
        StringBuilder result = new StringBuilder();
        result.append(String.format("Tìm thấy %d sản phẩm với từ khóa '%s':\n\n", products.size(), keyword));
        
        for (Product product : products) {
            result.append(String.format(
                "• %s - Giá: %,.0f VNĐ - %s\n",
                product.getName(),
                product.getPrice(),
                product.getInStock() ? "Còn hàng" : "Hết hàng"
            ));
        }
        
        return result.toString();
    }
    
    /**
     * Lấy sản phẩm theo khoảng giá
     */
    @Transactional(readOnly = true)
    public String getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        
        if (products.isEmpty()) {
            return String.format("Không có sản phẩm nào trong khoảng giá %,.0f - %,.0f VNĐ", 
                minPrice, maxPrice);
        }
        
        StringBuilder result = new StringBuilder();
        result.append(String.format("Sản phẩm trong khoảng giá %,.0f - %,.0f VNĐ:\n\n", 
            minPrice, maxPrice));
        
        for (Product product : products) {
            result.append(String.format(
                "• %s - Giá: %,.0f VNĐ - %s\n",
                product.getName(),
                product.getPrice(),
                product.getInStock() ? "Còn hàng" : "Hết hàng"
            ));
        }
        
        return result.toString();
    }
    
    /**
     * Lấy sản phẩm theo danh mục
     */
    @Transactional(readOnly = true)
    public String getProductsByCategory(String categoryName) {
        Category category = categoryRepository.findByNameContainingIgnoreCase(categoryName).stream()
            .findFirst()
            .orElse(null);
        
        if (category == null) {
            return "Không tìm thấy danh mục: " + categoryName;
        }
        
        List<Product> products = productRepository.findByCategory(category);
        
        if (products.isEmpty()) {
            return "Danh mục " + category.getName() + " chưa có sản phẩm nào.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append(String.format("Sản phẩm trong danh mục '%s':\n\n", category.getName()));
        
        for (Product product : products) {
            result.append(String.format(
                "• %s - Giá: %,.0f VNĐ - %s\n",
                product.getName(),
                product.getPrice(),
                product.getInStock() ? "Còn hàng" : "Hết hàng"
            ));
        }
        
        return result.toString();
    }
}
