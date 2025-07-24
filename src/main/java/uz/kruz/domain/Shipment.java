package uz.kruz.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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

    private Order order;

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + getId() +
                ", orderId=" + orderId +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", shippedAt=" + shippedAt +
                ", deliveryEstimate=" + deliveryEstimate +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
