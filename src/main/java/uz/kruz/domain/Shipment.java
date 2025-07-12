package uz.kruz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kruz.domain.vo.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Domain class representing the shipments table in the database.
 */
@Data
@NoArgsConstructor
public class Shipment extends BaseEntity {
    private Integer orderId;
    private String trackingNumber;
    private LocalDateTime shippedAt;
    private LocalDate deliveryEstimate;

    // Relationships (not stored in database directly)
    private Order order;

    public Shipment(Integer id, Integer orderId, String trackingNumber, LocalDateTime shippedAt,
                   LocalDate deliveryEstimate, LocalDateTime createdAt, LocalDateTime updatedAt,
                   Order order) {
        super(id, createdAt, updatedAt);
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.shippedAt = shippedAt;
        this.deliveryEstimate = deliveryEstimate;
        this.order = order;
    }
}
