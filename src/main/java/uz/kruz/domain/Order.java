package uz.kruz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kruz.domain.vo.BaseEntity;
import uz.kruz.domain.vo.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Domain class representing the orders table in the database.
 */
@Data
@NoArgsConstructor
public class Order extends BaseEntity {
    private Integer userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;

    // Relationships (not stored in database directly)
    private User user;
    private List<OrderItem> orderItems;
    private Shipment shipment;

    public Order(Integer id, Integer userId, BigDecimal totalAmount, OrderStatus status, 
                LocalDateTime orderDate, LocalDateTime createdAt, LocalDateTime updatedAt,
                User user, List<OrderItem> orderItems, Shipment shipment) {
        super(id, createdAt, updatedAt);
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.user = user;
        this.orderItems = orderItems;
        this.shipment = shipment;
    }
}
