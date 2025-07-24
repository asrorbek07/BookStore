package uz.kruz.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.BaseEntity;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class OrderItem extends BaseEntity {
    private Integer orderId;
    private Integer bookId;
    private Integer quantity;
    private BigDecimal price;

    private Order order;
    private Book book;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + getId() +
                ", orderId=" + orderId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
