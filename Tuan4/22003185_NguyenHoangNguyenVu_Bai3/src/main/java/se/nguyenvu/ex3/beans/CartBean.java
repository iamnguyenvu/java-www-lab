package se.nguyenvu.ex3.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartBean {
    @Builder.Default
    private List<CartItemBean> items = new ArrayList<>();

    public void clear() {
        if (items != null) {
            items.clear();
        }
    }

    public void addProduct(Product product) {
        if (items == null) {
            items = new ArrayList<>();
        }
        
        // Tìm sản phẩm đã có trong cart
        boolean found = false;
        for(CartItemBean item : items) {
            if(item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }
        
        // Nếu chưa có, thêm mới
        if (!found) {
            items.add(new CartItemBean(product, 1));
        }
    }

    public void removeProduct(int productId) {
        if (items != null) {
            items.removeIf(item -> item.getProduct().getId().equals(productId));
        }
    }

    public void updateQuantity(int productId, int quantity) {
        if (items != null) {
            items.stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst()
                    .ifPresent(item -> item.setQuantity(quantity));
        }
    }

    public Double getTotal() {
        if (items == null) {
            return 0.0;
        }
        return items.stream().mapToDouble(CartItemBean::getSubtotal).sum();
    }
}
