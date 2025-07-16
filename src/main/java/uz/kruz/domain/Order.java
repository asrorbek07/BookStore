package uz.kruz.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.BaseEntity;
import uz.kruz.domain.vo.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class Order extends BaseEntity {
    private Integer userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;

    // Relationships (not stored in database directly)
    private User user;
    private List<OrderItem> orderItems;
    private Shipment shipment;
}
