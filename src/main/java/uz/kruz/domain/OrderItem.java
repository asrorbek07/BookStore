package uz.kruz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kruz.domain.vo.BaseEntity;

import java.math.BigDecimal;

/**
 * Domain class representing the order_items table in the database.
 */
@Data
@NoArgsConstructor
public class OrderItem extends BaseEntity {
    private Integer orderId;
    private Integer bookId;
    private Integer quantity;
    private BigDecimal price;

    // Relationships (not stored in database directly)
    private Order order;
    private Book book;

    public OrderItem(Integer id, Integer orderId, Integer bookId, Integer quantity, BigDecimal price,
                    java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt,
                    Order order, Book book) {
        super(id, createdAt, updatedAt);
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.book = book;
    }
}
