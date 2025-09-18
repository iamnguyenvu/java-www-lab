package se.nguyenvu.ex4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    private Integer bookId;
    private String title;
    private String author;
    private String url;
    private BigDecimal price;
    private Integer quantity;
    
    // Getter for JSP compatibility
    public Integer getId() {
        return bookId;
    }
}
