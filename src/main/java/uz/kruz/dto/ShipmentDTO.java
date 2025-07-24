package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class ShipmentDTO extends BaseDTO {
    private Integer orderId;
    private String trackingNo;
    private Timestamp shippedAt;
    private Date deliveryEstimate;

    @Override
    public String toString() {
        return "ShipmentDTO{" +
                "id=" + getId() +
                ", orderId=" + orderId +
                ", trackingNo='" + trackingNo + '\'' +
                ", shippedAt=" + shippedAt +
                ", deliveryEstimate=" + deliveryEstimate +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
