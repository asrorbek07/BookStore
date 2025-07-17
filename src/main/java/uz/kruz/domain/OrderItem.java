package uz.kruz.domain;

import lombok.*;
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

    // Relationships (not stored in database directly)
    private Order order;
    private Book book;

}
