package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class OrderItemDTO extends BaseDTO {
    private Integer orderId;
    private Integer bookId;
    private Integer quantity;
    private BigDecimal price;

    @Override
    public String toString() {
        return "OrderItemDTO{" +
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
