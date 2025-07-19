package uz.kruz.dto;

import com.sun.jna.platform.win32.Sspi;
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
        private  Integer orderId;
        private String trackingNo;
        private Timestamp shippedAt;
        private Date deliveryEstimate;
}
