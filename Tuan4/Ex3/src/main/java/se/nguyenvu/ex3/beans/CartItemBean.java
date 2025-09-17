package se.nguyenvu.ex3.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemBean implements Serializable {
    private Product product;
    private Integer quantity;

    public Double getSubtotal() {
        return product.getPrice() * quantity;
    }
}

