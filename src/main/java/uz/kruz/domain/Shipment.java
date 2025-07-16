package uz.kruz.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class Shipment extends BaseEntity {
    private Integer orderId;
    private String trackingNumber;
    private LocalDateTime shippedAt;
    private LocalDate deliveryEstimate;

    // Relationships (not stored in database directly)
    private Order order;

}
