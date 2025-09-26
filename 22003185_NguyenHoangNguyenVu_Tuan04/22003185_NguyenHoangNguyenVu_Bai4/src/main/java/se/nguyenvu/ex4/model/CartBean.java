package se.nguyenvu.ex4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartBean {
    private List<CartBookBean> cartBooks = new ArrayList<>();

    public void clear() {
        cartBooks.clear();
    }

    public List<CartBookBean> getCartBooks() {
        if(cartBooks == null) {
            cartBooks = new ArrayList<>();
        }
        return cartBooks;
    }

    public void addBook(Book book) {
        cartBooks.stream().filter(b -> b.getBook().getBookId().equals(book.getBookId()))
                .findFirst().ifPresentOrElse(
                        b -> b.setQuantity(b.getQuantity() + 1),
                        () -> cartBooks.add(new CartBookBean(book, 1))
                );
    }
    
    public void addBook(Book book, Integer quantity) {
        cartBooks.stream().filter(b -> b.getBook().getBookId().equals(book.getBookId()))
                .findFirst().ifPresentOrElse(
                        b -> b.setQuantity(b.getQuantity() + quantity),
                        () -> cartBooks.add(new CartBookBean(book, quantity))
                );
    }

    public void removeBook(Integer bookId) {
        cartBooks.removeIf(b -> b.getBook().getBookId().equals(bookId));
    }

    public void updateQuantity(Integer bookId, Integer quantity) {
        cartBooks.stream().filter(b -> b.getBook().getBookId().equals(bookId))
                .findFirst().ifPresent(b -> b.setQuantity(quantity));
    }
    public BigDecimal getTotal() {
        return cartBooks.stream()
                .map(CartBookBean::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public BigDecimal getTotalPrice() {
        return getTotal();
    }
}
